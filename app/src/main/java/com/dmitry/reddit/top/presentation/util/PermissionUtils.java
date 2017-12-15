package com.dmitry.reddit.top.presentation.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by dima on 12/13/17.
 */

public class PermissionUtils {

    public static boolean hasStoragePermission(Context context){
        return ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED;
    }

    public static boolean requestStoragePermission(Activity context){
        String permissionName = Manifest.permission.WRITE_EXTERNAL_STORAGE;
        if (!ActivityCompat.shouldShowRequestPermissionRationale(context, permissionName)) {
            String[] list = {permissionName};
            ActivityCompat.requestPermissions(context, list,
                    100);
            return true;
        }
        return false;
    }
}
