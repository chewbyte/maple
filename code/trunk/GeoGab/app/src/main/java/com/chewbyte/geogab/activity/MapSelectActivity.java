package com.chewbyte.geogab.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.chewbyte.geogab.MapleObject.MapleMap;
import com.chewbyte.geogab.MapleObject.MapleMarker;
import com.chewbyte.geogab.MapleService;
import com.chewbyte.geogab.R;
import com.chewbyte.geogab.RadioButtons;
import com.chewbyte.geogab.ServiceGenerator;
import com.chewbyte.geogab.ThreadHeaderAdapter;
import com.chewbyte.geogab.common.Category;
import com.chewbyte.geogab.common.Session;
import com.chewbyte.geogab.common.ThreadHeader;
import com.mapbox.mapboxsdk.MapboxAccountManager;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerViewOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.constants.MyBearingTracking;
import com.mapbox.mapboxsdk.constants.MyLocationTracking;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationServices;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.UiSettings;
import com.mapbox.services.commons.ServicesException;
import com.mapbox.services.commons.models.Position;
import com.mapbox.services.geocoding.v5.GeocodingCriteria;
import com.mapbox.services.geocoding.v5.MapboxGeocoding;
import com.mapbox.services.geocoding.v5.models.CarmenFeature;
import com.mapbox.services.geocoding.v5.models.GeocodingResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapSelectActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MapSelectActivity";

    private MapView mapView;
    private MapboxMap map;

    private View MarkerPanel;
    private View ThreadPanel;
    private TextView Tooltip;

    private String addr_short;
    private String addr_full;
    private TextView tAddr_short, tAddr_full;

    private ProgressBar progressBar;

    private Button ThreadCancel, ThreadNext;

    private EditText ThreadText;
    private TextView ThreadTitleCount;

    private Marker droppedMarker;

    private Animation aBottomUp, aBottomDown, aTopUp, aTopDown;
    private FloatingActionButton bSearch, bFilter, bLocate, bNew;

    private boolean loc_mode = false;
    private boolean markerValid = false;

    private LocationServices loc_services;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MapboxAccountManager.start(this, getString(R.string.access_token));
        setContentView(R.layout.activity_map_select);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Maple");

        // Get TextView of the Appbar
        TextView toolbarTitle = null;
        for (int i = 0; i < toolbar.getChildCount(); ++i) {
            View child = toolbar.getChildAt(i);
            if (child instanceof TextView) {
                if (((TextView) child).getText().equals("Maple"))
                    toolbarTitle = (TextView) child;
                break;
            }
        }
        Typeface face = Typeface.createFromAsset(getAssets(), "mastoc.ttf");
        toolbarTitle.setTypeface(face);
        toolbarTitle.setTextSize(toolbarTitle.getTextSize() * 0.5f);

        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);
        TextView nav_header_title = (TextView) header.findViewById(R.id.nav_header_title);
        nav_header_title.setTypeface(face);

        // Mapbox mapView
        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);

        // Marker Panel (dropped pin)
        MarkerPanel = findViewById(R.id.marker_panel);
        MarkerPanel.setVisibility(View.INVISIBLE);

        // Marker Panel TextViews for displaying geolocation data
        tAddr_full = (TextView) findViewById(R.id.addr_full);
        tAddr_short = (TextView) findViewById(R.id.addr_short);

        // Marker Panel progress spinner during geolocation
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        // Thread Panel (new)
        ThreadPanel = findViewById(R.id.thread_panel);
        ThreadPanel.setVisibility(View.INVISIBLE);

        // Thread Panel EditText Tooltip (for displaying char count)
        Tooltip = (TextView) findViewById(R.id.radioTooltip);

        // Radio categories
        new RadioButtons(Tooltip, new ArrayList<>(Arrays.asList(
                (RadioButton) findViewById(R.id.radioEvent),
                (RadioButton) findViewById(R.id.radioDebate),
                (RadioButton) findViewById(R.id.radioAware),
                (RadioButton) findViewById(R.id.radioCurious)
        )));

        //Thread title count
        ThreadTitleCount = (TextView) findViewById(R.id.titleCount);

        // Load animations
        aBottomUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_bottom_up);
        aBottomDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_bottom_down);
        aTopUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_top_up);
        aTopDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_top_down);

        // Thread Panel Cancel Button
        ThreadCancel = (Button) findViewById(R.id.buttonCancel);
        ThreadCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setThreadPanelVisibility(false);
            }
        });

        // Thread Panel Next Button
        ThreadNext = (Button) findViewById(R.id.buttonNext);
        ThreadNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                if(Session.getMapSelected() != null) {
                    Session.setThreadTitle(String.valueOf(ThreadText.getText()));

                    MapleMarker marker = new MapleMarker();
                    marker.setLatitude((float) droppedMarker.getPosition().getLatitude());
                    marker.setLongitude((float) droppedMarker.getPosition().getLongitude());
                    marker.setTitle(Session.getThreadTitle());
                    marker.setMapid(Session.getMapSelected().getId());
                    marker.setUserid("0");

                    MapleService mapleService = ServiceGenerator.createService(MapleService.class);
                    Call<Void> call = mapleService.addMarker(marker);
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if(response.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Successfully persisted to server.", Toast.LENGTH_LONG).show();

                                new ThreadHeader(
                                        droppedMarker.getPosition(),
                                        Session.getThreadTitle(),
                                        "Snippet goes here.",
                                        Session.getCategorySelected(),
                                        Session.getUserById(1),
                                        map
                                );

                                Session.setThreadTitle("");
                                ThreadText.setText("");
                                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                                setMarkerPanelVisibility(false);
                                setThreadPanelVisibility(false);

                                map.removeAnnotation(droppedMarker);
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "Failed to connect to the server.", Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "Aborted: No map selected.", Toast.LENGTH_LONG).show();
                }
            }
        });

        // Thread Panel TextView for checking and updating char numbers
        ThreadText = (EditText) findViewById(R.id.threadText);
        ThreadText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                ThreadTitleCount.setText(String.format("%d/%d", charSequence.length(), 25));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        // Search Button
        bSearch = (FloatingActionButton) findViewById(R.id.mapbutton_search);
        bSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "TODO: Search for threads", Toast.LENGTH_SHORT).show();
            }
        });

        // Filter Button
        bFilter = (FloatingActionButton) findViewById(R.id.mapbutton_filter);
        bFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "TODO: Filter by thread type", Toast.LENGTH_SHORT).show();
            }
        });

        // Location Mode Button
        bLocate = (FloatingActionButton) findViewById(R.id.mapbutton_geolocate);
        bLocate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locModeToggle();
            }
        });

        // New Thread Button
        bNew = (FloatingActionButton) findViewById(R.id.mapbutton_new);
        bNew.setVisibility(View.INVISIBLE);
        bNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (markerValid) {
                    LatLng viewMarker = new LatLng();
                    viewMarker.setLatitude(droppedMarker.getPosition().getLatitude() + 0.00075);
                    viewMarker.setLongitude(droppedMarker.getPosition().getLongitude());

                    map.setCameraPosition(new CameraPosition.Builder()
                            .target(viewMarker)
                            .zoom(15)
                            .build());
                    if (ThreadPanel.getVisibility() == View.INVISIBLE) {
                        setThreadPanelVisibility(true);
                    }
                }
            }
        });

        // Load MapBox location services
        loc_services = LocationServices.getLocationServices(MapSelectActivity.this);

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {

                map = mapboxMap;

                UiSettings ui = mapboxMap.getUiSettings();
                ui.setCompassEnabled(false);
                ui.setRotateGesturesEnabled(false);
                ui.setLogoEnabled(false);
                ui.setTiltGesturesEnabled(false);
                ui.setAttributionEnabled(false);

                mapboxMap.getMarkerViewManager().addMarkerViewAdapter(new ThreadHeaderAdapter(MapSelectActivity.this));

                // Behavior when the map is touched
                mapboxMap.setOnMapClickListener(new MapboxMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(@NonNull LatLng point) {

                        setMarkerPanelVisibility(false);
                        setThreadPanelVisibility(false);

                        // Remove previous dropped markers
                        if (droppedMarker != null) {
                            map.removeAnnotation(droppedMarker);
                        }
                    }
                });

                // Behavior when the map is long touched
                mapboxMap.setOnMapLongClickListener(new MapboxMap.OnMapLongClickListener() {
                    @Override
                    public void onMapLongClick(@NonNull LatLng point) {

                        MarkerViewOptions marker = new MarkerViewOptions()
                                .position(point);

                        // Remove previous dropped markers
                        if (droppedMarker != null) {
                            map.removeAnnotation(droppedMarker);
                        }

                        droppedMarker = map.addMarker(marker);
                        reverseGeocode(droppedMarker.getPosition(), map);
                    }
                });

                mapboxMap.getMarkerViewManager().setOnMarkerViewClickListener(new MapboxMap.OnMarkerViewClickListener() {
                    @Override
                    public boolean onMarkerClick(@NonNull Marker marker, @NonNull View view, @NonNull MapboxMap.MarkerViewAdapter adapter) {
                        return true;
                    }
                });
            }
        });
    }

    private void setMarkerPanelVisibility(boolean visible) {

        if (visible && (MarkerPanel.getVisibility() == View.INVISIBLE)) {

            MarkerPanel.startAnimation(aBottomUp);
            MarkerPanel.setVisibility(View.VISIBLE);
            bNew.startAnimation(aBottomUp);
            bNew.setVisibility(View.VISIBLE);

            bSearch.startAnimation(aBottomDown);
            bFilter.startAnimation(aBottomDown);
            bLocate.startAnimation(aBottomDown);

            bSearch.setVisibility(View.INVISIBLE);
            bFilter.setVisibility(View.INVISIBLE);
            bLocate.setVisibility(View.INVISIBLE);
        } else if (!visible && MarkerPanel.getVisibility() == View.VISIBLE) {

            MarkerPanel.startAnimation(aBottomDown);
            MarkerPanel.setVisibility(View.INVISIBLE);
            bNew.startAnimation(aBottomDown);
            bNew.setVisibility(View.INVISIBLE);

            bSearch.startAnimation(aBottomUp);
            bFilter.startAnimation(aBottomUp);
            bLocate.startAnimation(aBottomUp);

            bSearch.setVisibility(View.VISIBLE);
            bFilter.setVisibility(View.VISIBLE);
            bLocate.setVisibility(View.VISIBLE);
        }
    }

    private void setThreadPanelVisibility(boolean visible) {

        if (visible && (ThreadPanel.getVisibility() == View.INVISIBLE)) {
            ThreadPanel.setVisibility(View.VISIBLE);
            ThreadPanel.startAnimation(aTopDown);
        } else if (!visible && (ThreadPanel.getVisibility() == View.VISIBLE)) {
            ThreadPanel.startAnimation(aTopUp);
            ThreadPanel.setVisibility(View.INVISIBLE);
        }

        ThreadText.requestFocus();
    }

    private void reverseGeocode(final LatLng point, final MapboxMap map) {
        // This method is used to reverse geocode where the user has dropped the marker.

        tAddr_full.setText("");
        tAddr_short.setText("");
        markerValid = false;
        bNew.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#aaaaaa")));
        bNew.setEnabled(false);

        setMarkerPanelVisibility(true);
        progressBar.setVisibility(View.VISIBLE);

        try {
            MapboxGeocoding client = new MapboxGeocoding.Builder()
                    .setAccessToken(getString(R.string.access_token))
                    .setCoordinates(Position.fromCoordinates(point.getLongitude(), point.getLatitude()))
                    .setGeocodingType(GeocodingCriteria.TYPE_ADDRESS)
                    .build();

            client.enqueueCall(new Callback<GeocodingResponse>() {

                @Override
                public void onResponse(Call<GeocodingResponse> call, Response<GeocodingResponse> response) {

                    List<CarmenFeature> results = response.body().getFeatures();
                    if (results.size() > 0) {
                        CarmenFeature feature = results.get(0);
                        if (droppedMarker != null) {

                            if (feature.getAddress() == null)
                                addr_short = feature.getText();
                            else
                                addr_short = String.format("%s %s", feature.getAddress(), feature.getText());

                            addr_full = feature.getPlaceName();
                            addr_full = addr_full.replace(", United Kingdom", "");
                            addr_full = addr_full.replace(addr_short + ", ", "");
                            addr_full = addr_full.substring(0, addr_full.length() - 4);

                            tAddr_short.setText(addr_short);
                            tAddr_full.setText(addr_full);

                            progressBar.setVisibility(View.INVISIBLE);

                            markerValid = true;
                            bNew.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#3399cc")));
                            bNew.setEnabled(true);
                        }

                    } else {
                        if (droppedMarker != null) {
                            addr_short = "No results";
                            addr_full = "There was a problem retrieving the address.";
                        }
                    }
                }

                @Override
                public void onFailure(Call<GeocodingResponse> call, Throwable t) {
                    Log.e(TAG, "Geocoding Failure: " + t.getMessage());
                    addr_short = "No results";
                    addr_full = "There was a problem retrieving the address.";
                }
            });
        } catch (ServicesException e) {
            Log.e(TAG, "Error geocoding: " + e.toString());
            e.printStackTrace();
        }
    }// reverseGeocode

    private void locModeToggle() {
        if (!loc_mode) {
            if (!loc_services.areLocationPermissionsGranted()) {

                ActivityCompat.requestPermissions(MapSelectActivity.this, new String[]{
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION}, 0);
            }
            if (loc_services.areLocationPermissionsGranted()) {

                enableLocationTracking();
                bLocate.setColorFilter(Color.parseColor("#FFCC00"));
            }
        } else {
            //map.setCameraPosition(savedCameraPosition);
            map.getTrackingSettings().setDismissAllTrackingOnGesture(true);
            bLocate.setColorFilter(Color.parseColor("#aaaaaa"));
        }
        loc_mode = !loc_mode;
    }

    private void enableLocationTracking() {

        // Disable tracking dismiss on map gesture
        map.getTrackingSettings().setDismissAllTrackingOnGesture(false);

        // Enable location and bearing tracking
        map.getTrackingSettings().setMyLocationTrackingMode(MyLocationTracking.TRACKING_FOLLOW);
        map.getTrackingSettings().setMyBearingTrackingMode(MyBearingTracking.NONE);

        // Zoom in for tracking mode
        map.setCameraPosition(new CameraPosition.Builder()
                .zoom(15)
                .build());
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Session.getMapSelected() != null && Session.getMarkers() != null) {
            map.clear();

            for (MapleMarker marker : Session.getMarkers()) {
                new ThreadHeader(
                        new LatLng(marker.getLatitude(), marker.getLongitude()),
                        marker.getTitle(),
                        "Snippet goes here.",
                        Category.DEBATE,
                        Session.getUserById(1),
                        map
                );
            }
        }
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.map_select, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_maps) {
            Intent intent = new Intent(this, MapListActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_search) {

        } else if (id == R.id.nav_trending) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_map_create) {

        } else if (id == R.id.nav_share) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
