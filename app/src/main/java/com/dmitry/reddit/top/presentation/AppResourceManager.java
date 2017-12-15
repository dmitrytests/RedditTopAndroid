package com.dmitry.reddit.top.presentation;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by dima on 12/6/17.
 */

public class AppResourceManager {
    private static AppResourceManager instance;
    private Resources resources;

    private AppResourceManager(Resources resources) {
        this.resources = resources;
    }

    public static  void init(@NonNull Resources resources){
        instance = new AppResourceManager(resources);
    }

    @Nullable
    public static String getString(int resId){
        if(instance.resources==null){
            return null;
        }
        return instance.resources.getString(resId);
    }

    @Nullable
    public static String getString(int resId, Object... args){
        if(instance.resources==null){
            return null;
        }
        return instance.resources.getString(resId, args);
    }

}
