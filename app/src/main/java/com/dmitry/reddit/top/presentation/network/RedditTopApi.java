package com.dmitry.reddit.top.presentation.network;

import com.dmitry.reddit.top.domain.model.TopResponseWrapper;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by dima on 12/5/17.
 */

public interface RedditTopApi {
    @GET("/top/.json")
    Call<TopResponseWrapper> loadTop(@Query("count") int count, @Query("limit") int limit, @Query("after") String afterName);
}
