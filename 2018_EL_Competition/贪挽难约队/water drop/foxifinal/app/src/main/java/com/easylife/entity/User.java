package com.easylife.entity;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

public class User extends BmobUser {
    private String nickname;
    private String pw;
    private String avatarUrl;
    private String avatarFileName;

    public User(){}

    public User(String mobilePhoneNumber, String password) {
        pw = password;
        this.setUsername("el"+mobilePhoneNumber);
        this.setPassword(password);
        this.setMobilePhoneNumber(mobilePhoneNumber);
        this.nickname = mobilePhoneNumber;
    }

    public User(String username, String password, String mobilePhoneNumber) {
        pw = password;
        this.setUsername(username);
        this.setPassword(password);
        this.setMobilePhoneNumber(mobilePhoneNumber);
        this.nickname = username;
    }

    public User(String username, String nickname, String password, String mobilePhoneNumber) {
        pw = password;
        this.setUsername(username);
        this.nickname = nickname;
        this.setPassword(password);
        this.setMobilePhoneNumber(mobilePhoneNumber);
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return pw;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getAvatarFileName() {
        return avatarFileName;
    }

    public void setAvatarFileName(String avatarFileName) {
        this.avatarFileName = avatarFileName;
    }
}
