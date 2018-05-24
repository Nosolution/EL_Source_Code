package com.easylife.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.easylife.entity.User;
import com.easylife.util.ImageAssetManager;

import java.util.Calendar;
import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class LoginOrRegisterActivity extends FragmentActivity {
    static final int NUM_ITEMS = 2;
    private static final String[] MONTH = {"Jan", "Feb", "Mar", "Apr", "May", "June", "July", "Aug", "Sept", "Oct", "Nov", "Dec"};
    String phonePattern = "^((1[3,5,8][0-9])|(14[5,7])|(17[0,6,7,8])|(19[7]))\\d{8}$";

    MyFragmentPagerAdapter adapter;
    ViewPager viewPager;

    private TextView day;
    private TextView monthAndYear;
    private TextView loginSelected;
    private TextView registerSelected;
    private TextView dailySentence;
    private TextView sentenceOrigin;
    private ImageView background;


    private String phoneOrAccount;
    private String password;
    private String username;
    private String verifyingCode;
    private String verifyingCodeToSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_or_register);

        //动态获取敏感权限
        requestPermission();

        new Handler().postDelayed(() -> SplashActivity.getInstance().finish(), 1000);

        //初始化控件
        initWidget();

        //设置点击事件
        setOnClickEvent();
    }

    //设置点击事件
    private void setOnClickEvent() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        loginSelected.setTextColor(Color.parseColor("#ffffff"));
                        loginSelected.setBackground(LoginOrRegisterActivity.this.getResources().getDrawable(R.drawable.login_or_register_selected_bg, null));
                        registerSelected.setTextColor(Color.parseColor("#8A000000"));
                        registerSelected.setBackground(LoginOrRegisterActivity.this.getResources().getDrawable(R.drawable.login_or_register_unselected_bg));
                        break;
                    case 1:
                        registerSelected.setTextColor(Color.parseColor("#ffffff"));
                        registerSelected.setBackground(LoginOrRegisterActivity.this.getResources().getDrawable(R.drawable.login_or_register_selected_bg, null));
                        loginSelected.setTextColor(Color.parseColor("#8A000000"));
                        loginSelected.setBackground(LoginOrRegisterActivity.this.getResources().getDrawable(R.drawable.login_or_register_unselected_bg));
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        loginSelected.setOnClickListener(v -> {
            viewPager.setCurrentItem(0);
            loginSelected.setTextColor(Color.parseColor("#ffffff"));
            loginSelected.setBackground(LoginOrRegisterActivity.this.getResources().getDrawable(R.drawable.login_or_register_selected_bg, null));
            registerSelected.setTextColor(Color.parseColor("#8A000000"));
            registerSelected.setBackground(LoginOrRegisterActivity.this.getResources().getDrawable(R.drawable.login_or_register_unselected_bg));
        });
        registerSelected.setOnClickListener(v -> {
            viewPager.setCurrentItem(1);
            registerSelected.setTextColor(Color.parseColor("#ffffff"));
            registerSelected.setBackground(LoginOrRegisterActivity.this.getResources().getDrawable(R.drawable.login_or_register_selected_bg, null));
            loginSelected.setTextColor(Color.parseColor("#8A000000"));
            loginSelected.setBackground(LoginOrRegisterActivity.this.getResources().getDrawable(R.drawable.login_or_register_unselected_bg));
        });
    }

    /**
     * 请求授权
     */
    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) { //表示未授权时
            //进行授权
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 1);
        }
    }

    //初始化控件
    private void initWidget() {
        viewPager = findViewById(R.id.login_or_register_viewpager);
        day = findViewById(R.id.day_textview);
        monthAndYear = findViewById(R.id.month_and_year_textview);
        dailySentence = findViewById(R.id.daily_sentence_textview);
        sentenceOrigin = findViewById(R.id.sentence_origin_textview);
        loginSelected = findViewById(R.id.login_selected_textview);
        registerSelected = findViewById(R.id.register_selected_textview);
        background = findViewById(R.id.background_imageview);

        //设置背景图
        ImageAssetManager manager = new ImageAssetManager();
        manager.initAssets(this);
        background.setImageBitmap(BitmapFactory.decodeStream(manager.getInputStream(MainApplication.picIndex)));


        adapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        Calendar calendar = Calendar.getInstance();//获取系统的日期
        //年
        int current_year = calendar.get(Calendar.YEAR);
        //月
        int current_month = calendar.get(Calendar.MONTH);
        //日
        int current_day = calendar.get(Calendar.DAY_OF_MONTH);

        day.setText(current_day + "");
        monthAndYear.setText(MONTH[current_month] + ". " + current_year);

        String[] sentence = manager.getSentence(MainApplication.sentenceIndex).split("--");
        dailySentence.setText(sentence[0]);
        if (sentence.length == 2) {
            sentenceOrigin.setText("--" + sentence[1]);
        } else {
            sentenceOrigin.setText("");
        }
    }

    //登录按钮
    public void login(View v) {
        final ProgressDialog dialog = new ProgressDialog(LoginOrRegisterActivity.this);
        dialog.setMessage("正在登录...");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();

        phoneOrAccount = MyFragment.phoneNumberForLogin.getText().toString();
        password = MyFragment.passwordForLogin.getText().toString();

        if (password.length() < 8 || password.length() > 16) {
            dialog.dismiss();
            Toast.makeText(LoginOrRegisterActivity.this, "密码长度必须大于8位且小于16位！", Toast.LENGTH_SHORT).show();
            return;
        }

        User user = new User();
        user.setPassword(password);
        MainApplication.userQuery.addWhereEqualTo("mobilePhoneNumber", phoneOrAccount);
        MainApplication.userQuery.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if (e == null) {
                    user.setUsername(list.get(0).getUsername());
                } else {
                    user.setUsername(phoneOrAccount);
                }
                user.login(new SaveListener<User>() {
                    @Override
                    public void done(User user, BmobException e) {
                        dialog.dismiss();
                        if (e != null) {
                            System.out.println(e.toString());
                            Toast.makeText(LoginOrRegisterActivity.this, e.toString() + "登录失败！请检查账号或手机号是否正确！", Toast.LENGTH_LONG).show();
                        } else {
                            saveLocalUser(user);
                            startActivity(new Intent(LoginOrRegisterActivity.this, MainControlActivity.class));
                            finish();
                            Toast.makeText(LoginOrRegisterActivity.this, "登录成功！", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }

    //保存已登录用户到本地
    private void saveLocalUser(User user) {
        SharedPreferences.Editor editor = getSharedPreferences("login_user", MODE_PRIVATE).edit();
        editor.putString("objectID", user.getObjectId());
        editor.putString("user_phone", user.getMobilePhoneNumber());
        editor.putString("username", user.getUsername());
        editor.putString("nickname", user.getNickname());
        editor.putString("password", user.getPassword());
        editor.putString("avatar", user.getAvatarUrl());
        editor.putString("avatar_file_name",user.getAvatarFileName());
        editor.apply();
    }

    //注册按钮
    public void register(View v) {
        final ProgressDialog dialog = new ProgressDialog(LoginOrRegisterActivity.this);
        dialog.setMessage("正在注册...");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();

        phoneOrAccount = MyFragment.phoneNumberForRegister.getText().toString();
        password = MyFragment.passwordForRegister.getText().toString();
        verifyingCode = MyFragment.verifyingCodeForRegister.getText().toString();

        if (!phoneOrAccount.matches(phonePattern)) {
            dialog.dismiss();
            Toast.makeText(LoginOrRegisterActivity.this, "请输入正确的手机号！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!verifyingCode.equals(verifyingCodeToSend)) {
            dialog.dismiss();
            Toast.makeText(LoginOrRegisterActivity.this, "请输入正确的验证码！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.length() < 8 || password.length() > 16) {
            dialog.dismiss();
            Toast.makeText(LoginOrRegisterActivity.this, "密码长度必须大于8位且小于16位！", Toast.LENGTH_SHORT).show();
            return;
        }

        User user = new User(phoneOrAccount, password);
        user.setUsername("el" + phoneOrAccount);
        user.signUp(new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                dialog.dismiss();
                if (e != null) {
                    System.out.println(e.toString());
                    Toast.makeText(LoginOrRegisterActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(LoginOrRegisterActivity.this, "注册成功！", Toast.LENGTH_SHORT).show();

                    // 实现自动登录
                    ProgressDialog dialog = new ProgressDialog(LoginOrRegisterActivity.this);
                    dialog.setMessage("正在登录...");
                    dialog.setIndeterminate(true);
                    dialog.setCancelable(true);
                    dialog.show();
                    user.setPassword(password);
                    user.login(new SaveListener<User>() {
                        @Override
                        public void done(User user, BmobException e) {
                            if (e != null) {
                                System.out.println(e.toString());
                                Toast.makeText(LoginOrRegisterActivity.this, "登录出现异常，请重试！", Toast.LENGTH_LONG).show();
                            } else {
                                // 实现登录后跳转至主界面
                                saveLocalUser(user);
                                startActivity(new Intent(LoginOrRegisterActivity.this, MainControlActivity.class));
                                Toast.makeText(LoginOrRegisterActivity.this, "登录成功！", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                finish();
                            }
                        }
                    });
                }
            }
        });
    }

    //忘记密码
    public void forget_password(View v) {
        final ProgressDialog dialog = new ProgressDialog(LoginOrRegisterActivity.this);
        dialog.setMessage("正在发送...");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);

        final AlertDialog.Builder builder = new AlertDialog.Builder(LoginOrRegisterActivity.this);
        final View view = LayoutInflater.from(LoginOrRegisterActivity.this).inflate(R.layout.forget_password_dialog, null);
        builder.setView(view);
        builder.setTitle("");
        builder.setNegativeButton("取消", (dialog1, which) -> {

        });
        builder.setPositiveButton("确定", (dialogInterface, which) -> {
            dialog.show();

            EditText phoneNumber = view.findViewById(R.id.phone_number_edittext);

            if (!phoneNumber.getText().toString().matches(phonePattern)) {
                dialog.dismiss();
                Toast.makeText(LoginOrRegisterActivity.this, "请输入正确的手机号！", Toast.LENGTH_SHORT).show();
                return;
            }

            String rawStr = "QWERTYUIOPLKJHGFDSAZXCVBNMqwertyuioplkjhgfdsazxcvbnm0123456789";
            StringBuilder pw = new StringBuilder("");
            for (int i = 0; i < 8; i++) {
                pw.append(rawStr.charAt((int) (Math.random() * rawStr.length())));
            }

            User user = new User("el" + phoneNumber.getText().toString(), pw.toString(), phoneNumber.getText().toString());
//                BmobUser.resetPasswordBySMSCode(null,pw.toString(),null);
            // TODO: 2018/4/20 更新用户密码


            Intent sendIntent = new Intent("SENT_SMS_ACTION");
            PendingIntent sendPI = PendingIntent.getBroadcast(LoginOrRegisterActivity.this, 0, sendIntent, 0);
            SmsManager smsManager = SmsManager.getDefault();
            List<String> divideContents = smsManager.divideMessage("该手机号注册账号的密码是：" + pw + ",请妥善保管！");
            for (String text : divideContents) {
                smsManager.sendTextMessage(phoneNumber.getText().toString(), null, text, sendPI, null);
            }

            dialog.dismiss();

            Toast.makeText(LoginOrRegisterActivity.this, "发送成功！", Toast.LENGTH_SHORT).show();
        });
        builder.show();


    }

    //发送验证码
    public void send_verifying_code(View v) {
        phoneOrAccount = MyFragment.phoneNumberForRegister.getText().toString();
        verifyingCodeToSend = String.valueOf((int) (Math.random() * 1000000));
        if (phoneOrAccount.matches(phonePattern)) {
            //获取发送短信权限
//            requestPermission();
            Intent sendIntent = new Intent("SENT_SMS_ACTION");
            PendingIntent sendPI = PendingIntent.getBroadcast(this, 0, sendIntent, 0);
            SmsManager smsManager = SmsManager.getDefault();
            List<String> divideContents = smsManager.divideMessage("你此次注册的验证码是：" + verifyingCodeToSend);
            for (String text : divideContents) {
                smsManager.sendTextMessage(phoneOrAccount, null, text, sendPI, null);
            }
            Toast.makeText(LoginOrRegisterActivity.this, "验证码发送成功！", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(LoginOrRegisterActivity.this, "请输入正确的手机号！", Toast.LENGTH_SHORT).show();
        }

    }

    public static class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return MyFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

    }

    public static class MyFragment extends android.support.v4.app.Fragment {
        static EditText phoneNumberForLogin;
        static EditText passwordForLogin;
        static EditText phoneNumberForRegister;
        static EditText verifyingCodeForRegister;
        static EditText passwordForRegister;

        int mNum;
        View view;

        @Nullable
        @Override
        public View getView() {
            return view;
        }

        static MyFragment newInstance(int num) {
            MyFragment f = new MyFragment();

            // Supply num input as an argument.
            Bundle args = new Bundle();
            args.putInt("num", num);
            f.setArguments(args);

            return f;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            mNum = getArguments() != null ? getArguments().getInt("num") : 1;
        }

        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            int id = R.layout.login_fragment;
            if (mNum == 1) {
                id = R.layout.register_fragment;
            }
            view = inflater.inflate(id, container, false);

            return view;
        }

        @Override
        public void onStart() {
            super.onStart();

            //初始化Fragment子控件
            phoneNumberForLogin = getActivity().findViewById(R.id.phone_number_for_login_edittext);
            passwordForLogin = getActivity().findViewById(R.id.password_for_login_edittext);
            phoneNumberForRegister = getActivity().findViewById(R.id.phone_for_register_edittext);
            verifyingCodeForRegister = getActivity().findViewById(R.id.verifying_code_for_register_edittext);
            passwordForRegister = getActivity().findViewById(R.id.password_for_register_edittext);
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("退出");
        builder.setMessage("确定要退出应用吗？");
        builder.setNegativeButton("取消", (dialog, which) -> {
            dialog.dismiss();
        });
        builder.setPositiveButton("退出", (dialog, which) -> {
            finish();
        });
        builder.show();
    }
}
