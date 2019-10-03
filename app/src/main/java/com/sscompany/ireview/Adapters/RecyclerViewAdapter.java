package com.sscompany.ireview.Adapters;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.sscompany.ireview.Models.*;
import com.sscompany.ireview.R;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import rm.com.longpresspopup.LongPressPopup;
import rm.com.longpresspopup.LongPressPopupBuilder;

import static android.support.constraint.Constraints.TAG;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context mContext;
    private List<MyItem> mData;

    public RecyclerViewAdapter(Context mContext, List<MyItem> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.card_view_show_items, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
        holder.item_title.setText(mData.get(position).getItem_name());

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.image_placeholder);

        Glide.with(mContext).applyDefaultRequestOptions(requestOptions).load(mData.get(position).getItem_cover_photo())
                .into(holder.image_item_thumbnail);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView image_item_thumbnail;
        TextView item_title;

        public MyViewHolder(View itemView) {
            super(itemView);
            image_item_thumbnail = (ImageView) itemView.findViewById(R.id.ItemImage);
            item_title = (TextView) itemView.findViewById(R.id.ItemTitle);
        }
    }

}
