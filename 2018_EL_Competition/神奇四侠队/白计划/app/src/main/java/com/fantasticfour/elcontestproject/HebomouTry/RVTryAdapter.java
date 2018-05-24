package com.fantasticfour.elcontestproject.HebomouTry;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.fantasticfour.elcontestproject.Instance.Instance;
import com.fantasticfour.elcontestproject.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RVTryAdapter extends RecyclerView.Adapter<RVTryAdapter.RVTryViewHolder> {
    private RVTryItemTouchHelperCallback m_RVTryItemTouchHelperCallback;
    private List<RVTryItem> m_ItemList;

    public class RVTryBtnDragOnTouchListener implements View.OnTouchListener{
        private int m_Position;
        private RVTryBtnDragOnTouchListener(int position){
            m_Position=position;
        }

        @Override
        public boolean onTouch(View v, MotionEvent event){
            if(v.getId() == R.id.hebomou_cv_try_btn_drag) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        m_RVTryItemTouchHelperCallback.SetBeDragEnabled(true);
                        break;
                    case MotionEvent.ACTION_UP:
                        m_RVTryItemTouchHelperCallback.SetBeDragEnabled(false);
                        break;
                    default:
                        break;
                }
            }
            return true;
        }
    }

    public class RVTryViewHolder extends RecyclerView.ViewHolder{
        private TextView m_tv_Name;
        private Button m_btn_Drag;
        private RVTryViewHolder(View view){
            super(view);
            m_tv_Name = view.findViewById(R.id.hebomou_cv_try_tv_name);
            m_btn_Drag = view.findViewById(R.id.hebomou_cv_try_btn_drag);
        }
        private void SetBtnDragOnTouchListener(int position){
            m_btn_Drag.setOnTouchListener(new RVTryBtnDragOnTouchListener(position));
        }
    }

    public RVTryAdapter(){
        m_ItemList = new ArrayList<>();
        m_ItemList.add(new RVTryItem("第1个"));
        m_ItemList.add(new RVTryItem("第2个"));
        m_ItemList.add(new RVTryItem("第3个"));
        m_ItemList.add(new RVTryItem("第4个"));
        m_ItemList.add(new RVTryItem("第5个"));
    }

    public void Reset(){
        m_ItemList = new ArrayList<>();
        m_ItemList.add(new RVTryItem("1"));
        m_ItemList.add(new RVTryItem("2"));
        m_ItemList.add(new RVTryItem("3"));
        m_ItemList.add(new RVTryItem("4"));
        m_ItemList.add(new RVTryItem("5"));
        m_ItemList.add(new RVTryItem("6"));
        m_ItemList.add(new RVTryItem("7"));
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RVTryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hebomou_rv_try_item, parent, false);
        return new RVTryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RVTryViewHolder holder, int position){
        RVTryItem item = m_ItemList.get(position);
        holder.m_tv_Name.setText(item.m_Name);
        holder.SetBtnDragOnTouchListener(position);
    }

    @Override
    public int getItemCount(){
        return m_ItemList.size();
    }

    public void SetItemTouchHelperCallback(RVTryItemTouchHelperCallback rVTryItemTouchHelperCallback){
        m_RVTryItemTouchHelperCallback = rVTryItemTouchHelperCallback;
    }

    public void ChangePosition(int sourcePos, int targetPos){
        Collections.swap(m_ItemList,sourcePos,targetPos);
        notifyItemMoved(sourcePos, targetPos);
    }

    public void Delete(int sourcePos){
        m_ItemList.remove(sourcePos);
        notifyItemRemoved(sourcePos);
    }
}
