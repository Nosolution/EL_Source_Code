package com.apps.easylife.Main;

import android.arch.lifecycle.Observer;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.apps.easylife.BL.CurrentSession;
import com.apps.easylife.BL.GoodtimeApplication;
import com.apps.easylife.BL.PreferenceHelper;
import com.apps.easylife.BL.SessionType;
import com.apps.easylife.BL.TimerState;
import com.apps.easylife.Settings.SettingsActivity;
import com.apps.easylife.Util.Constants;
import com.apps.easylife.R;
import com.apps.easylife.BL.TimerService;
import com.apps.easylife.Util.IntentWithAction;
import com.apps.easylife.Util.ThemeHelper;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

import static android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
import static com.apps.easylife.BL.PreferenceHelper.ENABLE_SCREEN_ON;
import static com.apps.easylife.BL.PreferenceHelper.THEME;
import static com.apps.easylife.BL.PreferenceHelper.WORK_DURATION;

public class TimerActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener{

    private static final String TAG = TimerActivity.class.getSimpleName();

    private final CurrentSession mCurrentSession = GoodtimeApplication.getInstance().getCurrentSession();
    private AlertDialog mDialog;
    private FullscreenHelper mFullscreenHelper;

    @BindView(R.id.timeLabel) TextView mTimeLabel;
    @BindView(R.id.stopButton) Button mStopButton;
    @BindView(R.id.toolbar) Toolbar mToolbar;
    @OnClick(R.id.timeLabel)
    public void onStartButtonClick() {
        start(SessionType.WORK);
    }

    @OnClick(R.id.stopButton)
    public void onStopButtonClick() {
        stop();
    }

    @OnClick(R.id.settings)
    public void onSettingsClick() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeHelper.setTheme(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(null);

        setupEvents();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        pref.registerOnSharedPreferenceChangeListener(this);
        toggleKeepScreenOn(PreferenceHelper.isScreenOnEnabled());

        toggleFullscreenMode();
    }

    @Override
    protected void onDestroy() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        pref.unregisterOnSharedPreferenceChangeListener(this);
        super.onDestroy();
    }

    private void setupEvents() {
        mCurrentSession.getDuration().observe(TimerActivity.this, new Observer<Long>() {
            @Override
            public void onChanged(@Nullable Long millis) {
                updateTime(millis);
            }
        });
        mCurrentSession.getSessionType().observe(TimerActivity.this, new Observer<SessionType>() {
            @Override
            public void onChanged(@Nullable SessionType sessionType) {
                //TODO: observe SessionType to show an icon
            }
        });

        mCurrentSession.getTimerState().observe(TimerActivity.this, new Observer<TimerState>() {
            @Override
            public void onChanged(@Nullable TimerState timerState) {
                if (timerState == TimerState.PAUSED) {
                    //TODO: animate timer
                    mStopButton.setVisibility(View.VISIBLE);
                } else {
                    //TODO: stop animating timer
                    mStopButton.setVisibility(View.INVISIBLE);
                }
            }
        });

        GoodtimeApplication.getInstance().getBus().getEvents().subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                if (o instanceof Constants.FinishWorkEvent) {
                    showFinishDialog(SessionType.WORK);
                } else if (o instanceof Constants.FinishBreakEvent) {
                    showFinishDialog(SessionType.BREAK);
                } else if (o instanceof Constants.ClearFinishDialogEvent) {
                    if (mDialog != null) {
                        mDialog.cancel();
                    }
                }
            }
        });
    }

    public void updateTime(Long millis) {
        mTimeLabel.setText(Long.toString(TimeUnit.MILLISECONDS.toSeconds(millis)));
        Log.v(TAG, "drawing the time label.");
    }

    public void start(SessionType sessionType) {
        Intent startIntent = new Intent();
        switch (mCurrentSession.getTimerState().getValue()) {
            case INACTIVE:
                startIntent = new IntentWithAction(TimerActivity.this, TimerService.class,
                        sessionType == SessionType.WORK ? Constants.ACTION.START_WORK : Constants.ACTION.START_BREAK);
                break;
            case ACTIVE:
            case PAUSED:
                startIntent = new IntentWithAction(TimerActivity.this, TimerService.class, Constants.ACTION.TOGGLE);
                break;
            default:
                Log.wtf(TAG, "Invalid timer state.");
                break;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(startIntent);
        } else {
            startService(startIntent);
        }
    }

    public void stop() {
        Intent stopIntent = new IntentWithAction(TimerActivity.this, TimerService.class, Constants.ACTION.STOP);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(stopIntent);
        } else {
            startService(stopIntent);
        }
    }

    //TODO: extract strings
    public void showFinishDialog(SessionType sessionType) {

        Log.i(TAG, "Showing the finish dialog.");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (sessionType == SessionType.WORK) {
            builder.setTitle("计划完成")
                    .setPositiveButton("开始休息", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            start(SessionType.BREAK);
                        }
                    })
                    .setNegativeButton("跳过休息", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            start(SessionType.WORK);
                        }
                    })
                    .setNeutralButton("关闭", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            GoodtimeApplication.getInstance().getBus().send(new Constants.ClearNotificationEvent());
                        }
                    })
                    .setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            GoodtimeApplication.getInstance().getBus().send(new Constants.ClearNotificationEvent());
                        }
                    });
        } else {
            builder.setTitle("Break complete")
                    .setPositiveButton("Begin Session", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            start(SessionType.WORK);
                        }
                    })
                    .setNegativeButton("Skip work", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            start(SessionType.BREAK);
                        }
                    })
                    .setNeutralButton("Close", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            GoodtimeApplication.getInstance().getBus().send(new Constants.ClearNotificationEvent());
                        }
                    })
                    .setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            GoodtimeApplication.getInstance().getBus().send(new Constants.ClearNotificationEvent());
                        }
                    });
        }

        mDialog = builder.create();
        mDialog.setCanceledOnTouchOutside(true);
        mDialog.show();
    }

    private void toggleFullscreenMode() {
        if (PreferenceHelper.isFullscreenEnabled()) {
            if (mFullscreenHelper == null) {
                mFullscreenHelper = new FullscreenHelper(findViewById(R.id.main), getSupportActionBar());
            }
        } else {
            if (mFullscreenHelper != null) {
                mFullscreenHelper.disable();
                mFullscreenHelper = null;
            }
        }
    }

    public void toggleKeepScreenOn(boolean enabled) {
        if (enabled) {
            getWindow().addFlags(FLAG_KEEP_SCREEN_ON);
        } else {
            getWindow().clearFlags(FLAG_KEEP_SCREEN_ON);
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        switch (key) {
            case WORK_DURATION:
                if (GoodtimeApplication.getInstance().getCurrentSession().getTimerState().getValue()
                        == TimerState.INACTIVE) {
                    updateTime(TimeUnit.MINUTES.toMillis(PreferenceHelper.getWorkDuration()));
                }
                break;
            case ENABLE_SCREEN_ON:
                toggleKeepScreenOn(PreferenceHelper.isScreenOnEnabled());
                break;
            case THEME:
                recreate();
                break;
            default:
                break;
        }
    }
}
