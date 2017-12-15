package com.dmitry.reddit.top.domain.repository;

import android.support.annotation.NonNull;

import com.dmitry.reddit.top.domain.model.RecordDataItem;
import com.dmitry.reddit.top.presentation.network.RedditTopController;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by dima on 12/5/17.
 */

public class RecordsRepository implements Repository<RecordDataItem> {
    private static final int DEFAULT_PAGE_ITEMS_SIZE = 10;
    private static Repository<RecordDataItem> instance;
    private LinkedList<RecordDataItem> dataSource;
    private int limit;
    private int count;
    private String lastRecordName;
    private String baseUrl;

    public static Repository<RecordDataItem> getInstance(@NonNull String url) {
        if (instance == null) {
            instance = new RecordsRepository(DEFAULT_PAGE_ITEMS_SIZE, url);
        }
        return instance;
    }

    private RecordsRepository(int limit, @NonNull String baseUrl) {
        dataSource = new LinkedList<>();
        this.baseUrl=baseUrl;
        this.limit = limit;
        lastRecordName="";
    }


    @Override
    public void loadMore(final DataLoadedCallback<RecordDataItem> callback) {
        RedditTopController controller = new RedditTopController(baseUrl);
        controller.start(count, limit, lastRecordName, new RedditTopController.TopCallback() {
            @Override
            public void onResponse(@NonNull List<RecordDataItem> data) {
                count+=data.size();
                lastRecordName = data.get(data.size()-1).getName();
                dataSource.addAll(data);
                callback.onMoreDataLoaded(dataSource);
            }

            @Override
            public void onError(Throwable t) {
                callback.onLoadingError(t);
            }
        });
    }


}
