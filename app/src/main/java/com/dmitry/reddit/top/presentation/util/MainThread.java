package com.dmitry.reddit.top.presentation.util;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by dima on 12/5/17.
 */

public class MainThread {
    private static MainThread instance;

    private Handler handler;

    private MainThread(){
        handler = new Handler(Looper.getMainLooper());
    }

    public static synchronized MainThread getInstance(){
        if(instance==null){
            instance = new MainThread();
        }
        return instance;
    }

    public void post(Runnable runnable){
        handler.post(runnable);
    }
}
