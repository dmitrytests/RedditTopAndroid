package com.dmitry.reddit.top.presentation.network;


import android.support.annotation.NonNull;
import android.util.Log;

import com.dmitry.reddit.top.domain.model.RecordDataItem;
import com.dmitry.reddit.top.domain.model.TopResponseWrapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by dima on 12/5/17.
 */

public class RedditTopController implements retrofit2.Callback<TopResponseWrapper> {
    public interface TopCallback {
        void onResponse(@NonNull List<RecordDataItem> data);
        void onError(Throwable t);
    }
    public static final String TAG = RedditTopController.class.getCanonicalName();
    private TopCallback callback;
    private String baseUrl;

    public RedditTopController(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public void start(int count, int limit, @NonNull String after, @NonNull TopCallback callback){
        this.callback = callback;
        Gson gson = new GsonBuilder().setLenient().create();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        RedditTopApi apiExecutor = retrofit.create(RedditTopApi.class);
        Call<TopResponseWrapper> call = apiExecutor.loadTop(count,limit, after);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<TopResponseWrapper> call, Response<TopResponseWrapper> response) {
        if(response.isSuccessful()){
            callback.onResponse(response.body().getResponseData());
        }else{
            Log.d(TAG, response.code() + " : "+response.errorBody().toString());
        }
    }

    @Override
    public void onFailure(Call<TopResponseWrapper> call, Throwable t) {
        callback.onError(t);
    }
}
