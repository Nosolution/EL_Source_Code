package com.example.lenovo.elapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;



public class Achievement_Adapter extends RecyclerView.Adapter<Achievement_Adapter.AchievementView>{
    private List<Achievement_item> mItemList;


    public Achievement_Adapter(List<Achievement_item> list) {
        mItemList = list;
    }

    @Override
    public AchievementView onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.achievement_item, viewGroup, false);
        return new AchievementView(view);
    }

    @Override
    public void onBindViewHolder(AchievementView achievementView, int position) {

        achievementView.imageView.setImageResource(mItemList.get(position).getImageId());
        achievementView.textView.setText(mItemList.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    public static class AchievementView extends  RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textView;

        public AchievementView(View itemView){
            super(itemView);
            imageView= (ImageView) itemView.findViewById(R.id.achievement_medal);
            textView= (TextView) itemView.findViewById(R.id.achievement_name);
        }

    }

}
