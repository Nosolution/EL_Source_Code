package com.example.el_project;


import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

/**
 * 这个类是用于管理音乐播放的，
 * 尽管目前很简单，但未来可能会大量拓展。
 * <p>
 * 如何使用：
 * 实例化一个此类的对象，构造时传入context作为参数，直接对此对象start, pause, stop, restart
 * 播放的音乐直接由此对象选择，可以通过randomSwitch()随机切换
 * <p>
 * 注意：
 * 当当前页面退出或此音乐不再需要播放等情况，请即时在相应的OnDestroy等生命周期管理函数中调用此对象的release方法
 *
 * @author NAiveD
 * @version 1.3
 */
public class MusicController {
    private MediaPlayer mediaPlayer = new MediaPlayer();
    private MusicCollection collection = new MusicCollection();
    private Context context;
    private int musicPlaying;

    /**
     * Instantiates a new Music controller.
     *
     * @param context the context
     */
    MusicController(Context context){
        this.context = context;
        initMusicPlayer();
    }

    private void initMusicPlayer(){
        musicPlaying = collection.getRandomMusic();
        mediaPlayer = MediaPlayer.create(context, musicPlaying);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Log.d("TEST", "onCompletion: MusicComplete");
                randomSwitch();
            }
        });
    }

    /**
     * Is playing boolean.
     * 判断是否正在播放音乐
     *
     * @return the boolean
     */
    public boolean isPlaying(){
        return mediaPlayer.isPlaying();
    }

    /**
     * Random switch.
     * 随机切歌
     */
    public void randomSwitch(){
        musicPlaying = collection.getRandomMusicEx(musicPlaying);
        release();
        mediaPlayer = MediaPlayer.create(context, musicPlaying);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Log.d("TEST", "onCompletion: MusicComplete");
                randomSwitch();
            }
        });
        mediaPlayer.start();
    }

    /**
     * Start.
     * 音乐播放开始，也是恢复暂停
     */
    public void start(){
        if(!mediaPlayer.isPlaying()){
            mediaPlayer.start();
        }
    }

    /**
     * Resume.
     * 从暂停恢复
     */
    public void resume(){
        start();
    }

    /**
     * Pause.
     * 暂停音乐
     */
    public void pause(){
        if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }
    }

    /**
     * Stop.
     * 停止音乐，开始音乐会彻底重新开始
     */
    public void stop(){
        mediaPlayer.reset();
        initMusicPlayer();
    }

    /**
     * Restart.
     * 重新开始音乐
     */
    public void restart(){
        stop();
        start();
    }

    /**
     * Release.
     * 释放播放器资源
     */
    public void release(){
        if (mediaPlayer != null){
            stop();
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }

}
