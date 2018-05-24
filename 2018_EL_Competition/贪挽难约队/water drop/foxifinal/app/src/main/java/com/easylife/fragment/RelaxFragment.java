package com.easylife.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ClipDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.easylife.activity.R;
import com.easylife.entity.Relax;
import com.easylife.entity.User;
import com.easylife.util.BgMusicAssetsManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;


public class RelaxFragment extends Fragment {
    public ImageView relaxBackground;
    public ImageView musicProgress;
    public Button play;
    public Button backToFocus;
    public ClipDrawable clipDrawable;
    public Timer timer = new Timer();
    public Handler handler;

    private MediaPlayer player = new MediaPlayer();
    private BgMusicAssetsManager musicAssetsManager;
    private String[] picNames;
    private List<String> images = new ArrayList<>();
    private AssetManager manager;
    private InputStream fis;
    private long musicDuration = 0;
    private int musicNum = 1;
    private int timeRecord = 0;

    private boolean isPrepared = false;
    private boolean isPlayable = false;
    private View.OnClickListener playableListener = v -> {
        if (!isPrepared) {
            Toast.makeText(getContext(), "正在缓冲，请稍候...", Toast.LENGTH_SHORT).show();
            return;
        }
        if (player.isPlaying()) {
            player.pause();
            play.setText("继续播放");
            isPlayable = false;
            //显示返回专注按钮，让用户可以中断休息
            backToFocus.setVisibility(View.VISIBLE);
        } else {
            player.start();
            play.setBackground(getResources().getDrawable(R.drawable.music_playing_button));
            Handler handler = new Handler();
            handler.postDelayed(() -> play.setBackground(getResources().getDrawable(R.drawable.music_waiting_button)), 5000);
            play.setText("暂 停");
            isPlayable = true;
            //隐藏返回专注按钮，让用户可以专心欣赏音乐
            backToFocus.setVisibility(View.INVISIBLE);
        }
    };
    private View.OnClickListener unplayableListener = v -> {
        Toast toast = Toast.makeText(getContext(), "现在你已经精力充沛了，马上返回专注，效率会更高！", Toast.LENGTH_LONG);
        toast.show();
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        musicAssetsManager = new BgMusicAssetsManager();
        musicAssetsManager.initAssets(getContext());
        loadMusic();
    }


    //加载背景音乐
    private void loadMusic() {
        try {
            int musicIndex = (int) (Math.random() * musicAssetsManager.getMusics().size());
            player.setDataSource(musicAssetsManager.getMusics().get(musicIndex));
            player.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_entertain, container, false);
        initWidget(view);
        setOnClickEvent();
        return view;
    }

    private void setOnClickEvent() {
        //播放按钮监听事件
        play.setOnClickListener(playableListener);

        //播放结束时回调方法
        player.setOnCompletionListener(mp -> {
            //剩余音乐计数器减1
            musicNum--;
            //保存时间到数据库
            saveRelaxTime(timeRecord);

            if (musicNum == 0) {
                play.setText("休息结束！");
                play.setOnClickListener(unplayableListener);
            } else {
                play.setText("下一首");
            }
            backToFocus.setVisibility(View.VISIBLE);
            play.setBackground(getResources().getDrawable(R.drawable.music_pause_button));

        });

        //播放资源缓冲完成时回调方法
        player.setOnPreparedListener(mp -> {
            isPrepared = true;
            musicDuration = player.getDuration();
            //休息时间累加(单位min)
            timeRecord = (int) (musicDuration / 1000);
        });

        //返回继续专注
        backToFocus.setOnClickListener(v -> {
            getActivity().finish();
        });

    }

    //保存时间到服务器
    private void saveRelaxTime(int timeRecord) {
        //剔除无效数据
        if (timeRecord < 1) {
            return;
        }

        BmobQuery<Relax> query = new BmobQuery<>();
        String userId = getCurrentUser(getContext()).getObjectId();
        query.addWhereEqualTo("userId", userId);
        query.findObjects(new FindListener<Relax>() {
            @Override
            public void done(List<Relax> list, BmobException e) {
                if (list.size() == 0) {
                    Relax relaxRecord = new Relax();
                    relaxRecord.setUserId(userId);
                    relaxRecord.setTime(timeRecord + "");
                    relaxRecord.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if (e == null) {
                                Log.i("save", s);
                            } else {
                                Log.i("save", e.toString());
                            }
                        }
                    });
                } else {
                    Calendar calendar = Calendar.getInstance();//获取系统的日期
                    //年
                    int current_year = calendar.get(Calendar.YEAR);
                    //月
                    int current_month = calendar.get(Calendar.MONTH) + 1;
                    //日
                    int current_day = calendar.get(Calendar.DAY_OF_MONTH);
                    String today = current_year + "-" + (current_month < 10 ? "0" + current_month : current_month) + "-" + (current_day < 10 ? "0" + current_day : current_day);

                    Log.i("today", today);
                    Relax todayRelax = null;
                    for (Relax relax : list) {
                        if (relax.getCreatedAt().contains(today)) {
                            todayRelax = relax;
                            break;
                        }
                    }
                    if (todayRelax != null) {
                        todayRelax.setTime(String.valueOf(Integer.parseInt(todayRelax.getTime()) + timeRecord));
                        todayRelax.update(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    Log.i("update", "successful");
                                } else {
                                    Log.i("update", e.toString());
                                }
                            }
                        });
                    } else {
                        todayRelax = new Relax();
                        todayRelax.setTime(String.valueOf(timeRecord));
                        todayRelax.setUserId(userId);
                        todayRelax.save(new SaveListener<String>() {
                            @Override
                            public void done(String s, BmobException e) {
                                if (e == null) {
                                    Log.i("save", s);
                                } else {
                                    Log.i("save", e.toString());
                                }
                            }
                        });
                    }
                }
            }
        });
    }

    //开始进度条
    private void startMusicProgress() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (clipDrawable.getLevel() <= 10000) {
                    try {
                        handler.sendEmptyMessage((int) (10000.0 * player.getCurrentPosition() / musicDuration));
                    } catch (Exception e) {
                        timer.cancel();
                        timer.purge();
                        timer = null;
                        //继续加载音乐
                        if (musicNum != 0) {
                            loadMusic();
                        }
                    }
                } else {
                    if (timer != null) {
                        timer.cancel();
                        timer.purge();
                        timer = null;
                    }
                }
            }
        }, musicDuration, 20);
    }

    //初始化控件
    private void initWidget(View view) {
        play = view.findViewById(R.id.play_button);
        relaxBackground = view.findViewById(R.id.relax_background_imageview);
        backToFocus = view.findViewById(R.id.back_to_focus_button);
        musicProgress = view.findViewById(R.id.buffer_progress_imageview);

        musicProgress.setImageLevel(0);
        clipDrawable = (ClipDrawable) musicProgress.getDrawable();
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                clipDrawable.setLevel(msg.what);
            }
        };
        startMusicProgress();
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inSampleSize = 3;
        initAssets();
        try {
            fis = manager.open(images.get((int) (Math.random() * images.size())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        relaxBackground.setImageBitmap(BitmapFactory.decodeStream(fis, null, opts));
    }

    //初始化资源文件
    private void initAssets() {
        try {
            manager = Objects.requireNonNull(getContext()).getAssets();
            picNames = manager.list("relax_pic");
            for (String pic : picNames) {
                images.add("relax_pic/" + pic);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setMusicNum(int musicNum) {
        this.musicNum = musicNum;
    }

    public Button getPlay() {
        return play;
    }

    public MediaPlayer getPlayer() {
        return player;
    }

    public boolean isPlayable() {
        return isPlayable;
    }

    //获取本地用户
    public User getCurrentUser(Context context) {
        User currentUser;
        SharedPreferences preferences = context.getSharedPreferences("login_user", Context.MODE_PRIVATE);
        currentUser = new User(
                preferences.getString("username", "null"),
                preferences.getString("nickname", "null"),
                preferences.getString("password", "null"),
                preferences.getString("user_phone", "null"));
        currentUser.setObjectId(preferences.getString("objectID", "null"));
        currentUser.setPassword(preferences.getString("password", "null"));
        currentUser.setAvatarUrl(preferences.getString("avatar", "null"));
        currentUser.setAvatarFileName(preferences.getString("avatar_file_name", "null"));
        return currentUser;
    }
}
