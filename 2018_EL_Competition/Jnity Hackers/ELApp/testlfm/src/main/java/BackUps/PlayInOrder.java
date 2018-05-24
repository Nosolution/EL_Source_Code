package BackUps;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.atomic.AtomicReference;

import Managers.MusicManager;

public class PlayInOrder extends BroadcastReceiver {
    private volatile List<String> musicPathList = new LinkedList<>();
    private volatile MediaPlayer mediaPlayer;
    private volatile MusicManager musicManager = MusicManager.getMusicManager();
    private volatile boolean IsFailed = false;
    private volatile boolean IsPause = false;

    @Override
    public void onReceive(Context context, Intent intent) {
        mediaPlayer = musicManager.playExternalAbsolutePath(mediaPlayer, musicPathList.get(0));
        AtomicReference<ListIterator<String>> iterator = new AtomicReference<>(musicPathList.listIterator());
        iterator.get().next();
        if (!IsFailed) {
            mediaPlayer.setOnCompletionListener(mp -> {
                if (!iterator.get().hasNext()) {
                    iterator.set(musicPathList.listIterator());
                }
                mediaPlayer = musicManager.playExternalAbsolutePath(mediaPlayer, iterator.get().next());
                this.IsPause = false;
            });
        }
    }
}
