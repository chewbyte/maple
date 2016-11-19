package com.chewbyte.geogab.common;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.chewbyte.geogab.ChatMessage;
import com.chewbyte.geogab.R;
import com.chewbyte.geogab.ThreadHeaderView;
import com.chewbyte.geogab.ThreadHeaderViewOptions;
import com.chewbyte.geogab.activity.MapActivity;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerView;
import com.mapbox.mapboxsdk.annotations.MarkerViewOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.chewbyte.geogab.common.Category.DEBATE;
import static com.chewbyte.geogab.common.Category.EVENT;

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

    TextView title;

    public TextView getSnippet() {
        return snippet;
    }

    TextView snippet;
    CircleImageView profile_image;

    Context context;

    private List<ChatMessage> chatMessageList = new ArrayList<>();

    public ThreadHeader(LatLng position, String title_str, String snippet_str, Category category, User owner, Context context) {

        this.category = category;
        this.owner = owner;

        ThreadHeaderView marker = (ThreadHeaderView) MapActivity.map.addMarker(new ThreadHeaderViewOptions()
                .position(position));
        marker.hideInfoWindow();

        View view = MapActivity.map.getMarkerViewManager().getView(marker);
        title = (TextView) view.findViewById(R.id.textView);
        snippet = (TextView) view.findViewById(R.id.textView2);
        profile_image = (CircleImageView) view.findViewById(R.id.profile_image);

        title.setText(title_str);
        snippet.setText(snippet_str);
        profile_image.setImageResource(owner.getProfileImage());

        switch(category) {
            case EVENT:
                title.setBackgroundColor(ContextCompat.getColor(context, R.color.color_event));
                profile_image.setBorderColor(ContextCompat.getColor(context, R.color.color_event));
                break;
            case DEBATE:
                title.setBackgroundColor(ContextCompat.getColor(context, R.color.color_debate));
                profile_image.setBorderColor(ContextCompat.getColor(context, R.color.color_debate));
                break;
            case AWARENESS:
                title.setBackgroundColor(ContextCompat.getColor(context, R.color.color_awareness));
                profile_image.setBorderColor(ContextCompat.getColor(context, R.color.color_awareness));
                break;
            case CURIOSITY:
                title.setBackgroundColor(ContextCompat.getColor(context, R.color.color_curiosity));
                profile_image.setBorderColor(ContextCompat.getColor(context, R.color.color_curiosity));
                break;
        }
    }

    public List<ChatMessage> getChatMessageList() {
        return chatMessageList;
    }
}
