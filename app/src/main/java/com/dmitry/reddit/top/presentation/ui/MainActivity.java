package com.dmitry.reddit.top.presentation.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dmitry.reddit.top.R;
import com.dmitry.reddit.top.R2;
import com.dmitry.reddit.top.presentation.contracts.TopResultsContracts;
import com.dmitry.reddit.top.presentation.contracts.TopResultsPresenterImpl;
import com.dmitry.reddit.top.presentation.util.PermissionUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements TopResultsContracts.View {

    private static final String PARCELABLE_LAYOUT_KEY= "layout";

    @BindView(R2.id.reddits_progress)
     ProgressBar progressBar;
    @BindView(R2.id.reddits_list)
    RecyclerView recyclerView;
    private TopResultsContracts.Presenter presenter;



    private void restoreLayoutManager(Bundle savedInstanceState){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        if(savedInstanceState!=null && savedInstanceState.containsKey(PARCELABLE_LAYOUT_KEY)){
            Parcelable state = savedInstanceState.getParcelable(PARCELABLE_LAYOUT_KEY);
            layoutManager.onRestoreInstanceState(state);
        }
        recyclerView.setLayoutManager(layoutManager);
        presenter.init();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(PARCELABLE_LAYOUT_KEY, recyclerView.getLayoutManager().onSaveInstanceState());
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        presenter = TopResultsPresenterImpl.getInstance(this);
        recyclerView.setAdapter(presenter.getAdapter());
        restoreLayoutManager(savedInstanceState);
    }




    @Override
    @NonNull
    public Context getContext() {
        return getApplicationContext();
    }

    @Override
    public void showLoadingView() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingView() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }


    @Override
    protected void onDestroy() {
        presenter.destroy();
        super.onDestroy();
    }
}
