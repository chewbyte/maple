package com.chewbyte.geogab.activity;

import android.content.Intent;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.chewbyte.geogab.R;
import com.chewbyte.geogab.common.User;
import com.mapbox.mapboxsdk.MapboxAccountManager;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Chris on 24/07/2016.
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

//        // Add code to print out the key hash
//        try {
//            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
//            for (Signature signature : info.signatures) {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                Log.e("MY KEY HASH:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//
//        } catch (NoSuchAlgorithmException e) {
//
//        }

        //Show the Splash screen for a certain amount of time using sleep
        Thread timer = new Thread(){
            public void run(){
                try{
                    // Mapbox access token only needs to be configured once in your app
                    Looper.prepare();
                    MapboxAccountManager.start(getApplicationContext(), getString(R.string.access_token));
                    sleep(1000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }finally{
                    // CHANGE TO LOGIN AFTER DEV
                    Intent openMain = new Intent(getApplicationContext(), MapActivity.class);
                    startActivity(openMain);
                    finish();
                }
            }
        };
        timer.start();
    }
}