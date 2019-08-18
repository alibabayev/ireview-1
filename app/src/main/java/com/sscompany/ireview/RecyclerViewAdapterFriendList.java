package com.sscompany.ireview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sscompany.ireview.Elements.User;

import java.util.List;

public class RecyclerViewAdapterFriendList extends RecyclerView.Adapter<RecyclerViewAdapterFriendList.MyViewHolder> {

    private Context mContext;
    private List<User> mData;

    public RecyclerViewAdapterFriendList(Context mContext, List<User> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.card_view_follower_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.item_names.setText(mData.get(position).getNames());
        holder.item_username.setText(mData.get(position).getUsername());
        holder.item_follower.setText("Following");
        /*
        holder.item_names.setText(mData.get(position).getName());
        holder.item_username.setText(mData.get(position).getName());
        holder.item_follower.setText(mData.get(position).getName());
        holder.item_profile_image.setImageResource(mData.get(position).getThumbnail());
        */
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView item_profile_image;
        TextView item_username;
        TextView item_names;
        Button item_follower;

        public MyViewHolder(View itemView) {
            super(itemView);
            item_profile_image = (ImageView) itemView.findViewById(R.id.ProfilePictureInList);
            item_names = (TextView) itemView.findViewById(R.id.UserFirstLastName);
            item_username = (TextView) itemView.findViewById(R.id.UserUsername);
            item_follower = (Button) itemView.findViewById(R.id.FollowerButtonFriendList);
        }
    }
}
