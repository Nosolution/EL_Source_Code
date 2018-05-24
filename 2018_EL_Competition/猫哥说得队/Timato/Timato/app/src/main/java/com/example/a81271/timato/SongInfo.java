package com.example.a81271.timato;

/**
 * Created by 81271 on 2018/5/24.
 */

public class SongInfo
{
        private  Long songDuration;
        private String songName;
        private String songUrl;


        public SongInfo(String SongName, String SongUrl,Long SongDuration) {
            songName = SongName;
            songUrl = SongUrl;
            songDuration=SongDuration;
        }

        public String getSongName() {
            return songName;
        }




        public String getSongUrl() {
            return songUrl;
        }

        public Long getSongDuration() {
            return songDuration;
        }
}
