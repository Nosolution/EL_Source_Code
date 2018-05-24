package com.easylife.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.easylife.entity.Feedback;
import com.easylife.entity.User;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class FeedbackActivity extends Activity {
    private ImageView back;
    private EditText feedbackContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        //初始化标题栏
        initActionBar();

        //初始化控件
        initWidget();

        //设置点击事件
        setOnclickEvent();
    }

    private void setOnclickEvent() {
        back.setOnClickListener(v -> finish());
    }

    private void initWidget() {
        back = findViewById(R.id.back_imageview);
        feedbackContent = findViewById(R.id.feedback_edittext);
    }

    //初始化标题栏
    private void initActionBar() {
        ActionBar actionBar = getActionBar();
        View actionBarView = LayoutInflater.from(this).inflate(R.layout.user_actionbar, null);
        if (actionBar != null) {
            actionBar.setCustomView(actionBarView);
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        }
    }

    public void sendFeedback(View view) {
        String content = feedbackContent.getText().toString();
        if (content.length() < 15) {
            Toast.makeText(FeedbackActivity.this, "反馈的内容不得少于15字！", Toast.LENGTH_LONG).show();
            return;
        }
        ProgressDialog waitingDialog = new ProgressDialog(this);
        waitingDialog.setMessage("正在提交你的宝贵反馈意见，请稍候...");
        waitingDialog.show();
        Feedback feedback = new Feedback();
        feedback.setContent(content);
        feedback.setUserId(getCurrentUser().getObjectId());
        feedback.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null){
                    waitingDialog.dismiss();
                    Toast.makeText(FeedbackActivity.this,"反馈提交成功！",Toast.LENGTH_SHORT).show();
                    FeedbackActivity.this.finish();
                }else {
                    Log.i("saveFeedback",s);
                    Toast.makeText(FeedbackActivity.this,"反馈提交失败，请重试！",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //获取本地用户
    public User getCurrentUser() {
        User currentUser;
        SharedPreferences preferences = getSharedPreferences("login_user", MODE_PRIVATE);
        currentUser = new User(
                preferences.getString("username", "null"),
                preferences.getString("nickname", "null"),
                preferences.getString("password", "null"),
                preferences.getString("user_phone", "null"));
        currentUser.setObjectId(preferences.getString("objectID", "null"));
        currentUser.setPassword(preferences.getString("password", "null"));
        currentUser.setAvatarUrl(preferences.getString("avatar", "null"));
        currentUser.setAvatarFileName(preferences.getString("avatar_file_name", "null"));
        return currentUser;
    }
}
