package com.mateoj.parsechat.example;

import android.app.Application;

import com.mateoj.parsechat.ParseChat;

/**
 * Created by jose on 9/17/15.
 */
public class ExampleApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ParseChat.Builder parseChat = new ParseChat.Builder(this,
                getString(R.string.parseAppId),
                getString(R.string.parseAppKey));
        ParseChat.initialize(parseChat);
    }
}
