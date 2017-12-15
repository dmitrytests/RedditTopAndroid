package com.dmitry.reddit.top.presentation.ui;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.dmitry.reddit.top.R;
import com.dmitry.reddit.top.R2;
import com.dmitry.reddit.top.domain.model.RecordDataItem;
import com.dmitry.reddit.top.presentation.AppResourceManager;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dima on 12/5/17.
 */

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.RecordViewHolder>{

    public interface OnScrolledToBottomListener{
        void onScrolledToBottom();
    }

    public interface OnItemClickListener{
        void onItemClicked(RecordDataItem item);
    }

    private List<RecordDataItem> data;
    private OnScrolledToBottomListener onScrolledToBottomListener;
    private OnItemClickListener onItemClickListener;
    private RequestManager requestManager;

    public RecordAdapter(Context context) {
        data=new LinkedList<>();
        requestManager = Glide.with(context);
    }

    @Override
    public RecordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.record_item, parent, false);
        return new RecordViewHolder(item);
    }

    @Override
    public void onBindViewHolder(RecordViewHolder holder, int position) {
        if(onScrolledToBottomListener !=null && position==data.size()-1){
            onScrolledToBottomListener.onScrolledToBottom();
        }
        final RecordDataItem item = data.get(position);
        if(!TextUtils.isEmpty(item.getThumbUrl())){
            requestManager.load(item.getThumbUrl())
                    .placeholder(R.drawable.placeholder)
                    .into(holder.thimbnail);
        }
        holder.title.setText(item.getTitle());
        holder.age.setText(AppResourceManager.getString(R.string.submitted_text_pattern, item.getFormattedTimeInterval()));
        holder.author.setText(AppResourceManager.getString(R.string.author_text_pattern, item.getAuthor()));
        holder.comments.setText(AppResourceManager.getString(R.string.coments_text_pattern, item.getComments()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener!=null){
                    onItemClickListener.onItemClicked(item);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(@NonNull List<RecordDataItem> data) {
        this.data = data;
    }

    public void setOnScrolledToBottomListener(OnScrolledToBottomListener listener) {
        this.onScrolledToBottomListener = listener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    class RecordViewHolder extends RecyclerView.ViewHolder{
        @BindView(R2.id.record_title_tv)
        TextView title;
        @BindView(R2.id.record_submission_time_tv)
        TextView age;
        @BindView(R2.id.record_author_tv)
        TextView author;
        @BindView(R2.id.record_comments_tv)
        TextView comments;
        @BindView(R2.id.record_thumb_iv)
        ImageView thimbnail;

        public RecordViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
