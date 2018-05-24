package com.fantasticfour.elcontestproject.Achievement;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fantasticfour.elcontestproject.Instance.Ins_Achievement.Achievement;
import com.fantasticfour.elcontestproject.Instance.Tool;
import com.fantasticfour.elcontestproject.R;

import java.util.List;

public class AchievementListAdapter extends RecyclerView.Adapter<AchievementListAdapter.ViewHolder> {
    private List<Achievement> m_AchievementList;
    private Context m_Context;

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView m_iv_Img;
        private TextView m_tv_Name;
        private ViewHolder(View view){
            super(view);
            m_iv_Img=view.findViewById(R.id.achievement_iv_item_activity_main_achievement_list_img);
            m_tv_Name=view.findViewById(R.id.achievement_tv_item_activity_main_achievement_list_name);
        }
    }

    public class ItemOnClickListener implements View.OnClickListener{
        private Achievement m_Achievement;
        private ItemOnClickListener(Achievement achievement){
            m_Achievement = achievement;
        }
        @Override
        public void onClick(View view){
            Intent intent = new Intent(m_Context, AchievementDetailsActivity.class);
            intent.putExtra("achievement", m_Achievement);
            m_Context.startActivity(intent);
        }
    }

    public AchievementListAdapter(Context context, List<Achievement> achievementList){
        m_Context = context;
        m_AchievementList = achievementList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.achievement_item_activity_main_achievement_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        Achievement achievement = m_AchievementList.get(position);
        Bitmap oriImg = Tool.GetBitmapFromAssets(m_Context, "Achievement/"+achievement.m_ImgFileName);
        if(achievement.m_BeAchieved==0)
            oriImg = Tool.ToGrayScale(oriImg);
        holder.m_iv_Img.setImageBitmap(oriImg);
        holder.m_tv_Name.setText(achievement.m_Name);
        holder.itemView.setOnClickListener(new ItemOnClickListener(achievement));
    }

    @Override
    public int getItemCount(){
        return m_AchievementList.size();
    }
}
