package Tmp_lib;
/*
*1. To get the source
* a. 用户在应用中事先自带的resource资源
例如：MediaPlayer.create(this, R.raw.test);
b. 存储在SD卡或其他文件路径下的媒体文件
例如：mp.setDataSource("/sdcard/test.mp3");
c. 网络上的媒体文件
例如：mp.setDataSource("http://www.citynorth.cn/music/confucius.mp3");
* */

//尚未实现某一个序列音乐播放
//暂时未考虑service  ——2018.4.18

/*
*一个button同时实现暂停播放和继续播放，需要在activity里加一个private boolean字段IsPause
* 此外，方法play不能够放在“实现暂停、继续”的回调函数或者事件监听器中（不然的话还是会从头开始播放）
* play方法属于确定了mediaplayer的来源，并且播放，请放在有关于音乐选择的地方
* if (IsPause) {
                mediaPlayer = Music_lib.ContinueToPlay(mediaPlayer);
                IsPause = false;
            } else {
                mediaPlayer = Music_lib.pause(mediaPlayer);
                IsPause = true;
            }
*——4.21
* 注：
* */

/*
 * 实现了一个函数Play直接同时播放assets和res内的文件
 * 同时让mediaPlayer的创建完全由一个函数完成
 * ———4.26
 * */

/*
 * 我再也不敢了,一定好好地同步remote，好好地写文档
 * ...orz
 *       ——5.5
 * */

/*
* 还有一个多星期.数数代码也有1600了，但进度还是慢，我觉得已经要完不成了
* 早晨开始心态崩，Ray给我了两道简单的C做做，心情还是不大好。
* Jones又在问我离散，TM我真的菜，真的忘光光了
* 转了个教室，看到了你，你就傻笑了。挺巧的
* 英语课每次讨论都要我自己唱独角戏
*
* 我可能真的不是一个“长”的料。为什么要给自己加这么多组长？
* 社会实践又加了一位人文的妹子。看到她们对这个项目这么期待，我真的没底，也担心失望
* 我其实就是个猪长。不会写策划，不懂得动员，唯一的策略就是压迫组员。
* 我毕竟从来没做过带“长”字的职务。
* 可能我早就习惯了被支使，以及用自己的全力去执行，等到我去要求别人的时候，只会拿要求自己的指标去强加
* 辩队队长，真的是个意外
* EL感觉就是要凉，我也不懂怎么去调动，我应该在最开始就找个厉害的前端来做一下指导的。
* 自己才疏学浅，也根本不懂写前端，写得难看，要不得
* 这天真热，仙二的空调很冷。写不进代码，看不进书。
* 大作业发布了，简单看看要求，草草写了需求与流程
* 今天心情真的不好
*
*
* 晚上走回来的时候，见苍穹星斗
* 你是否还可以记得，那个寒冬，你为我写的：
*
* 方圆公子玉
* 文来抱柱信
* 日升月落
*
* 应该是忘了最好
* 因为日月同光，过于闪耀，我也看不到了你眼中的光
* 时间磨平了你的棱角
*
* 你说的很对：
* 十年，二十年，我也不会再回来
* 可能最后
* 你已死去
* 而我
* 只是经过你的坟墓
*
*——5.15
*
* */



import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.io.IOException;

public class Music_lib {
    /**
     * Get the mediaPlayer,and you have to choose the source of the music.
     */
    public static MediaPlayer GetMediaPlayer() {
        return new MediaPlayer();
    }

    public static MediaPlayer play(Context context, MediaPlayer mediaPlayer, int rawFile) {
        mediaPlayer = playRes(context, mediaPlayer, rawFile);
        return mediaPlayer;
    }

    public static MediaPlayer play(Context context, MediaPlayer mediaPlayer, String source) {
        try {
            mediaPlayer = playAssets(context, mediaPlayer, source);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mediaPlayer;

    }

    public static MediaPlayer playExternalAbsolutePath(MediaPlayer mediaPlayer, String source) {
        try {
            if (mediaPlayer == null) {
                mediaPlayer = Music_lib.GetMediaPlayer();
            }
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.start();
                return mediaPlayer;
            }
            File file = new File(source);
            mediaPlayer.reset();
            mediaPlayer.setDataSource(file.getPath());
            mediaPlayer.prepareAsync();
            MediaPlayer finalMediaPlayer = mediaPlayer;
            mediaPlayer.setOnPreparedListener(mp -> finalMediaPlayer.start());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mediaPlayer;
    }

    /**
     * @param source :The external source(like in the sdcards)
     */
    public static MediaPlayer playExternal(MediaPlayer mediaPlayer, String source) {

        try {
            if (mediaPlayer == null) {
                mediaPlayer = Music_lib.GetMediaPlayer();
            }
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.start();
                return mediaPlayer;
            }
            File file = new File(Environment.getExternalStorageDirectory()
                    , "/music/" + source);
            mediaPlayer.reset();
            mediaPlayer.setDataSource(file.getPath());
            mediaPlayer.prepareAsync();
            MediaPlayer finalMediaPlayer = mediaPlayer;
            mediaPlayer.setOnPreparedListener(mp -> finalMediaPlayer.start());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mediaPlayer;
    }

    public static MediaPlayer ContinueToPlay(MediaPlayer mediaPlayer) {
        mediaPlayer.start();
        return mediaPlayer;
    }

    public static MediaPlayer pause(MediaPlayer mediaPlayer) {
        try {
            if (mediaPlayer == null)
                return null;
            mediaPlayer.pause();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mediaPlayer;
    }

    public static boolean isPlay(MediaPlayer mediaPlayer) {
        return mediaPlayer.isPlaying();
    }

    public static MediaPlayer stop(MediaPlayer mediaPlayer) {
        if (mediaPlayer == null)
            mediaPlayer = new MediaPlayer();
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
//            mediaPlayer.release();
            mediaPlayer.prepareAsync();
        }
        return mediaPlayer;
    }

    /**
     * @param pro :True if you want to play the music loop.
     */
    public static MediaPlayer LoopPlay(Context context, MediaPlayer mediaPlayer,
                                       String source, boolean pro) {
        mediaPlayer.setLooping(pro);
        mediaPlayer.setOnCompletionListener(mp -> {
            if (pro) {
                play(context, mediaPlayer, source);
            } else stop(mediaPlayer);
        });
        return mediaPlayer;
    }

    public static MediaPlayer LoopPlay(Context context, MediaPlayer mediaPlayer,
                                       int rawFile, boolean pro) {
        mediaPlayer.setLooping(pro);
        mediaPlayer.setOnCompletionListener(mp -> {
            if (pro) {
                play(context, mediaPlayer, rawFile);
            } else stop(mediaPlayer);
        });
        return mediaPlayer;
    }

    public static MediaPlayer LoopPlayExternal(MediaPlayer mediaPlayer, String source, boolean pro) {
        mediaPlayer.setLooping(pro);
        mediaPlayer.setOnCompletionListener(mp -> {
            if (pro) playExternal(mediaPlayer, source);
            else stop(mediaPlayer);
        });
        return mediaPlayer;
    }

    public static MediaPlayer LoopPlayExternalAbsolutePath(MediaPlayer mediaPlayer,
                                                           String source, boolean pro) {
        mediaPlayer.setLooping(pro);
        mediaPlayer.setOnCompletionListener(mp -> {
            if (pro) playExternalAbsolutePath(mediaPlayer, source);
            else stop(mediaPlayer);
        });
        return mediaPlayer;
    }

    public static MediaPlayer ChangeToPlayAnotherExternal(MediaPlayer mediaPlayer, String source) {
        mediaPlayer = Music_lib.stop(mediaPlayer);
        mediaPlayer = Music_lib.playExternal(mediaPlayer, source);
        return mediaPlayer;
    }

    public static MediaPlayer ChangeToPlayAnother(Context context, MediaPlayer mediaPlayer
            , String string) {
        mediaPlayer = Music_lib.stop(mediaPlayer);
        mediaPlayer = Music_lib.play(context, mediaPlayer, string);
        return mediaPlayer;
    }

    public static MediaPlayer ChangeToPlayAnother(Context context, MediaPlayer mediaPlayer
            , int rawFile) {
        mediaPlayer = Music_lib.stop(mediaPlayer);
        mediaPlayer = Music_lib.play(context, mediaPlayer, rawFile);
        return mediaPlayer;
    }

    public static MediaPlayer ChangeToPlayAnotherExternalAbsolutePath(MediaPlayer mediaPlayer,
                                                                      String source) {
        mediaPlayer = Music_lib.stop(mediaPlayer);
        mediaPlayer = Music_lib.playExternalAbsolutePath(mediaPlayer, source);
        return mediaPlayer;
    }

    /**
     * @param left:The  left road volume.
     * @param right:The right road volume.
     */
    public static MediaPlayer ChangeVolume(MediaPlayer mediaPlayer, double left, double right) {
        float leftRate = (float) left;
        float rightRate = (float) right;
        mediaPlayer.setVolume(leftRate, rightRate);
        return mediaPlayer;
    }

    /**
     * Get the mediaPlayer from the assets/music.
     *
     * @param context     :The Context to play the music.
     * @param mediaPlayer :The Target mediaPlayer.
     * @param string      :The file name.
     */
    private static MediaPlayer playAssets(Context context, MediaPlayer mediaPlayer, String string)
            throws IOException {
        if (mediaPlayer == null) {
            mediaPlayer = Music_lib.GetMediaPlayer();
        }
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.start();
            return mediaPlayer;
        }
        mediaPlayer.reset();
        AssetManager am = context.getAssets();
        AssetFileDescriptor afd = am.openFd("music/" + string);
        mediaPlayer.setDataSource(afd.getFileDescriptor()
                , afd.getStartOffset(), afd.getLength());
        mediaPlayer.prepareAsync();
        MediaPlayer finalMediaPlayer = mediaPlayer;
        mediaPlayer.setOnPreparedListener(mp -> finalMediaPlayer.start());
        return mediaPlayer;
    }

    private static MediaPlayer playRes(Context context, MediaPlayer mediaPlayer, int rawFile) {
        if (mediaPlayer == null) {
            mediaPlayer = Music_lib.GetMediaPlayer();
        }
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.start();
            return mediaPlayer;
        }
        mediaPlayer.reset();
        try {
            Uri uri = Uri.parse("android.resource://" + context.getPackageName() + "/" + rawFile);
            mediaPlayer.setDataSource(context, uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.prepareAsync();
        MediaPlayer finalMediaPlayer = mediaPlayer;
        mediaPlayer.setOnPreparedListener(mp -> finalMediaPlayer.start());
        return mediaPlayer;
    }
}