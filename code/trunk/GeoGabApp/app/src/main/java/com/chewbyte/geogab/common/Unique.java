package com.chewbyte.geogab.common;

import java.util.UUID;

/**
 * Created by Chris on 25/07/2016.
 */
public abstract class Unique {

    public UUID uuid;

    public Unique() {
        this.uuid = UUID.randomUUID();
    }

    public UUID getUUID() {
        return uuid;
    }
}
