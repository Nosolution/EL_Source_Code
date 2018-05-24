package com.fantasticfour.elcontestproject.User;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fantasticfour.elcontestproject.Instance.Ins_Preferences.User;
import com.fantasticfour.elcontestproject.Instance.Instance;
import com.fantasticfour.elcontestproject.Instance.Tool;
import com.fantasticfour.elcontestproject.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.UploadFileListener;

public class UserActivity extends AppCompatActivity {
    public static final String s_ProfileFileNameSuffix = "Profile.png";

    public static final String s_DefaultProfileFilePath = "User/DefaultProfile.png";
    public static final String s_LoginProfileFilePath = "User/LoginProfile.png";
    public static final String s_UploadingProfileFilePath = "User/UploadingProfile.png";
    public static final String s_UploadFailProfileFilePath = "User/UploadFailProfile.png";
    public static final String s_DownloadingProfileFilePath = "User/DownloadingProfile.png";

    public static final int s_LoginRequestCode = 100;
    public static final int s_SetProfileRequestCode = 101;
    private ImageView m_iv_Profile;
    private TextView m_tv_UserName;
    private TextView m_tv_Email;
    private TextView m_tv_Register;
    private ProgressBar m_pb_UploadProfile;

    private View.OnClickListener m_OnClickSetProfile;
    private View.OnClickListener m_OnClickReUpload;
    private View.OnClickListener m_OnClickLogin;
    private View.OnClickListener m_OnClickLogout;
    private AlertDialog.Builder m_LogoutDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_main);
        DealWithTitle();
        InitViews();
        InitOnClickListener();
        DealWithUser();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_CANCELED)return;

        if(requestCode == s_LoginRequestCode) {
            Instance.s_Preferences.SetUser((User) data.getSerializableExtra("user"));
            if(Instance.s_Preferences.GetProfileState() == User.ProfileState.Normal)
                Instance.s_Preferences.SetProfileState(User.ProfileState.Downloading);
            DealWithUser();
        }
        else if(requestCode == s_SetProfileRequestCode){
            Instance.s_Preferences.SetProfileState(User.ProfileState.Uploading);
            DealWithUser();
        }
    }

    private void DealWithTitle(){
        //返回键
        Button btn_Back = findViewById(R.id.title_leftbutton);
        btn_Back.setBackground(getDrawable(R.drawable.left_arrow));
        btn_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //右侧按钮禁用
        Button btn_Right = findViewById(R.id.title_rightbutton);
        btn_Right.setClickable(false);

        //中间文本
        TextView tv_Middle = findViewById(R.id.title_middletext);
        tv_Middle.setText("我的");
    }
    private void InitViews(){
        m_iv_Profile = findViewById(R.id.user_iv_activity_main_profile);
        m_tv_UserName = findViewById(R.id.user_tv_activity_main_username);
        m_tv_Email = findViewById(R.id.user_tv_activity_main_email);
        m_tv_Register = findViewById(R.id.user_tv_activity_main_register);
        m_pb_UploadProfile = findViewById(R.id.user_pb_activity_main_upload_profile);
    }
    private void InitOnClickListener(){
        m_OnClickSetProfile = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserActivity.this, UserSetProfileActivity.class);
                startActivityForResult(intent, s_SetProfileRequestCode);
            }
        };

        m_OnClickReUpload = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_iv_Profile.setOnClickListener(null);
                m_iv_Profile.setImageBitmap(Tool.GetBitmapFromAssets(UserActivity.this, s_UploadingProfileFilePath));
                m_pb_UploadProfile.setVisibility(View.VISIBLE);
                UploadProfile();
            }
        };

        m_OnClickLogin = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserActivity.this, UserLoginActivity.class);
                startActivityForResult(intent, s_LoginRequestCode);
            }
        };

        m_LogoutDialog = new AlertDialog.Builder(UserActivity.this, R.style.AppTheme_Dialog)
                .setMessage("您确定要退出吗？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Logout();
                    }
                })
                .setNegativeButton("取消", null);

        m_OnClickLogout = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_LogoutDialog.show();
            }
        };
    }
    private void DownloadProfile(){
        BmobFile file = new BmobFile(GetProfileFileName(), "", Instance.s_Preferences.GetUser().m_Avatar);
        file.download(new File(getFilesDir(), GetProfileFileName()), new DownloadFileListener() {
            @Override
            public void done(String s, BmobException e) {
                if(e == null){
                    Instance.s_Preferences.SetProfileState(User.ProfileState.Normal);
                }
                else {
                    Toast.makeText(UserActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
                DealWithUser();
            }

            @Override
            public void onProgress(Integer integer, long l) {
                m_pb_UploadProfile.setProgress(integer);
            }
        });
    }
    private void DealWithUser(){
        User user = Instance.s_Preferences.GetUser();
        m_pb_UploadProfile.setVisibility(View.INVISIBLE);
        if(!user.BeNull()){
            switch (user.m_ProfileState){
                case Normal:
                    File file = new File(getFilesDir(), GetProfileFileName());
                    m_iv_Profile.setOnClickListener(m_OnClickSetProfile);
                    m_iv_Profile.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
                    m_pb_UploadProfile.setVisibility(View.INVISIBLE);
                    break;
                case NoProfile:
                    m_iv_Profile.setOnClickListener(m_OnClickSetProfile);
                    m_iv_Profile.setImageBitmap(Tool.GetBitmapFromAssets(this, s_DefaultProfileFilePath));
                    m_pb_UploadProfile.setVisibility(View.INVISIBLE);
                    break;
                case Uploading:
                    m_iv_Profile.setOnClickListener(null);
                    m_iv_Profile.setImageBitmap(Tool.GetBitmapFromAssets(this, s_UploadingProfileFilePath));
                    m_pb_UploadProfile.setVisibility(View.VISIBLE);
                    UploadProfile();
                    break;
                case UploadFail:
                    m_iv_Profile.setOnClickListener(m_OnClickReUpload);
                    m_iv_Profile.setImageBitmap(Tool.GetBitmapFromAssets(this, s_UploadFailProfileFilePath));
                    m_pb_UploadProfile.setVisibility(View.INVISIBLE);
                    break;
                case Downloading:
                    m_iv_Profile.setOnClickListener(null);
                    m_iv_Profile.setImageBitmap(Tool.GetBitmapFromAssets(this, s_DownloadingProfileFilePath));
                    m_pb_UploadProfile.setVisibility(View.VISIBLE);
                    DownloadProfile();
                    break;
            }
            m_tv_UserName.setText(user.m_UserName);
            m_tv_Email.setText(user.m_Email);
            m_tv_Register.setText(Html.fromHtml("<u>退出登陆</u>"));
            m_tv_Register.setOnClickListener(m_OnClickLogout);
        }else{
            m_iv_Profile.setImageBitmap(Tool.GetBitmapFromAssets(this, s_LoginProfileFilePath));
            m_iv_Profile.setOnClickListener(m_OnClickLogin);
            m_tv_UserName.setText("");
            m_tv_Email.setText("");
            m_tv_Register.setText(Html.fromHtml("<u>没有账号？点此注册</u>"));
            m_tv_Register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(UserActivity.this, UserRegisterActivity.class);
                    startActivity(intent);
                }
            });
        }
    }

    private void Logout(){
        Instance.s_Preferences.Logout();
        DealWithUser();
    }

    private void UploadProfile(){
        final BmobFile bmobProfileFile = new BmobFile(new File(getFilesDir(), GetProfileFileName()));
        if(!Instance.s_Preferences.GetUser().m_Avatar.equals(""))
            bmobProfileFile.setUrl(Instance.s_Preferences.GetUser().m_Avatar);
        bmobProfileFile.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    Instance.s_Preferences.SetProfileState(User.ProfileState.Normal);
                    Instance.s_Preferences.SetAvatar(bmobProfileFile.getFileUrl());
                    Instance.s_Preferences.UpdateUserProfile();
                }
                else{
                    Instance.s_Preferences.SetProfileState(User.ProfileState.UploadFail);
                    Toast.makeText(UserActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
                DealWithUser();
            }

            @Override
            public void onProgress(Integer value) {
                m_pb_UploadProfile.setProgress(value);
            }
        });
    }

    public static String GetProfileFileName(){
        return Instance.s_Preferences.GetUser().m_UserName+"_"+s_ProfileFileNameSuffix;
    }
}


