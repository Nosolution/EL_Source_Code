package com.fantasticfour.elcontestproject.WNoise;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fantasticfour.elcontestproject.Instance.Ins_WNoise.WNoise;
import com.fantasticfour.elcontestproject.Instance.Instance;
import com.fantasticfour.elcontestproject.R;

import java.util.Collections;
import java.util.List;

/**
 * Created by 12509 on 2018/4/17.
 */

public class WNoiseAdapter extends RecyclerView.Adapter<WNoiseAdapter.ViewHolder>{
    private List<WNoise> mWnoiseList;
    private WNoiseTouchHelperCallback mWNoiseItemTouchHelperCallback;
    private boolean isNormal = true;
    private WNoiseActivity mContext;

    public class DragOnTouchListener implements View.OnTouchListener{
        private int mPosition;
        private DragOnTouchListener(int position){
            mPosition = position;
        }

        @Override
        public boolean onTouch(View v, MotionEvent event){
            if(v.getId() == R.id.Delete){
                mWNoiseItemTouchHelperCallback.SetBeDragEnabled(true);
            }
            return true;
        }
    }

    public class WnoiseNameOnClickListener implements Button.OnClickListener {
        private long mWnoiseId;
        private int mPosition;

        public WnoiseNameOnClickListener(long wnoiseId, int position){
            mWnoiseId = wnoiseId;
            mPosition = position;
        }

        @Override
        public void onClick(View v){
            final Context mContext = v.getContext();
            LayoutInflater inflater = (LayoutInflater) v.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.save_wnoise, null);
            final EditText editText = (EditText) view.findViewById(R.id.scene_name);
            editText.setText(mWnoiseList.get(mPosition).m_Name);
            editText.setHint("Enter the White Noise's name");
            AlertDialog dialog = new AlertDialog.Builder(mContext)
                    .setTitle("修改白噪音名称")//设置对话框的标题
                    .setView(view)
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String content = editText.getText().toString();
                            Toast.makeText(mContext, content, Toast.LENGTH_SHORT).show();
                            Instance.s_WNoiseController.SetWNoiseName(mWnoiseId, content);
                            WNoiseAdapter.this.notifyItemChanged(mPosition);
                            dialog.dismiss();
                        }
                    }).create();
            dialog.show();
        }
    }

    public class DeleteOnClickListener implements View.OnClickListener{
        private int mPosition;
        private DeleteOnClickListener(int position){
            mPosition = position;
        }

        @Override
        public void onClick(View v){
            final Context mContext = v.getContext();
            AlertDialog DeleteDialog = new AlertDialog.Builder(mContext)
                    .setTitle("确认要删除这个白噪音吗")//设置对话框的标题
                    .setMessage("保存场景中的白噪音也会被删除")
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
                            Instance.s_WNoiseController.RemoveWNoise(mWnoiseList.get(mPosition).m_ID);
                            mWnoiseList = Instance.s_WNoiseController.GetWNoiseList();
                            notifyItemRemoved(mPosition);
                            notifyItemRangeChanged(mPosition, mWnoiseList.size() - mPosition);
                            Toast.makeText(mContext,"已删除", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    }).create();
            DeleteDialog.show();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView wnoiseName;
        SeekBar wnoiseProgress;
        Button drag;

        public ViewHolder(View view){
            super(view);
            wnoiseName = (TextView) view.findViewById(R.id.music_name);
            wnoiseProgress = (SeekBar) view.findViewById(R.id.music_progress);
            drag = (Button) view.findViewById(R.id.Delete);
        }

        private void SetDragOnTouchListener(int position){
            if(isNormal){
                drag.setOnTouchListener(new DragOnTouchListener(position));
                drag.setOnClickListener(null);
            }
            else{
                drag.setOnClickListener(new DeleteOnClickListener(position));
                drag.setOnTouchListener(null);
            }
        }
    }

    public void Reset(){
        for(WNoise wNoise : mWnoiseList)
            wNoise.m_Volume = 0;
        notifyDataSetChanged();
    }

    public void Set(List<WNoise> WNoiseList){
        //直接把adapter里面的list改成传进来的list，不知道行不行
        mWnoiseList = WNoiseList;
        notifyDataSetChanged();
    }

    public void AddWNoise(){
        mWnoiseList = Instance.s_WNoiseController.GetWNoiseList();
        notifyDataSetChanged();
    }

    public boolean GetIsNormal(){
        return isNormal;
    }

    public void SetItemTouchHelperCallback(WNoiseTouchHelperCallback callback){
        mWNoiseItemTouchHelperCallback = callback;
    }


    public void ChangePosition(int sourcePos, int targetPos){
        Collections.swap(mWnoiseList,sourcePos,targetPos);
        //Instance.s_WNoiseController.MoveWNoise(mWnoiseList.get(sourcePos).m_ID, mWnoiseList.get(targetPos).m_ID);
        Instance.s_WNoiseController.SwapAdjacentWNoise(mWnoiseList.get(sourcePos).m_ID, mWnoiseList.get(targetPos).m_ID);
        notifyItemMoved(sourcePos, targetPos);
    }

    public void SetRandom(){
        Instance.s_WNoiseController.RandomSetWNoiseVolume();
        notifyDataSetChanged();
    }

    public void ChangeDrage(){
        isNormal = !isNormal;
        notifyDataSetChanged();
    }

    public WNoiseAdapter(List<WNoise> WNoiseList, WNoiseActivity context){
        mWnoiseList = WNoiseList;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_wnoise, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        WNoise wnoise = mWnoiseList.get(position);
        holder.wnoiseName.setText(wnoise.m_Name);
        holder.wnoiseProgress.setProgress(wnoise.m_Volume);
        WNoiseVolumeProgressListener listener=new WNoiseVolumeProgressListener(wnoise.m_ID);
        holder.wnoiseProgress.setOnSeekBarChangeListener(listener);
        holder.SetDragOnTouchListener(position);
        holder.wnoiseName.setOnClickListener(new WnoiseNameOnClickListener(wnoise.m_ID,position));
        if(isNormal){
            holder.drag.setEnabled(true);
            //holder.drag.setText("三");
            holder.drag.setBackground(mContext.getDrawable(R.drawable.triple_line_black));
        }
        else {
            //holder.drag.setText("Delete");
            holder.drag.setBackground(mContext.getDrawable(R.drawable.trash_can_red));
            if(wnoise.m_BeBuiltIn == 1){
                holder.drag.setEnabled(false);
                //holder.drag.setText("can't");
                holder.drag.setBackground(mContext.getDrawable(R.drawable.trash_can_gray));
            }
        }
    }

    @Override
    public int getItemCount(){
        return mWnoiseList.size();
    }
}
