package com.chewbyte.geogab.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.chewbyte.geogab.MapleObject.MapleMap;
import com.chewbyte.geogab.MapleObject.MapleMarker;
import com.chewbyte.geogab.MapleService;
import com.chewbyte.geogab.R;
import com.chewbyte.geogab.ServiceGenerator;
import com.chewbyte.geogab.common.Session;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapListActivity extends AppCompatActivity {

    private static final String TAG = "MapListActivity";

    private ProgressBar progressBar;
    private ListView listView;
    private ArrayAdapter<String> listAdapter;
    private List<MapleMap> mapList;
    private FloatingActionButton addMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.list_toolbar);
        toolbar.setTitle("Select a map");
        setSupportActionBar(toolbar);

        listView = (ListView) findViewById(R.id.list_map);
        progressBar = (ProgressBar) findViewById(R.id.list_progressBar);

        Handler handler = new Handler();
        Runnable r = new Runnable() {
            public void run() {
                listAllMaps();
            }
        };
        handler.postDelayed(r, 500);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView row = (TextView) view.findViewById(R.id.rowTextView);
                MapleMap selectedMap = null;
                for (MapleMap map : mapList) {
                    selectedMap = map.getTitle().equals(row.getText()) ? map : selectedMap;
                }
                Toast.makeText(getApplicationContext(), String.format("Selected: %s", selectedMap.getTitle()), Toast.LENGTH_LONG).show();
                Session.setMapSelected(selectedMap);
                retrieveMarkersForMap(selectedMap.getId());
            }
        });

        addMap = (FloatingActionButton) findViewById(R.id.add_map);
        addMap.setEnabled(false);
        addMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "TODO: Create Map", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void listAllMaps() {
        MapleService mapleService = ServiceGenerator.createService(MapleService.class);
        Call<List<MapleMap>> call = mapleService.getAllMaps();
        call.enqueue(new Callback<List<MapleMap>>() {
            @Override
            public void onResponse(Call<List<MapleMap>> call, Response<List<MapleMap>> response) {
                Log.v(TAG, call.request().toString());
                if (response.isSuccessful()) {
                    Log.v(TAG, response.body().toString());
                    ArrayList<String> list = new ArrayList<>();
                    mapList = response.body();
                    for (MapleMap map : mapList) {
                        list.add(map.getTitle());
                    }
                    listAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.list_row, list);
                    listView.setAdapter(listAdapter);
                    progressBar.setVisibility(View.INVISIBLE);
                    addMap.setEnabled(true);
                }
            }

            @Override
            public void onFailure(Call<List<MapleMap>> call, Throwable t) {
                Log.d("Error", t.getMessage());
                Handler handler = new Handler();
                Runnable r = new Runnable() {
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Failed to connect to the server.", Toast.LENGTH_LONG).show();
                        finish();
                    }
                };
                handler.postDelayed(r, 3000);
            }
        });
    }

    private void retrieveMarkersForMap(String mapid) {
        MapleService mapleService = ServiceGenerator.createService(MapleService.class);
        Call<List<MapleMarker>> call = mapleService.getMarkersByMapId(mapid);
        call.enqueue(new Callback<List<MapleMarker>>() {
            @Override
            public void onResponse(Call<List<MapleMarker>> call, Response<List<MapleMarker>> response) {
                Log.v(TAG, call.request().toString());
                if (response.isSuccessful()) {
                    Log.v(TAG, response.body().toString());
                    Session.setMarkers(response.body());
                }
                finish();
            }

            @Override
            public void onFailure(Call<List<MapleMarker>> call, Throwable t) {
                Log.d("Error", t.getMessage());
                Handler handler = new Handler();
                Runnable r = new Runnable() {
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Failed to connect to the server.", Toast.LENGTH_LONG).show();
                        finish();
                    }
                };
                handler.postDelayed(r, 3000);
            }
        });
    }
}
