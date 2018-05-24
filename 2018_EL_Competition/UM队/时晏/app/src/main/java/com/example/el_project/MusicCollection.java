package com.example.el_project;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The type Music collection.
 * 音乐集，负责管理背景音乐的选择。
 * <p>使用：  实例化一个本类对象，通过两个方法得到一支随机歌曲资源或除特定某一支曲目的随机歌曲资源</p>
 *
 *  @author NAiveD
 *  @version 1.0
 */
public class MusicCollection {
    private List<Integer> musicCollection;
    private Random random;
    private int count;

    /**
     * Instantiates a new Music collection.
     */
    MusicCollection(){
        musicCollection = new ArrayList<Integer>();
        init();
    }

    private void init(){
        musicCollection.add(R.raw.music_choosen_for_app_1);
        musicCollection.add(R.raw.music_choosen_for_app_2);
        musicCollection.add(R.raw.music_choosen_for_app_3);
        musicCollection.add(R.raw.music_choosen_for_app_4);
        musicCollection.add(R.raw.music_choosen_for_app_5);
        musicCollection.add(R.raw.music_choosen_for_app_6);
        musicCollection.add(R.raw.music_choosen_for_app_7);
        musicCollection.add(R.raw.music_choosen_for_app_8);
        musicCollection.add(R.raw.music_choosen_for_app_9);
        musicCollection.add(R.raw.music_choosen_for_app_10);

        count = musicCollection.size();
    }

    /**
     * Get random music ex int.
     * 得到一首除传入曲目以外的歌的res
     *
     * @param Ex the ex 排除的曲目，R包的res
     * @return the int
     */
    public int getRandomMusicEx(int Ex){
        random = new Random();
        int result = 0;
        do {
            int index = random.nextInt(count);
            result = musicCollection.get(index);
        }while (result == Ex);
        return result;
    }

    /**
     * Get random music int.
     * 得到一首随机曲目的res
     *
     * @return the int
     */
//得到一首随机歌
    public int getRandomMusic(){
        random = new Random();
        int result = 0;
        int index = random.nextInt(count);
        result = musicCollection.get(index);
        return result;
    }

}
