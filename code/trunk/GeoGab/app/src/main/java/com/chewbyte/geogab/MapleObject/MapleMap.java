package com.chewbyte.geogab.MapleObject;

import java.util.List;

/**
 * Created by Chris on 19/02/2017.
 */
public class MapleMap {

    String id;
    String title;
    List<String> markers;
    String userid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getMarkers() {
        return markers;
    }

    public void setMarkers(List<String> markers) {
        this.markers = markers;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
