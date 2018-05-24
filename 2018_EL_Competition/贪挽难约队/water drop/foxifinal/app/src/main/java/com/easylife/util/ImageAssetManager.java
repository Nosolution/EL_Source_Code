package com.easylife.util;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ImageAssetManager {
    List<String> Images = null;
    List<String> sentences = new ArrayList<>();
    String[] files = null;
    AssetManager assets = null;
    InputStream inputStream = null;
    InputStream sentenceIs;


    public void initAssets(Context context) {
        try {
            assets = context.getAssets();         /*获取assets目录*/
            files = assets.list("splash_pic");     /*返回给定路径中所有资源的字符串数组*/
            Images = new ArrayList<>();
            for (String s : files) {
                Images.add("splash_pic/" + s);
            }
            sentenceIs = context.getAssets().open("splash.txt");
            InputStreamReader isr = new InputStreamReader(sentenceIs);
            BufferedReader br = new BufferedReader(isr);
            while (true) {
                String temp = br.readLine();
                if (temp == null) {
                    break;
                }
                sentences.add(temp);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> getImages() {
        return Images;
    }

    public void setImages(List<String> images) {
        Images = images;
    }

    public AssetManager getAssets() {
        return assets;
    }

    public void setAssets(AssetManager assets) {
        this.assets = assets;
    }

    public InputStream getInputStream(int index) {
        try {
            inputStream = assets.open(Images.get(index));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputStream;
    }

    public List<String> getSentences() {
        return sentences;
    }

    public void setSentences(List<String> sentences) {
        this.sentences = sentences;
    }

    public String getSentence(int index) {
        return sentences.get(index);
    }
}
