package com.chewbyte.geogab.common;

import android.widget.RadioButton;

import com.chewbyte.geogab.R;
import com.mapbox.mapboxsdk.geometry.LatLng;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Chris on 24/07/2016.
 */
public abstract class Session {

    private static String threadTitle = "";
    private static User localUser;
    private static Category categorySelected;

    public static ThreadHeader getCurrentThread() {
        return currentThread;
    }

    public static void setCurrentThread(ThreadHeader currentThread) {
        Session.currentThread = currentThread;
    }

    private static ThreadHeader currentThread;

    public static HashMap<Integer,User> users = new HashMap<Integer, User>(){{
        put(1, new User(1,"loco92", "Loco", "Roco", R.drawable.profile3));
        put(2, new User(2,"jcdenton", "JC", "Denton", R.drawable.profile));
        put(3, new User(3,"jvardy", "Jamie", "Vardy", R.drawable.profile2));
        put(4, new User(4,"markd21", "Mark", "Davies", R.drawable.profile4));
    }};

    public static User getLocalUser() {
        return localUser;
    }

    public static void setLocalUser(User localUser) {
        Session.localUser = localUser;
    }

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
