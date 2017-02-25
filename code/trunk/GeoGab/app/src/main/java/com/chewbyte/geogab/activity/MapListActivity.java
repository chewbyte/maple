package com.chewbyte.geogab.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.chewbyte.geogab.MapleObject.MapleMap;
import com.chewbyte.geogab.MapleService;
import com.chewbyte.geogab.R;
import com.chewbyte.geogab.ServiceGenerator;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.list_toolbar);
        toolbar.setTitle("Select a map");
        setSupportActionBar(toolbar);

        listView = (ListView) findViewById(R.id.list_map);
        progressBar = (ProgressBar) findViewById(R.id.list_progressBar);

        listAllMaps();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
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
                if (response.isSuccessful()) {
                    ArrayList<String> list = new ArrayList<>();
                    for (MapleMap map : response.body()) {
                        list.add(map.getTitle());
                    }
                    listAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.list_row, list);
                    listView.setAdapter(listAdapter);
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }
            @Override
            public void onFailure(Call<List<MapleMap>> call, Throwable t) {
                Log.d("Error", t.getMessage());
                Toast.makeText(getApplicationContext(), "Failed to connect to the server.", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }
}
