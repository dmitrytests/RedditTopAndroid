package com.dmitry.reddit.top.domain.repository;


import java.util.List;

/**
 * Created by dima on 12/5/17.
 */

public interface Repository<T> {

    interface DataLoadedCallback<T>{
        void onMoreDataLoaded(List<T> dataList);
        void onLoadingError(Throwable t);
    }
    void loadMore(DataLoadedCallback<T> callback);
}
