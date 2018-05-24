package com.apps.easylife.BL;
import android.app.Application;
import android.support.v7.preference.PreferenceManager;

import java.util.concurrent.TimeUnit;
import static com.apps.easylife.BL.PreferenceHelper.*;

public class GoodtimeApplication extends Application {

    private static volatile GoodtimeApplication INSTANCE;
    private static EventBus mBus;
    private static CurrentSessionManager mCurrentSessionManager;

    public static GoodtimeApplication getInstance() {
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;

        mCurrentSessionManager = new CurrentSessionManager(new CurrentSession(TimeUnit.MINUTES.toMillis(
                PreferenceManager.getDefaultSharedPreferences(this)
                        .getInt(WORK_DURATION, 25))));
        mBus = new EventBus();
    }

    public CurrentSession getCurrentSession() {
        return mCurrentSessionManager.getCurrentSession();
    }

    public EventBus getBus() {
        return mBus;
    }

    public CurrentSessionManager getCurrentSessionManager() {
        return mCurrentSessionManager;
    }
}