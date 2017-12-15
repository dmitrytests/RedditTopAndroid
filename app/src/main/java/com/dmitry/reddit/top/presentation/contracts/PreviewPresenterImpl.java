package com.dmitry.reddit.top.presentation.contracts;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.dmitry.reddit.top.R;
import com.dmitry.reddit.top.domain.model.RecordDataItem;
import com.dmitry.reddit.top.domain.repository.RecordsRepository;
import com.dmitry.reddit.top.domain.repository.Repository;
import com.dmitry.reddit.top.presentation.AppResourceManager;
import com.dmitry.reddit.top.presentation.ui.RecordAdapter;
import com.dmitry.reddit.top.presentation.util.MainThread;
import com.dmitry.reddit.top.presentation.util.PermissionUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by dima on 12/14/17.
 */

public class PreviewPresenterImpl implements PreviewContracts.Presenter {
    public static final String TAG = PreviewPresenterImpl.class.getCanonicalName();
    private PreviewContracts.View view;
    private static PreviewPresenterImpl insatnce;

    public static PreviewContracts.Presenter getInstance(@NonNull PreviewContracts.View view){
        if(insatnce==null) {
            insatnce = new PreviewPresenterImpl(view);
        }else{
            insatnce.view = view;
        }
        return insatnce;
    }

    private PreviewPresenterImpl(final PreviewContracts.View view) {
        this.view=view;
    }

    @Override
    public void onCreated(Intent intent) {
        view.hideSaveButton();
        String url = intent.getStringExtra(TopResultsContracts.EXTRA_NAME_URL);
        if(!TextUtils.isEmpty(url)) {
            RequestListener requestListener = new RequestListener() {
                @Override
                public boolean onException(Exception e, Object model, Target target, boolean isFirstResource) {
                    e.printStackTrace();
                    view.showErrorMessage(AppResourceManager.getString(R.string.image_loading_error_pattern, e.getLocalizedMessage()));
                    return false;
                }

                @Override
                public boolean onResourceReady(Object resource, Object model, Target target, boolean isFromMemoryCache, boolean isFirstResource) {
                    view.showSaveButton();
                    return false;
                }
            };
            Glide.with(view.getContext())
                    .load(url)
                    .asBitmap()
                    .listener(requestListener)
                    .placeholder(R.drawable.placeholder)
                    .into(view.getTargetImageView());
        }
    }

    @Override
    public void onSaveClicked(Activity activity) {
        if(!PermissionUtils.hasStoragePermission(view.getContext())){
            if(!PermissionUtils.requestStoragePermission(activity)){
                view.showPermissionDilaog(AppResourceManager.getString(R.string.permissions_required_message));
            }
        }else{
            doSaveImage();
        }
    }

    @Override
    public void onPermissionRelust() {
        if(!PermissionUtils.hasStoragePermission(view.getContext())){
            view.showPermissionDilaog(AppResourceManager.getString(R.string.permissions_required_message));
        }else{
            doSaveImage();
        }
    }

    private void doSaveImage(){
        Bitmap image = ((BitmapDrawable)view.getTargetImageView().getDrawable()).getBitmap();
        String savedImagePath = null;
        String imageFileName = "JPEG_" + System.currentTimeMillis() + ".jpg";
        File storageDir = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                        + "/reddit_img");
        boolean success = true;
        if (!storageDir.exists()) {
            success = storageDir.mkdirs();
        }
        if (success) {
            File imageFile = new File(storageDir, imageFileName);
            savedImagePath = imageFile.getAbsolutePath();
            try {
                OutputStream fOut = new FileOutputStream(imageFile);
                image.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                fOut.flush();
                fOut.close();
                galleryAddPic(savedImagePath);
                view.showErrorMessage("Image stored locally!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private void galleryAddPic(String imagePath) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(imagePath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        view.getContext().sendBroadcast(mediaScanIntent);
    }

    @Override
    public void destroy() {
        view=null;
    }
    
}
