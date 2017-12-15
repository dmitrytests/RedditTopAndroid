package com.dmitry.reddit.top.presentation.contracts;

import android.app.Activity;
import android.content.Intent;
import android.widget.ImageView;

/**
 * Created by dima on 12/14/17.
 */

public interface PreviewContracts {

    public interface Presenter extends BasePresenter{
        void onCreated(Intent intent);
        void onPermissionRelust();
        void onSaveClicked(Activity activity);
    }

    interface View extends BasePresenter.BaseView{
        void showErrorMessage(String message);
        void showPermissionDilaog(String message);
        ImageView getTargetImageView();
        void hideSaveButton();
        void showSaveButton();
    }


}
