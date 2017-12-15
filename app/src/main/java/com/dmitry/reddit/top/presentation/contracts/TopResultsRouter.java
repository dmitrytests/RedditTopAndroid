package com.dmitry.reddit.top.presentation.contracts;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.dmitry.reddit.top.presentation.ui.PreviewActivity;

/**
 * Created by dima on 12/10/17.
 */

public class TopResultsRouter implements TopResultsContracts.Router {
    private BasePresenter.BaseView view;

    public TopResultsRouter(BasePresenter.BaseView view) {
        this.view = view;
    }

    @Override
    public void loadPreview(@NonNull String url) {
        //loadPreviewInBrowser(url);
        loadPreviewInApp(url);
    }

    private void loadPreviewInApp(String url){
        Intent intent = new Intent(view.getContext(), PreviewActivity.class);
        intent.putExtra(TopResultsContracts.EXTRA_NAME_URL, url);
        view.getContext().startActivity(intent);
    }

    private void loadPreviewInBrowser(String url){
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        view.getContext().startActivity(i);
    }
}
