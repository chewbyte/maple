package com.chewbyte.geogab.common;

import com.chewbyte.geogab.ThreadHeaderView;
import com.chewbyte.geogab.ThreadHeaderViewOptions;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapboxMap;

import java.util.Date;

/**
 * Created by Chris on 24/07/2016.
 */
public class ThreadHeader {

    private int zoom;
    private int score;
    private String addr_short, addr_long;
    private Date date_start, date_expire;
    private float loc_lat, loc_long;
    private float density;
    private Topic topic;
    private boolean isPrivate;
    private Marker marker;
    private User owner;
    private Category category;
    private LatLng position;

    public ThreadHeader(LatLng position, String title_str, String snippet_str, Category category, User owner, MapboxMap map) {

        ThreadHeaderViewOptions options = new ThreadHeaderViewOptions().position(position);

        options.setTitle(title_str);
        options.setSnippet(snippet_str);
        options.setCategory(category);
        options.setOwner(owner);

        ThreadHeaderView marker = (ThreadHeaderView) map.addMarker(options);
        marker.hideInfoWindow();
    }
}
