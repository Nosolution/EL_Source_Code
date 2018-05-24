package com.apps.easylife.BL;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

/**
 * 保存计划持续时长，类型和白噪音
 * @see GoodtimeApplication
 */
public class CurrentSession {
    private MutableLiveData<Long> mDuration = new MutableLiveData<>();
    private MutableLiveData<TimerState> mTimerState = new MutableLiveData<>();
    private MutableLiveData<SessionType> mSessionType = new MutableLiveData<>();

    public CurrentSession(long duration) {
        this.mDuration.setValue(duration);
        this.mTimerState.setValue(TimerState.INACTIVE);
        this.mSessionType.setValue(SessionType.WORK);
    }

    public LiveData<Long> getDuration() {
        return mDuration;
    }

    public LiveData<TimerState> getTimerState() {
        return mTimerState;
    }

    public LiveData<SessionType> getSessionType() {
        return mSessionType;
    }

    public void setDuration(long newDuration) {
        mDuration.setValue(newDuration);
    }

    public void setTimerState(TimerState state) {
        mTimerState.setValue(state);
    }

    public void setSessionType(SessionType type) {
        mSessionType.setValue(type);
    }
}
