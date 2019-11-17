package com.conlage.onmyway.system;

import android.app.Application;

import com.vk.api.sdk.VK;
import com.vk.api.sdk.VKTokenExpiredHandler;

public class OnMyWay extends Application {

    private static OnMyWay onMyWay;
    public Config.Group group1, group2, group3, group4;

    @Override
    public void onCreate() {
        super.onCreate();
        onMyWay = this;
        VK.addTokenExpiredHandler(new VKTokenExpiredHandler() {
            @Override
            public void onTokenExpired() {
                // token expired
            }
        });

    }

    public static OnMyWay getInstance(){
        return onMyWay;
    }
}
