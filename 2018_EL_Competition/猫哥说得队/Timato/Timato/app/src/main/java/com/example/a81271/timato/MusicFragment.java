package com.example.a81271.timato;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import android.support.annotation.NonNull;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class MusicFragment extends Fragment {
    String name;
    private ArrayList<SongInfo> songs = new ArrayList<SongInfo>();
    public int current_position;
    RecyclerView recyclerView;
    SongAdapter songAdapter;
    private MainActivity mainActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) context;
    }
    public String GetCurrentName()
    {
        return songs.get(current_position).getSongName();
    }
    public void PLAY(int position)
    {
        if(position<0) {
            current_position = songs.size() - 1;
        }
            else
        {
            if(current_position>=songs.size())
                current_position = 0;
            else
            current_position = position;
        }
        if(current_position<7)
        {
            mainActivity.Core.Pause();
            mainActivity.Core.LoadInternalMusic(songs.get(current_position).getSongName());
            mainActivity.Core.Play();
            mainActivity.bar.Update();
            mainActivity.bar.is_playing = true;
            mainActivity.bar.invalidate();
        }
        else
        {
            mainActivity.Core.Pause();
            mainActivity.Core.LoadUserMusic(songs.get(position).getSongUrl());
            mainActivity.Core.Play();
            mainActivity.bar.Update();
            mainActivity.bar.is_playing = true;
            mainActivity.bar.invalidate();
        }
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.music_fragment, container, false);
        addmusic();
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        songAdapter = new SongAdapter(getContext(), songs);
        recyclerView.setAdapter(songAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.mainActivity);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                linearLayoutManager.getOrientation());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(dividerItemDecoration);

        current_position = 0;

        songAdapter.setOnItemClickListener(new SongAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Toast.makeText(getActivity(),"YES!",Toast.LENGTH_SHORT).show();
                PLAY(position);
            }
            @Override
            public void onLongClick(int position) {
            }
        });
//        final AudioCore audioCore=new AudioCore(getContext());
//        String name= this.getActivity().getSharedPreferences("Music", Context.MODE_PRIVATE).getString("Name", "");
//        if(name.equals(null)){
//            name="River";
//        }
//        Button bt=view.findViewById(R.id.button);
//        final String finalName = name;
//        bt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast toast=Toast.makeText(getContext(), finalName, Toast.LENGTH_LONG);
//                toast.show();
//                audioCore.LoadInternalMusic(finalName);
//                audioCore.Play();
//            }
//        });
        checkUserPermission();
        return view;
    }
    private void addmusic() {
        SongInfo clocktickstd = new SongInfo("ClockTick","", (long) 0);
        songs.add(clocktickstd);
        SongInfo fan = new SongInfo("Fan","", (long) 0);
        songs.add(fan);
        SongInfo night = new SongInfo("Night","", (long) 0);
        songs.add(night);
        SongInfo river=new SongInfo("River","",(long)0);
        songs.add(river);
        SongInfo sea = new SongInfo("Sea","", (long) 0);
        songs.add(sea);
        SongInfo street = new SongInfo("Street","", (long) 0);
        songs.add(street);
        SongInfo wind = new SongInfo("Wind","", (long) 0);
        songs.add(wind);

    }
    private void checkUserPermission(){
        if(Build.VERSION.SDK_INT>=23){
            if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},123);
                return;
            }
        }
        loadSongs();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 123:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    loadSongs();
                }else{
                    Toast.makeText(getContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
                    checkUserPermission();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        }

    }

    private void loadSongs(){
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.IS_MUSIC+"!=0";
        Cursor cursor = getActivity().getContentResolver().query(uri,null,selection,null,null);
        if(cursor != null){
            if(cursor.moveToFirst()){
                do{
                    String name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                    String url = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                    Long duration=cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION));
                    SongInfo s = new SongInfo(name,url,duration);
                    songs.add(s);

                }while (cursor.moveToNext());
            }

            cursor.close();
            songAdapter = new SongAdapter(getContext(), songs);

        }
    }

}