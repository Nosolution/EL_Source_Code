package com.fantasticfour.elcontestproject.Instance.BackendInteraction;

import android.content.Context;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import com.fantasticfour.elcontestproject.Instance.Ins_Preferences.User;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class BackendDAO {
    private MyBmobUser m_User;
    public interface RegisterCallback{
        void RegisterSuccess();
        void RegisterError(BmobException e);
    }
    public interface LoginCallback{
        void LoginSuccess(MyBmobUser user);
        void LoginError(BmobException e);
    }

    public BackendDAO(Context context) {
        final String appID = "2ccf9ad84ffb876e0195d674243d59c2";
        Bmob.initialize(context, appID);

        //android7.0文件读取
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
    }

    public void RegisterNewAccount(User user, final RegisterCallback callback) {
        MyBmobUser myBombUser = new MyBmobUser(user);
        myBombUser.signUp(new SaveListener<MyBmobUser>() {
            @Override
            public void done(MyBmobUser user, BmobException e){
                if(e == null)
                    callback.RegisterSuccess();
                else
                    callback.RegisterError(e);
            }
        });
    }

    public void Login(User user, final LoginCallback callback){
        MyBmobUser myBombUser = new MyBmobUser(user);
        myBombUser.login(new SaveListener<MyBmobUser>() {
            @Override
            public void done(MyBmobUser user, BmobException e){
                if(e == null) {
                    m_User = user;
                    callback.LoginSuccess(user);
                }
                else
                    callback.LoginError(e);
            }
        });
    }

    public void UpdateUserProfile(User user){
        m_User.setAvatar(user.m_Avatar);
        m_User.setProfileState(user.m_ProfileState.ordinal());
        m_User.update(m_User.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e!=null)
                    Log.d("error", e.toString());
            }
        });
    }
}


