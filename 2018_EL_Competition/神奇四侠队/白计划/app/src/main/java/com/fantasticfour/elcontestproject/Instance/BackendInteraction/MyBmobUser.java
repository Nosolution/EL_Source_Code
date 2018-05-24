package com.fantasticfour.elcontestproject.Instance.BackendInteraction;

import com.fantasticfour.elcontestproject.Instance.Ins_Preferences.User;

import cn.bmob.v3.BmobUser;

public class MyBmobUser extends BmobUser {
    private Integer profileState;
    private String avatar;
    public Integer getProfileState(){return profileState;}
    public String getAvatar() {return avatar;}

    public void setProfileState(Integer profileState){this.profileState = profileState;}
    public void setAvatar(String avatar){this.avatar = avatar;}

    MyBmobUser(User user){
        setUsername(user.m_UserName);
        setEmail(user.m_Email);
        setProfileState(user.m_ProfileState.ordinal());
        setPassword(user.m_Password);
        setAvatar(user.m_Avatar);
    }
}
