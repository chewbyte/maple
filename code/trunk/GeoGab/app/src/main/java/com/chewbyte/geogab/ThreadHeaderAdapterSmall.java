package com.chewbyte.geogab;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mapbox.mapboxsdk.maps.MapboxMap;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Chris on 11/09/2016.
 */
public class ThreadHeaderAdapterSmall extends MapboxMap.MarkerViewAdapter<ThreadHeaderView> {

    private LayoutInflater inflater;

    CircleImageView profileImage;
    Context context;
    ImageView marker_bg;

    public ThreadHeaderAdapterSmall(Context context) {
        super(context);
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Nullable
    @Override
    public View getView(@NonNull ThreadHeaderView marker, @NonNull View view, @NonNull ViewGroup parent) {
        if (view == null) {

            view = inflater.inflate(R.layout.thread_header_small, parent, false);
            view.setFocusableInTouchMode(false);
        }

        profileImage = (CircleImageView) view.findViewById(R.id.profile_image);
        profileImage.setImageResource(marker.getOwner().getProfileImage());
        marker_bg = (ImageView) view.findViewById(R.id.marker_bg);

        switch(marker.getCategory()) {
            case EVENT:
                profileImage.setBorderColor(ContextCompat.getColor(context, R.color.color_event));
                marker_bg.setColorFilter(ContextCompat.getColor(context, R.color.color_event));
                break;
            case DEBATE:
                profileImage.setBorderColor(ContextCompat.getColor(context, R.color.color_debate));
                marker_bg.setColorFilter(ContextCompat.getColor(context, R.color.color_debate));
                break;
            case AWARENESS:
                profileImage.setBorderColor(ContextCompat.getColor(context, R.color.color_awareness));
                marker_bg.setColorFilter(ContextCompat.getColor(context, R.color.color_awareness));
                break;
            case CURIOSITY:
                profileImage.setBorderColor(ContextCompat.getColor(context, R.color.color_curiosity));
                marker_bg.setColorFilter(ContextCompat.getColor(context, R.color.color_curiosity));
                break;
        }

        return view;
    }
}
