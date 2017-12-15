package com.dmitry.reddit.top.domain.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dima on 12/5/17.
 */

public class TopResponseWrapper {
    @SerializedName("data")
    private TopResponseDataItem data;

    public List<RecordDataItem> getResponseData(){
        ArrayList<RecordDataItem> result;
        if(data!=null && data.dataItems!=null){
            result = new ArrayList<>(data.dataItems.size());
            for(RedditChildDataItem item : data.dataItems){
                result.add(item.data);
            }
        }else{
            result=new ArrayList<>();
        }
        return result;
    }


     static class TopResponseDataItem {
        @SerializedName("children")
        List<RedditChildDataItem> dataItems;
    }

     static class RedditChildDataItem {
        @SerializedName("data")
        RecordDataItem data;
    }
}
