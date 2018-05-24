package com.hlxx.climber.firstpage;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import com.ddz.floatingactionbutton.FloatingActionButton;
import com.hlxx.climber.firstpage.setting.HistoryActivity;
import com.hlxx.climber.secondpage.ClimbingActivity;
import com.hlxx.climber.R;
import com.hlxx.climber.firstpage.setting.SettingActivity;
import com.hlxx.climber.services.AzureServiceAdapter;
import com.microsoft.windowsazure.mobileservices.MobileServiceActivityResult;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.wx.wheelview.adapter.ArrayWheelAdapter;
import com.wx.wheelview.widget.WheelView;

import com.microsoft.windowsazure.mobileservices.authentication.MobileServiceAuthenticationProvider;
import com.microsoft.windowsazure.mobileservices.authentication.MobileServiceUser;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.ArrayList;


import static com.hlxx.climber.services.AzureServiceAdapter.Initialize;


public class TimeSettingActivity extends AppCompatActivity {
    //背景图片
    private ImageView imageView;
    private WheelView wheelView;
    private long firstPressedTime;
    private MobileServiceClient mClient;
    //login
    AzureServiceAdapter mServiceAdapter;
    public static final int MICROSOFT_LOGIN_REQUEST_CODE = 1;
    public static final String SHAREDPREFFILE = "temp";
    public static final String USERIDPREF = "uid";
    public static final String TOKENPREF = "tkn";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.gc();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_setting);
        // createScaleImage();
        createImageView(R.mipmap.remote_mountain1); //设置背景图片
        createWheelView(); //设置时间滚轮
        createButton();//设置开始专注按钮
        createFAButton();//设置右上角浮动按钮
        //下面是自定义一个任务栏，取代原先自带的任务栏

        //login
        Initialize(this);
        mServiceAdapter = AzureServiceAdapter.getInstance();
        mClient = mServiceAdapter.getClient();
    }

    private void cacheUserToken(MobileServiceUser user) {
        SharedPreferences prefs = getSharedPreferences(SHAREDPREFFILE, Context.MODE_PRIVATE);
        Editor editor = prefs.edit();
        editor.putString(USERIDPREF, user.getUserId());
        editor.putString(TOKENPREF, user.getAuthenticationToken());
        editor.commit();
    }

    private boolean loadUserTokenCache(MobileServiceClient client) {
        SharedPreferences prefs = getSharedPreferences(SHAREDPREFFILE, Context.MODE_PRIVATE);
        String userId = prefs.getString(USERIDPREF, null);
        if (userId == null)
            return false;
        String token = prefs.getString(TOKENPREF, null);
        if (token == null)
            return false;

        MobileServiceUser user = new MobileServiceUser(userId);
        user.setAuthenticationToken(token);
        client.setCurrentUser(user);

        return true;
    }

    //身份认证
    private void authenticate() {
        if (loadUserTokenCache(mClient)) {
            Toast.makeText(TimeSettingActivity.this, "您已经登录啦", Toast.LENGTH_SHORT).show();
        }
        // If we failed to load a token cache, login and create a token cache
        else {
            // Login using the Microsoft provider.
            mClient.login(MobileServiceAuthenticationProvider.MicrosoftAccount, "focusclimb", MICROSOFT_LOGIN_REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // When request completes
        if (resultCode == RESULT_OK) {
            // Check the request code matches the one we send in the login request
            if (requestCode == MICROSOFT_LOGIN_REQUEST_CODE) {
                MobileServiceActivityResult result = mClient.onActivityResult(data);
                if (result.isLoggedIn()) {
                    // login succeeded
                    createAndShowDialog(String.format("You are now logged in - %1$2s", mClient.getCurrentUser().getUserId()), "Success");
                    cacheUserToken(mClient.getCurrentUser());
                } else {
                    // login failed, check the error message
                    String errorMessage = result.getErrorMessage();
                    createAndShowDialog(errorMessage, "Error");
                }
            }
        }
    }

    private void createFAButton() {
        FloatingActionButton fabSetting = findViewById(R.id.fab_setting);
        FloatingActionButton fabHistory = findViewById(R.id.fab_history);
        FloatingActionButton fabLogin = findViewById(R.id.fab_login);
        fabSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TimeSettingActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });
        fabHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(TimeSettingActivity.this, HistoryActivity.class
                );
                startActivity(intent);
            }
        });

        fabLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //login
                authenticate();
            }
        });

    }

    private void createButton() {
        Button startbutton = findViewById(R.id.button);
        startbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //data即当前选中的滚轮数据
                String data = (String) wheelView.getSelectionItem();
                //下面冬冬可自行切换至下一activity并传入data数据
                Intent intent = new Intent(TimeSettingActivity.this, ClimbingActivity.class);
                intent.putExtra("time", data);
                startActivity(intent);
                finish();

            }
        });
    }

    private void createImageView(int num) {
        imageView = findViewById(R.id.imageview);
        imageView.setImageResource(num);
    }

    //设置时间滚轮
    private void createWheelView() {
        wheelView = findViewById(R.id.wheelView);
        //设置数据适配器
        wheelView.setWheelAdapter(new ArrayWheelAdapter(this));
        //传入数据
        wheelView.setWheelData(createMinutes());
        wheelView.setWheelClickable(true);
        //设置滚轮数据循环显示
        wheelView.setLoop(true);
        //设置滚轮显示3行
        wheelView.setWheelSize(3);
        wheelView.setSkin(WheelView.Skin.Holo);
        WheelView.WheelViewStyle style = new WheelView.WheelViewStyle();
        //设置选中的文字颜色与大小
        style.selectedTextColor = getResources().getColor(R.color.colorbutton);
        style.holoBorderColor = getResources().getColor(R.color.colorbutton);
        style.selectedTextSize = 25;
        //设置背景颜色为透明
        style.backgroundColor = Color.alpha(0);
        style.textColor = getResources().getColor(R.color.colorbutton);

        wheelView.setStyle(style);
        //设置滚轮右侧的指示文字及其格式
        wheelView.setExtraText("分钟", getResources().getColor(R.color.colorbutton), 60, 120);
        //设置滚轮滑动停止时的事件，即更换图片
        wheelView.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
            @Override
            public void onItemSelected(int i, Object o) {
                //必须先清零，以避免内存过度占用问题
                imageView.setImageResource(0);
                //切换图片
                imageView.setImageResource(R.mipmap.remote_mountain1 + (i+2)/3);


            }
        });
    }

    //为滚轮设置数据
    private ArrayList<String> createMinutes() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("01");list.add("05");
        list.add("10");list.add("20");
        list.add("30");list.add("40");
        list.add("50");list.add("60");
        list.add("90");list.add("120");
        return list;
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - firstPressedTime < 5000) {
            ActivityCompat.finishAffinity(this);//退出整个程序
        } else {
            Toast.makeText(getBaseContext(), "再点一次退出", Toast.LENGTH_SHORT).show();
            firstPressedTime = System.currentTimeMillis();
        }
    }

    /**
     * Creates a dialog and shows it
     *
     * @param message The dialog message
     * @param title   The dialog title
     */
    private void createAndShowDialog(final String message, final String title) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);


        builder.setMessage(message);
        builder.setTitle(title);
        builder.create().show();
    }

    private void createAndShowDialog(Exception exception, String title) {
        Throwable ex = exception;
        if (exception.getCause() != null) {
            ex = exception.getCause();
        }
        createAndShowDialog(ex.getMessage(), title);
    }
}

