package com.dmitry.reddit.top;

import android.app.Application;

import com.dmitry.reddit.top.presentation.AppResourceManager;

/**
 * Created by dima on 12/6/17.
 */

public class RedditApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppResourceManager.init(getResources());
    }
}
