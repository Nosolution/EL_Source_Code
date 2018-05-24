package com.easylife.entity;

import cn.bmob.v3.BmobObject;

public class Focus extends BmobObject {
    private String userId;
    private Integer presetTime;
    private Integer actualTime;
    private Boolean isSuccess;
    private String focusType;

    public Focus() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getPresetTime() {
        return presetTime;
    }

    public void setPresetTime(Integer presetTime) {
        this.presetTime = presetTime;
    }

    public Integer getActualTime() {
        return actualTime;
    }

    public void setActualTime(Integer actualTime) {
        this.actualTime = actualTime;
    }

    public Boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(Boolean success) {
        isSuccess = success;
    }

    public String getFocusType() {
        return focusType;
    }

    public void setFocusType(String focusType) {
        this.focusType = focusType;
    }
}
