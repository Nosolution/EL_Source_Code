package com.example.a81271.timato;
//This project source is created by Zhaoheng Yin.
//Copyright 2018 Zhaoheng Yin.
//Catbro group Confidential.

/**
 * Created by 81271 on 2018/5/24.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongHolder> {
    public String path;
    private ArrayList<SongInfo> songs = new ArrayList<SongInfo>();
    private Context context;
    private int lastSelectedPosition = -1;
    protected MediaPlayer mediaPlayer;

    public SongAdapter(Context context, ArrayList<SongInfo> songs) {
        this.context = context;
        this.songs = songs;
    }

    public interface OnItemClickListener{
        void onClick( int position);
        void onLongClick( int position);
    }
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener ){
        this. mOnItemClickListener=onItemClickListener;
    }

    @Override
    public SongHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View myView = LayoutInflater.from(context).inflate(R.layout.row_songs,viewGroup,false);
        return new SongHolder(myView);
    }

    @Override
    public void onBindViewHolder(final SongHolder songHolder, final int i) {
        final SongInfo s = songs.get(i);
        songHolder.tvSongName.setText(songs.get(i).getSongName());
        songHolder.selectionState.setChecked(lastSelectedPosition == i);
        if( mOnItemClickListener!= null){
            songHolder.itemView.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onClick(i);
                }
            });
            songHolder. itemView.setOnLongClickListener( new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnItemClickListener.onLongClick(i);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    public class SongHolder extends RecyclerView.ViewHolder {
        TextView tvSongName;
        RadioButton selectionState;
        public SongHolder(View itemView) {
            super(itemView);
            tvSongName = (TextView) itemView.findViewById(R.id.tvSongName);
            selectionState = (RadioButton) itemView.findViewById(R.id.radioButton);
            selectionState.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lastSelectedPosition = getAdapterPosition();
                    notifyDataSetChanged();
                    SharedPreferences sp = context.getSharedPreferences("Music", MODE_PRIVATE);
                    SharedPreferences.Editor editor=sp.edit();
                    editor.putString("Url", songs.get(lastSelectedPosition).getSongUrl());
                    editor.putString("Name", songs.get(lastSelectedPosition).getSongName());
                    editor.putInt("Index",lastSelectedPosition);
//                    editor.putLong("Duration",songs.get(lastSelectedPosition).getSongDuration());
                    editor.commit();

                }
            });
        }
    }


}
