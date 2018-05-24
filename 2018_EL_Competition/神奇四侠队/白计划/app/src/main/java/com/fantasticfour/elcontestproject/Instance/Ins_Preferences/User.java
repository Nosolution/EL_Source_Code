package com.fantasticfour.elcontestproject.Instance.Ins_Preferences;

import java.io.Serializable;

public class User implements Serializable{
    public enum ProfileState{
        NoProfile,
        Normal,
        Uploading,
        UploadFail,
        Downloading
    }


    public String m_UserName;
    public String m_Email;
    public String m_Password;
    public ProfileState m_ProfileState;
    public String m_Avatar;

    public String getM_UserName() {return m_UserName;}
    public String getM_Email() {return m_Email;}
    public String getM_Password() {return m_Password;}
    public ProfileState getM_ProfileState() {return m_ProfileState;}
    public String getM_Avatar() {return m_Avatar;}

    public void setM_Email(String email) {m_Email = email;}
    public void setM_Password(String password) {m_Password = password;}
    public void setM_UserName(String userName) {m_UserName = userName;}
    public void setM_ProfileState(ProfileState profileState) {m_ProfileState = profileState;}
    public void setM_Avatar(String avatar){m_Avatar = avatar;}

    public boolean BeNull(){
        return m_UserName == null;
    }

    public User(){
        m_ProfileState = ProfileState.Normal;
    }

    public User(String userName, String email, String password, ProfileState profileState, String avatar){
        m_UserName = userName;
        m_Email = email;
        m_Password = password;
        m_ProfileState = profileState;
        m_Avatar = avatar;
    }
}
