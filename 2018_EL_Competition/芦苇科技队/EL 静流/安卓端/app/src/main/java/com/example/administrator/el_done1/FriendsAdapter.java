package com.example.administrator.el_done1;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

/**
 * Created by Administrator on 2018-5-12.
 */

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.ViewHolder> {
    private List<Friend> mFriendsList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        View friendView;
        RoundedImageView friendFace;
        TextView friendName;

        public ViewHolder(View view){
            super(view);
            friendView = view;
            friendFace = (RoundedImageView) view.findViewById(R.id.friend_face);
            friendFace.setOval(true);
            friendName = (TextView) view.findViewById(R.id.friend_name);
        }
    }
    public FriendsAdapter(List<Friend> friendList){
        mFriendsList = friendList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.friend_item, parent,false);
        /*ViewHolder holder = new ViewHolder(view);*/

        //add clicking func
        final ViewHolder holder = new ViewHolder(view);
        holder.friendView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Friend friend = mFriendsList.get(position);
                Toast.makeText(view.getContext(),"turn to friend page(if completed)",
                        Toast.LENGTH_SHORT).show();
            }
        });
        holder.friendFace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Friend friend = mFriendsList.get(position);
                Toast.makeText(view.getContext(),"faceImage was clicked",
                        Toast.LENGTH_SHORT).show();
            }
        });
        return holder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        Friend friend = mFriendsList.get(position);
        holder.friendFace.setImageResource(friend.getFriendFaceId());
        holder.friendName.setText(friend.getName());
    }
    @Override
    public int getItemCount(){
        return mFriendsList.size();
    }
}
