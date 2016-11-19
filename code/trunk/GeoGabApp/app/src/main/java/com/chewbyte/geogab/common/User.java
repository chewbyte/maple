package com.chewbyte.geogab.common;

/**
 * Created by Chris on 24/07/2016.
 */
public class User extends Unique {

    private int id;
    private String username;
    private int profile_image_id;
    private String name_forename, name_surname;
    private String profile_text;
    private int score;

    public User(int id, String username, String name_forename, String name_surname, int profile_image_id) {

        this.id = id;
        this.username = username;
        this.name_forename = name_forename;
        this.name_surname = name_surname;
        this.score = 0;
        this.profile_text = "I'm crazy!";
        this.profile_image_id = profile_image_id;
    }

    public int getProfileImage() {
        return profile_image_id;
    }

    public int getId() {
        return id;
    }
}
