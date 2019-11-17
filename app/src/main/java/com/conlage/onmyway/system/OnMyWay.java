package com.conlage.onmyway.system;

import android.app.Application;

import com.vk.api.sdk.VK;
import com.vk.api.sdk.VKTokenExpiredHandler;

public class OnMyWay extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        VK.addTokenExpiredHandler(new VKTokenExpiredHandler() {
            @Override
            public void onTokenExpired() {
                // token expired
            }
        });
    }
}
