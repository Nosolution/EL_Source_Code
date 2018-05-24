package com.example.a81271.timato;
//This project source is created by Zhaoheng Yin.
//Copyright 2018 Zhaoheng Yin.
//Catbro group Confidential.

/**
 * Created by 81271 on 2018/5/23.
 */

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.provider.MediaStore;
import android.widget.GridLayout;

import java.io.IOException;

/**
 * Created by 81271 on 2018/5/22.
 */

public class InsideAudioCore {
    private Context m_context;
    private MediaPlayer Player;
    private boolean Special;
    private int SongEndurance;
    ///////////////////Info/////////////////
    private boolean is_playing;
    private boolean is_loaded;
    public boolean GetPlayingState()
    {
        return Special;
    }
    public boolean GetLoadState(){return is_loaded;}
    public boolean GetPlayingStatus(){return is_playing;}
    public double GetPlayingPosition()
    {
        if(Special)
            return 0;
        else {
            if(SongEndurance>0)
                return (double) Player.getCurrentPosition() / SongEndurance;
            else
                return 1;
        }
    }

    public void SetPlayingPosition(double ratio)
    {
        if(Special)return;
        else
            Player.seekTo((int)(SongEndurance*ratio));
    }

    public void Play()
    {
        if(is_playing)return;
        is_playing=true;
        Player.start();
    }
    public void Pause()
    {
        Player.pause();
        is_playing=false;
    }

    public InsideAudioCore(Context context)
    {
        m_context = context;
        Player = new MediaPlayer();
        Special = true;
        is_playing = false;
        SongEndurance = 300;
        is_loaded = false;
    }
    public void LoadInternalMusic(String SongName) //当前的音乐是否是很长的白噪声，是的话使用MediaPlayer放
    {
        is_playing = false;
        is_loaded = true;
        Pause();
        switch(SongName)
        {
            case "ClockTick":
            {

                Player = MediaPlayer.create(m_context,R.raw.clocktickstd);
                Player.setLooping(true);
                break;
            }
            case "Fan":
            {
                Player = MediaPlayer.create(m_context,R.raw.fan);
                break;
            }
            case "Night":
            {
                Player = MediaPlayer.create(m_context,R.raw.night);
                break;
            }
            case "Sea":
            {
                Player = MediaPlayer.create(m_context,R.raw.sea);
                break;
            }
            case "Street":
            {
                Player = MediaPlayer.create(m_context,R.raw.street);
                break;
            }
            case "River":
            {
                Player = MediaPlayer.create(m_context,R.raw.river);
                Player.setLooping(true);
                break;
            }
            case "Wind":
            {
                Player = MediaPlayer.create(m_context,R.raw.wind);
                Player.setLooping(true);
                break;
            }
        }


        Special = true;
    }

    public void LoadUserMusic(String Path)
    {

        try
        {
            is_loaded = true;
            Player.setDataSource(Path);
            SongEndurance = Player.getDuration();
            Pause();
            is_playing = false;
            Special = false;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}