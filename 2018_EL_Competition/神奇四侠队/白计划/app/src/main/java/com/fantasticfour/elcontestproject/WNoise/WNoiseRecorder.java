package com.fantasticfour.elcontestproject.WNoise;

import android.media.MediaRecorder;
import android.util.Log;

import java.io.File;
import java.io.IOException;

import static android.content.Context.MODE_PRIVATE;
import static com.github.mikephil.charting.charts.Chart.LOG_TAG;

/**
 * Created by 12509 on 2018/5/18.
 */

public class WNoiseRecorder{
    private String mFilePath;
    private String mFileName;
    private MediaRecorder mRecorder;
    private long mElapsedMillis;
    private long mStartingTimeMillis;

    public WNoiseRecorder(String FilePath,String FileName){
        mFilePath = FilePath;
        mFileName = FileName;
    }

    public void startRecording(){
        setFileNameNPath();

        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);//音频源
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mRecorder.setOutputFile(mFilePath); // 设置录音文件的保存路径
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        mRecorder.setAudioChannels(1);
        // 设置录音文件的清晰度
        mRecorder.setAudioSamplingRate(44100);
        mRecorder.setAudioEncodingBitRate(192000);

        try {
            mRecorder.prepare();
            mRecorder.start();
            mStartingTimeMillis = System.currentTimeMillis();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
    }

    public void setFileNameNPath(){
        File f;
        mFilePath = mFilePath + "/" + mFileName + ".mp4";
        f = new File(mFilePath);
    }

    public void stopRecording(){
        mRecorder.stop();

        mElapsedMillis = (System.currentTimeMillis() - mStartingTimeMillis);
        mRecorder.release();

        mRecorder = null;

    }
}
