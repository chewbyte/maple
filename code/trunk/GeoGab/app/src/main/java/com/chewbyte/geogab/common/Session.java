package com.chewbyte.geogab.common;

import com.chewbyte.geogab.R;

import java.util.HashMap;

/**
 * Created by Chris on 24/07/2016.
 */
public abstract class Session {

    public static User getUserById(final int user_id) {
        return users.get(user_id);
    }

    private static Category categorySelected;

    private static String threadTitle = "";

    public static HashMap<Integer,User> users = new HashMap<Integer, User>(){{
        put(1, new User(1,"loco92", "Loco", "Roco", R.drawable.profile3));
        put(2, new User(2,"jcdenton", "JC", "Denton", R.drawable.profile));
        put(3, new User(3,"jvardy", "Jamie", "Vardy", R.drawable.profile2));
        put(4, new User(4,"markd21", "Mark", "Davies", R.drawable.profile4));
    }};

    public static Category getCategorySelected() {
        return categorySelected;
    }

    public static void setCategorySelected(Category categorySelected) {
        Session.categorySelected = categorySelected;
    }

    public static String getThreadTitle() {
        return threadTitle;
    }

    public static void setThreadTitle(String threadTitle) {
        Session.threadTitle = threadTitle;
    }
}