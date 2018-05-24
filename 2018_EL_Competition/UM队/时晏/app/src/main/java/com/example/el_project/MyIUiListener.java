package com.example.el_project;

import android.util.Log;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

/**
 *
 * 重写应用腾讯的一个接口
 * 为分享这是必须的
 *
 * @author NAiveD
 * @version 1.0
 * */

public class MyIUiListener implements IUiListener {
    @Override
    public void onComplete(Object o) {

    }

    @Override
    public void onError(UiError uiError) {
        Log.d("SHARE", "onError: Share Error");
    }

    @Override
    public void onCancel() {
    }
}
