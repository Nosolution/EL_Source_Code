package com.fantasticfour.elcontestproject.WNoise;

import android.widget.SeekBar;

import com.fantasticfour.elcontestproject.Instance.Instance;

/**
 * Created by 12509 on 2018/4/20.
 */

public class WNoiseVolumeProgressListener implements SeekBar.OnSeekBarChangeListener {
    private long mWnoiseId;

    public WNoiseVolumeProgressListener(long wnoiseId){
        mWnoiseId = wnoiseId;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
        Instance.s_WNoiseController.SetWNoiseVolume(mWnoiseId, progress);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar){

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar){

    }
}
