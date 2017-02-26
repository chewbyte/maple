package com.chewbyte.geogab;

import com.chewbyte.geogab.MapleObject.MapleMap;
import com.chewbyte.geogab.MapleObject.MapleMarker;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

/**
 * Created by Chris on 19/02/2017.
 */

public interface MapleService {

    @GET("maps")
    Call<List<MapleMap>> getAllMaps();

    @GET("markers")
    Call<List<MapleMarker>> getMarkersByMapId(@Query("mapid") String mapid);

    @POST("markers")
    Call<Void> addMarker(@Body MapleMarker marker);
}