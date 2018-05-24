package com.fantasticfour.elcontestproject.Instance.Ins_WNoise;

import com.fantasticfour.elcontestproject.Instance.Database.BasicDataNode;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class WNoisePlayer implements SoundPool.OnLoadCompleteListener {
    private Context m_Context;
    private SoundPool m_SoundPool;
    private Map<Integer, WNoise> m_ReversedSoundIDMap;
    private Map<Long, Integer> m_WNoiseSoundIDMap;
    private Map<Long, Integer> m_WNoiseStreamIDMap;
    private Set<Long> m_Started, m_Loaded;
    private boolean m_BeSilent = true;

    private void Start(int sampleID){
        if(m_BeSilent)return;
        WNoise wNoise = m_ReversedSoundIDMap.get(sampleID);
        if(!m_Started.contains(wNoise.m_ID)){
            m_Started.add(wNoise.m_ID);
            int soundID = m_WNoiseSoundIDMap.get(wNoise.m_ID);
            int streamID = m_SoundPool.play(soundID, (float) wNoise.m_Volume / 100.0f, (float) wNoise.m_Volume / 100.0f, 1, -1, 1);
            m_WNoiseStreamIDMap.put(wNoise.m_ID, streamID);
        }
    }

    public WNoisePlayer(Context context, Map<Long, BasicDataNode<WNoise> > wNoiseMap) {
        m_Context = context;
        m_SoundPool=new SoundPool(10, AudioManager.STREAM_MUSIC,0);
        AssetManager assetManager=context.getAssets();
        m_WNoiseStreamIDMap=new TreeMap<>();
        m_ReversedSoundIDMap=new TreeMap<>();
        m_WNoiseSoundIDMap=new TreeMap<>();
        m_Loaded = new TreeSet<>();
        m_Started = new TreeSet<>();
        for(BasicDataNode<WNoise> wNoise : wNoiseMap.values()){
            if(wNoise.BeVirtual())continue;
            try {
                int soundID;
                if(wNoise.m_Data.m_BeBuiltIn==1) {
                    AssetFileDescriptor fd = assetManager.openFd("WNoise/" + wNoise.m_Data.m_FileName);
                    soundID = m_SoundPool.load(fd, 1);
                }
                else{
                    File file = new File(context.getFilesDir(), wNoise.m_Data.m_FileName);
                    soundID = m_SoundPool.load(file.getPath(), 1);
                }
                m_ReversedSoundIDMap.put(soundID, wNoise.m_Data);
                m_WNoiseSoundIDMap.put(wNoise.m_ID, soundID);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        m_SoundPool.setOnLoadCompleteListener(this);
    }
    public void AddWNoise(WNoise wNoise){
        if(wNoise.m_BeBuiltIn == 0){
            File file = new File(m_Context.getFilesDir(), wNoise.m_FileName);
            int soundID = m_SoundPool.load(file.getPath(), 1);
            m_ReversedSoundIDMap.put(soundID, wNoise);
            m_WNoiseSoundIDMap.put(wNoise.m_ID, soundID);
        }
    }
    public void DeleteWNoise(long id){
        if(m_WNoiseSoundIDMap.get(id)==null)return;
        int soundID = m_WNoiseSoundIDMap.remove(id);
        Integer streamID = m_WNoiseStreamIDMap.remove(id);
        if(streamID != null)
            m_SoundPool.stop(streamID);
        m_SoundPool.unload(soundID);
        m_Loaded.remove(id);
        m_Started.remove(id);
        m_ReversedSoundIDMap.remove(soundID);
    }

    public void SetSilent(boolean beSilent){
        m_BeSilent = beSilent;
        if (beSilent)
            m_SoundPool.autoPause();
        else
            m_SoundPool.autoResume();
        if(!BeAllStarted()&&!beSilent){
            for(Map.Entry<Long, Integer> entry : m_WNoiseSoundIDMap.entrySet())
                if(m_Loaded.contains(entry.getKey()))
                    Start(entry.getValue());
        }
    }
    private boolean BeAllStarted(){
        return m_Started.size()==m_Loaded.size();
    }

    public boolean GetSilent(){return m_BeSilent;}

    public void SetVolume(long id, int volume){
        if(m_Started.contains(id))
            m_SoundPool.setVolume(m_WNoiseStreamIDMap.get(id), (float) volume/100.0f, (float)volume/100.0f);
    }

    public void SetVolume(Map<Long, BasicDataNode<WNoise>> wNoiseMap) {
        for(BasicDataNode<WNoise> wNoise : wNoiseMap.values()){
            if(wNoise.BeVirtual())continue;
            if(m_WNoiseSoundIDMap.get(wNoise.m_ID)==null)continue;
            SetVolume(wNoise.m_ID, wNoise.m_Data.m_Volume);
        }
    }

    public void SetVolumeByVolumeMap(Map<Long, Integer> volumeMap) {
        for(Map.Entry<Long, Integer> entry : volumeMap.entrySet()){
            if(m_WNoiseSoundIDMap.get(entry.getKey())==null)continue;
            SetVolume(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
        m_Loaded.add(m_ReversedSoundIDMap.get(sampleId).m_ID);
        Start(sampleId);
    }
}
