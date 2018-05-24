package com.easylife.util;

import android.content.Context;
import android.content.SharedPreferences;

public class LoadingSettingsManager {
    public static final String SETTINGS_PREFERENCE = "settings";
    public final int FOCUS_MODE_GENERAL = 0;
    public final int FOCUS_MODE_SPECIAL = 1;
    public final int RELAX_MODE_GENERAL = 0;
    public final int RELAX_MODE_SPECIAL = 1;
    public final int TASK_MODE_REMIND = 0;
    public final int TASK_MODE_NOT_REMIND = 1;
    private SharedPreferences preferences;

    public LoadingSettingsManager(Context context) {
        preferences = context.getSharedPreferences(SETTINGS_PREFERENCE, Context.MODE_PRIVATE);
    }

    public String getFocusModeVale() {
        return preferences.getString("focus_mode", String.valueOf(this.FOCUS_MODE_SPECIAL));
    }

    public String getRelaxModeValue() {
        return preferences.getString("relax_mode", String.valueOf(RELAX_MODE_SPECIAL));
    }

    public String getTaskRemindValue() {
        return preferences.getString("task_remind_mode", String.valueOf(TASK_MODE_REMIND));
    }

    public String getFocusTimeValue() {
        return preferences.getString("focus_time", String.valueOf(25));
    }

    public String getRelaxTimeValue() {
        return preferences.getString("relax_time", String.valueOf(1));
    }

    public SharedPreferences getPreferences() {
        return preferences;
    }
}
