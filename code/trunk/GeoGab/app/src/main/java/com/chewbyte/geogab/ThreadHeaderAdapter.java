package com.chewbyte.geogab;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mapbox.mapboxsdk.maps.MapboxMap;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Chris on 11/09/2016.
 */
public class ThreadHeaderAdapter extends MapboxMap.MarkerViewAdapter<ThreadHeaderView> {

    private LayoutInflater inflater;

    TextView title;
    TextView snippet;
    CircleImageView profileImage;
    Context context;

    public ThreadHeaderAdapter(Context context) {
        super(context);
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Nullable
    @Override
    public View getView(@NonNull ThreadHeaderView marker, @NonNull View view, @NonNull ViewGroup parent) {
        if (view == null) {

            view = inflater.inflate(R.layout.thread_header, parent, false);
            view.setFocusableInTouchMode(false);
        }

        title = (TextView) view.findViewById(R.id.textView);
        snippet = (TextView) view.findViewById(R.id.textView2);
        profileImage = (CircleImageView) view.findViewById(R.id.profile_image);

        title.setText(marker.getTitle());
        snippet.setText(marker.getSnippet());
        profileImage.setImageResource(marker.getOwner().getProfileImage());

        switch(marker.getCategory()) {
            case EVENT:
                title.setBackgroundColor(ContextCompat.getColor(context, R.color.color_event));
                profileImage.setBorderColor(ContextCompat.getColor(context, R.color.color_event));
                break;
            case DEBATE:
                title.setBackgroundColor(ContextCompat.getColor(context, R.color.color_debate));
                profileImage.setBorderColor(ContextCompat.getColor(context, R.color.color_debate));
                break;
            case AWARENESS:
                title.setBackgroundColor(ContextCompat.getColor(context, R.color.color_awareness));
                profileImage.setBorderColor(ContextCompat.getColor(context, R.color.color_awareness));
                break;
            case CURIOSITY:
                title.setBackgroundColor(ContextCompat.getColor(context, R.color.color_curiosity));
                profileImage.setBorderColor(ContextCompat.getColor(context, R.color.color_curiosity));
                break;
        }

        return view;
    }
}
