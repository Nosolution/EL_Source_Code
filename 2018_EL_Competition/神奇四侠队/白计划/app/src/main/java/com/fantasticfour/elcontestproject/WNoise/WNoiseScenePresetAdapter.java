package com.fantasticfour.elcontestproject.WNoise;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.fantasticfour.elcontestproject.Instance.Ins_WNoise.WNoisePreset;
import com.fantasticfour.elcontestproject.Instance.Instance;
import com.fantasticfour.elcontestproject.R;

import java.util.List;

/**
 * Created by 12509 on 2018/4/19.
 */

public class WNoiseScenePresetAdapter extends RecyclerView.Adapter<WNoiseScenePresetAdapter.ViewHolder>{
    private List<WNoisePreset> mWnoisePresetList;
    private int targetPosition = 0;//当前选中的preset的position

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView mWNoisePresetName;
        Button mDeleteScene;
        RadioButton mSelectScene;

        public ViewHolder(View view){
            super(view);
            mWNoisePresetName = (TextView) view.findViewById(R.id.WNoisePresetName);
            mDeleteScene = (Button) view.findViewById(R.id.DeleteScene);
            mSelectScene = view.findViewById(R.id.select_wnoise_preset);
        }
    }


    public WNoiseScenePresetAdapter(List<WNoisePreset> WNoisePresetList, WNoisePreset preset){
        mWnoisePresetList = WNoisePresetList;
        targetPosition = mWnoisePresetList.lastIndexOf(preset);
    }

    @Override
    public WNoiseScenePresetAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wnoise_preset_list_item, parent, false);
        WNoiseScenePresetAdapter.ViewHolder holder = new ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(final WNoiseScenePresetAdapter.ViewHolder holder, int position){
        WNoisePreset wNoisePreset = mWnoisePresetList.get(position);
        final int mPosition = position;
        holder.mWNoisePresetName.setText(wNoisePreset.m_Name);
        holder.mDeleteScene.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                final Context mContext = v.getContext();
                AlertDialog DeleteDialog = new AlertDialog.Builder(mContext)
                        .setTitle("确认要删除这个场景吗")//设置对话框的标题
                        //设置对话框的按钮
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Instance.s_WNoiseController.RemovePreset(mWnoisePresetList.get(mPosition).m_ID);
                                mWnoisePresetList.remove(mPosition);
                                notifyItemRemoved(mPosition);
                                notifyItemRangeChanged(mPosition, mWnoisePresetList.size() - mPosition);
                                Toast.makeText(mContext,"已删除", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        }).create();
                DeleteDialog.show();
            }
        });
        //如果是当前场景则不能被删除。
        if(wNoisePreset.m_BeCurrent == 1){
            //Instance.s_WNoiseController.TryPreset(mWnoisePresetList.get(mPosition).m_ID);
            holder.mDeleteScene.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    final Context mContext = v.getContext();
                    AlertDialog DeleteDialog = new AlertDialog.Builder(mContext)
                            .setTitle("当前场景不能被删除！")//设置对话框的标题
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).create();
                    DeleteDialog.show();
                }
            });
        }
        if(position == targetPosition){
            holder.mSelectScene.setChecked(true);
        }else{
            holder.mSelectScene.setChecked(false);
        }
        holder.mSelectScene.setOnClickListener(new PositionOnClickListener(position) {
            @Override
            public void onClick(View v) {
                if(mPosition != targetPosition){
                    holder.mSelectScene.setChecked(true);
                    notifyItemChanged(targetPosition);
                    //Instance.s_WNoiseController.LoadPreset(mWnoisePresetList.get(mPosition).m_ID);
                    Instance.s_WNoiseController.TryPreset(mWnoisePresetList.get(mPosition).m_ID);
                    targetPosition = mPosition;
                }
            }
        });
    }

    @Override
    public int getItemCount(){
        return mWnoisePresetList.size();
    }

    abstract class PositionOnClickListener implements View.OnClickListener{
        private int mPosition;
        public PositionOnClickListener(int Position){
            mPosition = Position;
        }
    }

    public WNoisePreset getChosenPreset(){
        return mWnoisePresetList.get(targetPosition);
    }

}
