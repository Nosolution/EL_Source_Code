package com.example.administrator.el_done1;

import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

/**
 * Created by Administrator on 2018-5-21.
 */

public class DairyAdapter extends RecyclerView.Adapter<DairyAdapter.ViewHolder>{
    private List<Dairy> mDairyList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        View dairyView;
        RoundedImageView dairyFace_left;
        RoundedImageView getDairyFace_right;
        TextView dairyName;

        public ViewHolder(View view){
            super(view);
            dairyView = view;
            dairyFace_left = (RoundedImageView) view.findViewById(R.id.dairy_face_left);
            getDairyFace_right = (RoundedImageView)view.findViewById(R.id.dairy_face_right);
            dairyName = (TextView) view.findViewById(R.id.dairy_name);
        }
    }
    public DairyAdapter(List<Dairy> dairyList){
        mDairyList = dairyList;
    }
    @Override
    public DairyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dairy_item, parent,false);
        /*ViewHolder holder = new ViewHolder(view);*/

        //add clicking func
        final ViewHolder holder = new ViewHolder(view);
        holder.dairyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Dairy dairy = mDairyList.get(position);
                Toast.makeText(view.getContext(),"turn to dairy page(if completed)",
                        Toast.LENGTH_SHORT).show();
            }
        });
        holder.dairyFace_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Dairy dairy = mDairyList.get(position);
                Toast.makeText(view.getContext(),"faceImage was clicked",
                        Toast.LENGTH_SHORT).show();
            }
        });
        holder.getDairyFace_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Dairy dairy = mDairyList.get(position);
                Toast.makeText(view.getContext(),"faceImage was clicked",
                        Toast.LENGTH_SHORT).show();
            }
        });
        return holder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        Dairy dairy = mDairyList.get(position);
        holder.dairyFace_left.setImageResource(dairy.getDairyFaceId());
        holder.getDairyFace_right.setImageResource(dairy.getDairyFaceId());
        if (dairy.isAtLeft()){
            holder.dairyFace_left.setVisibility(View.VISIBLE);
            holder.getDairyFace_right.setVisibility(View.INVISIBLE);
            holder.dairyName.setGravity(Gravity.LEFT);
        }
        else {
            holder.dairyFace_left.setVisibility(View.INVISIBLE);
            holder.getDairyFace_right.setVisibility(View.VISIBLE);
            holder.dairyName.setGravity((Gravity.RIGHT));
        }
        holder.dairyName.setText(dairy.getName());

    }
    @Override
    public int getItemCount(){
        return mDairyList.size();
    }
}
