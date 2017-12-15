package com.dmitry.reddit.top.presentation.contracts;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

/**
 * Created by dima on 12/10/17.
 */

public interface TopResultsContracts {
    public static final String EXTRA_NAME_URL = "com.dmitry.reddit.top.presentation.contracts.EXTRA_NAME_URL";

    public interface Presenter extends BasePresenter{
        void init();
        @NonNull
        RecyclerView.Adapter getAdapter();
        void onScrolledBottom();
    }

    interface View extends BasePresenter.BaseView{
        void showLoadingView();
        void hideLoadingView();
        void showErrorMessage(String message);
    }

    interface Router{
        void loadPreview(@NonNull String url);
    }

    interface Interactor{

    }

}
