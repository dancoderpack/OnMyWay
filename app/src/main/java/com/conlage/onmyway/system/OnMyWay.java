package com.conlage.onmyway.system;

import android.app.Application;
import android.content.SharedPreferences;

import com.vk.api.sdk.VK;
import com.vk.api.sdk.VKTokenExpiredHandler;

public class OnMyWay extends Application {

    private static OnMyWay onMyWay;
    public Config.Group group1, group2, group3, group4;

    @Override
    public void onCreate() {
        super.onCreate();
        onMyWay = this;
        VK.addTokenExpiredHandler(() -> {
            // token expired
        });

    }

    public void setServiceWorked(boolean isWorked){
        SharedPreferences sharedPreferences = getSharedPreferences("ServiceWorked", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isWorked", isWorked);
        editor.apply();
    }

    public boolean isServiceWorked(){
        SharedPreferences sharedPreferences = getSharedPreferences("ServiceWorked", MODE_PRIVATE);
        return sharedPreferences.getBoolean("isWorked", false);
    }

    public static OnMyWay getInstance(){
        return onMyWay;
    }
}
