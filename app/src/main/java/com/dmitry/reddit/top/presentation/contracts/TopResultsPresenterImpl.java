package com.dmitry.reddit.top.presentation.contracts;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;

import com.dmitry.reddit.top.R;
import com.dmitry.reddit.top.domain.model.RecordDataItem;
import com.dmitry.reddit.top.domain.repository.RecordsRepository;
import com.dmitry.reddit.top.domain.repository.Repository;
import com.dmitry.reddit.top.presentation.AppResourceManager;
import com.dmitry.reddit.top.presentation.ui.RecordAdapter;
import com.dmitry.reddit.top.presentation.util.MainThread;

import java.util.List;

/**
 * Created by dima on 12/5/17.
 */

public class TopResultsPresenterImpl implements TopResultsContracts.Presenter {
    public static final String TAG = TopResultsPresenterImpl.class.getCanonicalName();
    private TopResultsContracts.View view;
    private RecordAdapter adapter;
    private Repository<RecordDataItem> repository;
    private TopResultsContracts.Router router;

    private static TopResultsPresenterImpl insatnce;

    public static TopResultsContracts.Presenter getInstance(@NonNull TopResultsContracts.View view){
        if(insatnce==null) {
            insatnce = new TopResultsPresenterImpl(view);
        }else{
            insatnce.view = view;
        }
        return insatnce;
    }

    private TopResultsPresenterImpl(final TopResultsContracts.View view) {
        this.view = view;
        router = new TopResultsRouter(view);
        String url= AppResourceManager.getString(R.string.base_api_url);
        if(!TextUtils.isEmpty(url)) {
            repository = RecordsRepository.getInstance(url);
        }else{
            Log.d(TAG, "api url is required");
        }
        adapter = new RecordAdapter(view.getContext());
        adapter.setOnScrolledToBottomListener(new RecordAdapter.OnScrolledToBottomListener() {
            @Override
            public void onScrolledToBottom() {
                onScrolledBottom();
            }
        });
        adapter.setOnItemClickListener(new RecordAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(RecordDataItem item) {
                Log.d(TAG, "url="+item.getSourceUrl());
                 if(!TextUtils.isEmpty(item.getSourceUrl())){
                    router.loadPreview(item.getSourceUrl());
                }
            }
        });
    }


    public void init(){
        onScrolledBottom();
    }

    @NonNull
    @Override
    public RecyclerView.Adapter getAdapter() {
        return adapter;
    }



    @Override
    public void onScrolledBottom() {
        view.showLoadingView();
        if(repository!=null) {
            repository.loadMore(new Repository.DataLoadedCallback<RecordDataItem>() {
                @Override
                public void onMoreDataLoaded(List<RecordDataItem> dataList) {
                    adapter.setData(dataList);
                    updateView();
                }

                @Override
                public void onLoadingError(Throwable error) {
                    error.printStackTrace();
                    view.hideLoadingView();
                    view.showErrorMessage("error happened: " + error.getMessage());

                }
            });
        }

    }



    private void updateView(){
        MainThread.getInstance().post(new Runnable() {
            @Override
            public void run() {
                view.hideLoadingView();
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void destroy() {
        view=null;
    }
}
