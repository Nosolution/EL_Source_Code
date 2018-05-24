package com.fantasticfour.elcontestproject.User;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fantasticfour.elcontestproject.Instance.Tool;
import com.fantasticfour.elcontestproject.R;

import java.io.File;

public class UserSetProfileMethodsAdapter extends RecyclerView.Adapter<UserSetProfileMethodsAdapter.ViewHolder> {
    private UserSetProfileActivity m_Context;
    private String[] m_MethodNames;
    private View.OnClickListener[] m_Listener;

    UserSetProfileMethodsAdapter(UserSetProfileActivity context){
        m_Context = context;
        m_MethodNames = new String[2];
        m_MethodNames[0] = "拍照";
        m_MethodNames[1] = "选择本地文件";

        m_Listener = new View.OnClickListener[2];
        m_Listener[0] = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                // 判断存储卡是否可用，存储照片文件
                if (Tool.HasSdcard()) {
                    intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri
                            .fromFile(new File(m_Context.getExternalCacheDir(), UserSetProfileActivity.IMAGE_FILE_NAME)));
                }

                m_Context.startActivityForResult(intentFromCapture, UserSetProfileActivity.s_CameraRequestCode);
            }
        };
        m_Listener[1] = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentFromGallery = new Intent();
                // 设置文件类型
                intentFromGallery.setType("image/*");
                intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
                m_Context.startActivityForResult(intentFromGallery, UserSetProfileActivity.s_FileRequestCode);
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView m_tv_Name;
        ViewHolder(View v){
            super(v);
            m_tv_Name = v.findViewById(R.id.user_tv_item_activity_set_profile_methods_name);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item_activity_set_profile_methods, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        holder.m_tv_Name.setText(m_MethodNames[position]);
        holder.itemView.setOnClickListener(m_Listener[position]);
    }

    @Override
    public int getItemCount(){
        return m_MethodNames.length;
    }
}
