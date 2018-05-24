package com.hlxx.climber.firstpage.setting;


import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;
import android.support.v7.preference.SwitchPreferenceCompat;
import android.widget.Toast;

import com.hlxx.climber.R;
import com.hlxx.climber.secondpage.ClimbingActivity;
import com.hlxx.climber.secondpage.settings.VibrateSetter;
import com.hlxx.climber.firstpage.setting.SettingActivity;
public class SettingFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        // Load the Preferences from the XML file
        addPreferencesFromResource(R.xml.prefs);
        SwitchPreferenceCompat remindPreference = (SwitchPreferenceCompat) findPreference("remind");
        SwitchPreferenceCompat alwayslightPreference = (SwitchPreferenceCompat) findPreference("alwaysLight");
        PreferenceScreen versionAdapt=(PreferenceScreen)findPreference("key_4");
        remindPreference.
        versionAdapt.setOnPreferenceClickListener(new VersionAdaptPreferenceClickListener());
        remindPreference.setOnPreferenceChangeListener(new RemindonPreferenceChangerListener());
        alwayslightPreference.setOnPreferenceChangeListener(new AlwaysLightonPreferenceChangerListener());

    }


    class RemindonPreferenceChangerListener implements Preference.OnPreferenceChangeListener {
        public boolean onPreferenceChange(Preference preference, Object shockState) {
            VibrateSetter.setVibrate(Boolean.parseBoolean(shockState.toString()));
            return true;
        }
    }

    class AlwaysLightonPreferenceChangerListener implements Preference.OnPreferenceChangeListener {
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            ClimbingActivity.setWillScreenOn(Boolean.parseBoolean(newValue.toString()) );
            return true;
        }
    }
    class VersionAdaptPreferenceClickListener implements Preference.OnPreferenceClickListener{
        public boolean onPreferenceClick(Preference preference){
            Toast.makeText(getActivity(),"现在已经是最新版本啦！",Toast.LENGTH_LONG).show();
            return true;
        }

    }
}



