package com.sscompany.ireview.Adapters;


import com.sscompany.ireview.Models.*;
import com.sscompany.ireview.R;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context mContext;
    private List<InterfaceItem> mData;

    public RecyclerViewAdapter(Context mContext, List<InterfaceItem> mData) {
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
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.item_title.setText(mData.get(position).getName());
        //holder.image_item_thumbnail.setImageResource(mData.get(position).getCover_photo());
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
