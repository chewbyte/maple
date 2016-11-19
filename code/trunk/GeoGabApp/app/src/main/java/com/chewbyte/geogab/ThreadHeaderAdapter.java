package com.chewbyte.geogab;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.chewbyte.geogab.activity.MapActivity;
import com.chewbyte.geogab.common.Session;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;

import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Chris on 11/09/2016.
 */
public class ThreadHeaderAdapter extends MapboxMap.MarkerViewAdapter<ThreadHeaderView> {

    private LayoutInflater inflater;

    public ThreadHeaderAdapter(Context context) {
        super(context);
        this.inflater = LayoutInflater.from(context);
    }

    @Nullable
    @Override
    public View getView(@NonNull ThreadHeaderView marker, @NonNull View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {

            convertView = inflater.inflate(R.layout.thread_header, parent, false);
            convertView.setFocusableInTouchMode(false);
        }
        return convertView;
    }
}
