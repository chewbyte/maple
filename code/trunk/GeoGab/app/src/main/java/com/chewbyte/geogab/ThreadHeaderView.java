package com.chewbyte.geogab;

import com.chewbyte.geogab.common.Category;
import com.chewbyte.geogab.common.User;
import com.mapbox.mapboxsdk.annotations.BaseMarkerViewOptions;
import com.mapbox.mapboxsdk.annotations.MarkerView;

/**
 * Created by Chris on 11/09/2016.
 */
public class ThreadHeaderView extends MarkerView {

    ThreadHeaderViewOptions threadHeaderViewOptions;

    public String getTitle() {
        return threadHeaderViewOptions.getTitle();
    }

    public String getSnippet() {
        return threadHeaderViewOptions.getSnippet();
    }

    public User getOwner() {
        return threadHeaderViewOptions.getOwner();
    }

    public Category getCategory() {
        return threadHeaderViewOptions.getCategory();
    }

    public ThreadHeaderView(ThreadHeaderViewOptions threadHeaderViewOptions) {
        super(threadHeaderViewOptions);
        this.threadHeaderViewOptions = threadHeaderViewOptions;
    }
}
