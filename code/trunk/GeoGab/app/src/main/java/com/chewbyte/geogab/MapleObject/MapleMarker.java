package com.chewbyte.geogab.MapleObject;

import com.chewbyte.geogab.common.Category;

/**
 * Created by Chris on 19/02/2017.
 */
public class MapleMarker {

    String id;
    String title;
    float latitude;
    float longitude;
    String userid;
    String mapid;
    Category category;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) { this.category = category; }

    public String getId() { return id; }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getMapid() {
        return mapid;
    }

    public void setMapid(String mapid) {
        this.mapid = mapid;
    }
}
