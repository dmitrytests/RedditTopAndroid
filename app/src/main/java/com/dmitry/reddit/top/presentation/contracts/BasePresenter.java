package com.dmitry.reddit.top.presentation.contracts;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Created by dima on 12/5/17.
 */

public interface BasePresenter {
    interface BaseView{
        @NonNull
        Context getContext();
    }

    void destroy();
}
