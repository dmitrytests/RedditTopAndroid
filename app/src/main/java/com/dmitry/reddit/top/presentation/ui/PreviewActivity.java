package com.dmitry.reddit.top.presentation.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.dmitry.reddit.top.R;
import com.dmitry.reddit.top.R2;
import com.dmitry.reddit.top.presentation.contracts.PreviewContracts;
import com.dmitry.reddit.top.presentation.contracts.PreviewPresenterImpl;
import com.dmitry.reddit.top.presentation.contracts.TopResultsContracts;
import com.dmitry.reddit.top.presentation.contracts.TopResultsPresenterImpl;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dima on 12/10/17.
 */

public class PreviewActivity extends AppCompatActivity implements PreviewContracts.View{
    @BindView(R2.id.preview_iv)
   ImageView preview;
    @BindView(R2.id.preview_save_btn)
    Button saveButton;
    PreviewContracts.Presenter presenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preview_screen);
        ButterKnife.bind(this);
        presenter = PreviewPresenterImpl.getInstance(this);
        presenter.onCreated(getIntent());
    }

    @OnClick(R2.id.preview_save_btn)
    public void save(View view){
        presenter.onSaveClicked(this);
    }


    @Override
    public ImageView getTargetImageView() {
        return preview;
    }


    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        presenter.destroy();
        super.onDestroy();
    }

    @Override
    public void hideSaveButton() {
        saveButton.setVisibility(View.GONE);
    }

    @Override
    public void showSaveButton() {
        saveButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void showPermissionDilaog(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Warning")
                .setMessage(message)
                .setCancelable(false)
                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .create()
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        presenter.onPermissionRelust();
    }


    @Override
    @NonNull
    public Context getContext() {
        return getApplicationContext();
    }

}
