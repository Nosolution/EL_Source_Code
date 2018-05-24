package com.fantasticfour.elcontestproject.Instance.Ins_Preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.fantasticfour.elcontestproject.Instance.BackendInteraction.BackendDAO;
import com.fantasticfour.elcontestproject.Instance.Instance;

public class Preferences {
    private static final String s_UserNameKey = "UserName";
    private static final String s_EmailKey = "Email";
    private static final String s_ProfileStateKey = "ProfileState";
    private static final String s_AvatarKey = "Avatar";


    private SharedPreferences m_Preferences;
    private boolean m_BeFirstOpen;
    private User m_User;

    public Preferences(Context context){
        m_Preferences = context.getSharedPreferences("Settings", Context.MODE_PRIVATE);
        CheckFirstOpen();
        LoadUser();
    }

    private void CheckFirstOpen(){
        m_BeFirstOpen = m_Preferences.getBoolean("FirstOpen", true);
        if(m_BeFirstOpen) {
            SharedPreferences.Editor editor = m_Preferences.edit();
            editor.putBoolean("FirstOpen", false);
            editor.apply();
        }
    }

    private void LoadUser(){
        m_User = new User();
        m_User.m_UserName = m_Preferences.getString(s_UserNameKey, null);
        m_User.m_Email = m_Preferences.getString(s_EmailKey, null);
        m_User.m_ProfileState = User.ProfileState.values()[m_Preferences.getInt(s_ProfileStateKey, User.ProfileState.NoProfile.ordinal())];
        m_User.m_Avatar = m_Preferences.getString(s_AvatarKey, null);
    }

    public void SetUser(User user){
        m_User = user;
        SharedPreferences.Editor editor = m_Preferences.edit();
        editor.putString(s_UserNameKey, user.m_UserName);
        editor.putString(s_EmailKey, user.m_Email);
        editor.putInt(s_ProfileStateKey, user.m_ProfileState.ordinal());
        editor.putString(s_AvatarKey, user.m_Avatar);
        editor.apply();
    }

    public void Logout(){
        m_User = new User();
        SetUser(m_User);
    }

    public boolean BeFirstOpen(){
        return m_BeFirstOpen;
    }

    public User GetUser(){
        return m_User;
    }

    public User.ProfileState GetProfileState(){
        return m_User.m_ProfileState;
    }

    public void UpdateUserProfile(){
        Instance.s_BackendDAO.UpdateUserProfile(m_User);
    }

    public void SetProfileState(User.ProfileState state){
        m_User.m_ProfileState = state;
        SharedPreferences.Editor editor = m_Preferences.edit();
        editor.putInt(s_ProfileStateKey, m_User.m_ProfileState.ordinal());
        editor.apply();
    }

    public void SetAvatar(String avatar){
        m_User.m_Avatar = avatar;
    }
}
