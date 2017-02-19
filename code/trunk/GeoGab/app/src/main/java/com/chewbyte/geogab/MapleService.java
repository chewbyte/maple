package com.chewbyte.geogab;

import com.chewbyte.geogab.MapleObject.MapleMap;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Chris on 19/02/2017.
 */

public interface MapleService {

    @GET("maps")
    Call<List<MapleMap>> getAllMaps();
}