package com.nju.team.timescapes;

/**
 * Created by ASUS on 2018/5/8.
 */

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

public class mscService extends Service implements MediaPlayer.OnCompletionListener{
    MediaPlayer player;

    private final IBinder binder = new AudioBinder();
    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return binder;
    }
    /**
     * 音乐播放完的时候触发该动作
     */
    @Override
    public void onCompletion(MediaPlayer player) {
        // TODO Auto-generated method stub
        player.start();
        player.setLooping(true);
    }

    public void onCreate(){
        super.onCreate();
        //从raw中获取mp3文件
        player = MediaPlayer.create(this, R.raw.msc);
        player.setOnCompletionListener(this);


    }

    public int onStartCommand(Intent intent, int flags, int startId){
        if(!player.isPlaying()){
            player.start();
        }
        return START_STICKY;
    }

    public void onDestroy(){
        //super.onDestroy();
        if(player.isPlaying()){
            player.stop();
        }
        player.release();
    }

    //定义Binder对象
    class AudioBinder extends Binder{

        //返回Service对象
        mscService getService(){
            return mscService.this;
        }
    }

}
