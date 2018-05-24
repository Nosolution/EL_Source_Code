package com.apps.easylife.BL;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.apps.easylife.Util.Constants;

import io.reactivex.functions.Consumer;

public class TimerService extends Service {

    private static final String TAG = TimerService.class.getSimpleName();

    private CurrentSessionManager mSessionManager;
    private NotificationHelper mNotificationHelper;

    @Override
    public void onCreate() {
        super.onCreate();

        mNotificationHelper = new NotificationHelper(getApplicationContext());
        mSessionManager = GoodtimeApplication.getInstance().getCurrentSessionManager();

        setupEvents();
    }

    @Override
    public synchronized int onStartCommand(final Intent intent, int flags, int startId) {

        switch (intent.getAction()) {
            case Constants.ACTION.START_WORK:
            case Constants.ACTION.SKIP_BREAK:
                onStartEvent(SessionType.WORK);
                break;
            case Constants.ACTION.STOP:
                onStopEvent();
                break;
            case Constants.ACTION.TOGGLE:
                onToggleEvent();
                break;
            case Constants.ACTION.START_BREAK:
                onStartEvent(SessionType.BREAK);
                break;
            case Constants.ACTION.SKIP_WORK:
                onSkipWorkEvent();
                break;
            default:
                break;
        }
        return START_STICKY;
    }

    private void setupEvents() {
        GoodtimeApplication.getInstance().getBus().getEvents().subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                if (o instanceof Constants.FinishWorkEvent) {
                    onFinishEvent(SessionType.WORK);
                } else if (o instanceof Constants.FinishBreakEvent) {
                    onFinishEvent(SessionType.BREAK);
                } else if (o instanceof Constants.UpdateTimerProgressEvent) {
                    updateNotificationProgress();
                } else if (o instanceof Constants.ClearNotificationEvent) {
                    mNotificationHelper.clearNotification();
                }
            }
        });
    }

    private void onStartEvent(SessionType sessionType) {
        GoodtimeApplication.getInstance().getBus().send(new Constants.ClearFinishDialogEvent());
        mSessionManager.startTimer(sessionType);
        startForeground(Constants.NOTIFICATION_ID, mNotificationHelper.createNotification(
                getApplicationContext(), mSessionManager.getCurrentSession()));
    }

    private void onToggleEvent() {
        mSessionManager.toggleTimer();
        startForeground(Constants.NOTIFICATION_ID, mNotificationHelper.createNotification(
                getApplicationContext(), mSessionManager.getCurrentSession()));
    }

    private void onStopEvent() {
        mSessionManager.stopTimer();
        stopForeground(true);
        stopSelf();
    }

    private void onFinishEvent(SessionType sessionType) {
        stopForeground(true);
        stopSelf();
        mNotificationHelper.notifyFinished(getApplicationContext(), sessionType);
    }

    private void onSkipWorkEvent() {
        onStartEvent(SessionType.BREAK);
    }

    private void updateNotificationProgress() {
        mNotificationHelper.updateNotificationProgress(
                mSessionManager.getCurrentSession().getDuration().getValue());
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
