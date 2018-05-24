package BackUps;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.atomic.AtomicReference;

import Managers.Achievement;
import Managers.ClockManager;
import Managers.CountingUp;
import Managers.FileManager;
import Managers.MusicManager;
import Managers.ScreenManager;
import Managers.Task;
import Managers.TaskManager;
import Managers.TimeManager;


public class WinStrategy {

    private volatile MediaPlayer mediaPlayer;
    private volatile TaskManager taskManager;
    private volatile int MaxDelay;
    private TimeManager timeManager;
    private ClockManager clockManager;
    private ScreenManager lockScreenManager;
    private MusicManager musicManager;
    private FileManager fileManager;
    private Achievement achievement;
    private Task task;
    private List<String> musicPathList;
    private volatile boolean IsPause = true;
    private volatile boolean IsFailed = false;

    private WinStrategy(Context context) {

        this.fileManager = FileManager.getFileManager();
        this.musicManager = MusicManager.getMusicManager();
        this.mediaPlayer = musicManager.GetMediaPlayer();
        this.timeManager = TimeManager.getTimeManager();
        this.clockManager = ClockManager.getClockManager(context);
        this.lockScreenManager = ScreenManager.getScreenManager(context);
        this.IsPause = false;
        this.MaxDelay = 5;
        this.achievement = Achievement.getAchievement(context);
        this.musicPathList = new LinkedList<>();
        this.taskManager = TaskManager.getTaskManager(context);
        setMusicPathList(context, ".mp3", "music");

    }

    public void WinStrategyOn(Context context, Task task) {
        this.task = task;
        Toast.makeText(context, "任务已经开始\n请少侠放下手机 开始静心专注吧~~~", Toast.LENGTH_LONG).show();
        PlayInArray();
        CountingPart(context, MaxDelay, task);
        ScreenListenerPart(context);
    }

    public void setMusicPathList(Context context, String suffix, String targetpath) {
        try {
            File musicBuffFile = fileManager.getAllSameSuffixPath(context, suffix, "/" + targetpath, true);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(musicBuffFile));
            String tmp;
            while ((tmp = bufferedReader.readLine()) != null && musicPathList.size() <= 10) {
                musicPathList.add(tmp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static WinStrategy getWinStrategy(Context context) {
        return new WinStrategy(context);
    }

    private void PlayInArray() {
        mediaPlayer = musicManager.playExternalAbsolutePath(mediaPlayer, musicPathList.get(0));
        AtomicReference<ListIterator<String>> iterator = new AtomicReference<>(musicPathList.listIterator());
        iterator.get().next();
        if (!IsFailed) {
            mediaPlayer.setOnCompletionListener(mp -> {
                if (!iterator.get().hasNext()) {
                    iterator.set(musicPathList.listIterator());
                }
                mediaPlayer = musicManager.playExternalAbsolutePath(mediaPlayer, iterator.get().next());
            });
        }
    }

    private void ScreenListenerPart(Context context) {
        if (IsFailed)
            return;
        ScreenManager l = new ScreenManager(context);
        l.begin(new ScreenManager.ScreenStateListener() {

            @Override
            public void onUserPresent() {
                mediaPlayer = musicManager.pause(mediaPlayer);
                IsPause = true;
                Toast.makeText(context, "     任务进行中 请保持专注\n不要玩手机哦~", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onScreenOn() {
//                Toast.makeText(context, "天干物燥\n小心火烛", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onScreenOff() {
                if (!IsFailed())
                    mediaPlayer = musicManager.ContinueToPlay(mediaPlayer);
                IsPause = false;
            }
        });
    }

    private void CountingPart(Context context, int Max, Task task) {
        @SuppressLint("HandlerLeak") Handler handler = new Handler() {
            //重写handleMessage方法获得子线程传来的数据
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                int ms = msg.arg1;
                if (ms == Max) {
                    Toast.makeText(context, "任务失败", Toast.LENGTH_LONG).show();
                    taskManager.TaskFail(task);
                    mediaPlayer = musicManager.stop(mediaPlayer);
                    IsPause = true;
                    IsFailed = true;
                }
            }
        };
        CountingUp countingUp = CountingUp.getCountingUp(handler, Max);
        new Thread(countingUp).start();
    }

    public boolean IsFailed() {
        return this.IsFailed;
    }

    public int getMaxDelay() {
        return MaxDelay;
    }

    public void setMaxDelay(int maxDelay) {
        MaxDelay = maxDelay;
    }
}
