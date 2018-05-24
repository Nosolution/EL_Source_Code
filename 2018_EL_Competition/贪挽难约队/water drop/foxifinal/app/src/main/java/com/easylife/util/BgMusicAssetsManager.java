package com.easylife.util;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class BgMusicAssetsManager {
    List<String> musics = null;
    AssetManager assets = null;
    InputStream inputStream = null;


    public void initAssets(Context context) {
        try {
            musics = new ArrayList<>();
            inputStream = context.getAssets().open("music.txt");
            InputStreamReader isr = new InputStreamReader(inputStream);
            BufferedReader br = new BufferedReader(isr);
            while (true) {
                String temp = br.readLine();
                if (temp == null) {
                    break;
                }
                musics.add(temp);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public AssetManager getAssets() {
        return assets;
    }

    public InputStream getInputStream(int index) {
        try {
            inputStream = assets.open(musics.get(index));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputStream;
    }

    public List<String> getMusics() {
        return musics;
    }
}
