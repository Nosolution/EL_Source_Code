package com.easylife.activity;

import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.easylife.entity.User;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

public class UserInfoActivity extends Activity {
    private TextView nickname;
    private TextView username;
    private TextView phoneNumber;
    private TextView changePassword;
    private TextView logout;
    private LinearLayout changeNickname;
    private LinearLayout changeUsername;
    private EditText nicknameEdit;
    private ImageView back;
    private RoundedImageView avatar;

    private String avatarSrc = Environment.getExternalStorageDirectory().getPath() + "/EasyLife/avatar/";
    private User currentUser;

    //调用系统相册-选择图片
    private static final int IMAGE = 1;
    private static final int REQUEST_ORIGINAL = 2;// 请求原图信号标识
    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static final String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private String picPath;//图片存储路径


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);

        //获取本地用户
        currentUser = getCurrentUser();

        //初始化标题栏
        initActionBar();

        //初始化控件
        initWidget();

        //初始化用户信息
        initUserInfo();

        //设置点击事件
        setOnClickEvent();
    }

    //设置点击事件
    private void setOnClickEvent() {
        logout.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("退出账号");
            builder.setMessage("确定退出当前账号吗？");
            builder.setPositiveButton("退出", (dialog, which) -> {
                SharedPreferences preferences = getSharedPreferences("login_user", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
                Intent intent = new Intent(UserInfoActivity.this, LoginOrRegisterActivity.class);
                startActivity(intent);
                finish();
            });
            builder.setNegativeButton("取消", (dialog, listener) -> {
                dialog.dismiss();
            });
            builder.show();
        });

        back.setOnClickListener(v -> {
            startActivity(new Intent(UserInfoActivity.this, UserActivity.class));
            finish();
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        });

        avatar.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(UserInfoActivity.this);
            AlertDialog dialog = builder.create();
            View view = LayoutInflater.from(this).inflate(R.layout.change_avatar_dialog, null);

            view.findViewById(R.id.camera_textview).setOnClickListener(view1 -> {
                //调用系统相机拍照
                picPath = Environment.getExternalStorageDirectory().getPath() + "/EasyLife/pic/IMG_" + System.currentTimeMillis() + "_temp.png";
                File file = new File(picPath);

                // android 7.0系统解决拍照的问题
                StrictMode.VmPolicy.Builder vmBuilder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(vmBuilder.build());
                vmBuilder.detectFileUriExposure();

                //获取相机权限
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    /* 相机请求码 */
                    final int REQUEST_CAMERA = 0;
                    ActivityCompat.requestPermissions(this,
                            new String[]{android.Manifest.permission.CAMERA}, REQUEST_CAMERA);
                }
                final Handler handler = new Handler();
                final Timer timer = new Timer(false);
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        handler.post(() -> {
                            if (ActivityCompat.checkSelfPermission(UserInfoActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                                timer.cancel();
                                //第一种方法，获取压缩图
//                        Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                        // 启动相机
//                        startActivityForResult(intent1, REQUEST_THUMBNAIL);

                                //第二种方法，获取原图
                                String cameraPackageName = getCameraPhoneAppInfos(UserInfoActivity.this);
                                if (cameraPackageName == null) {
                                    cameraPackageName = "com.android.camera";
                                }
                                final Intent intent_camera = UserInfoActivity.this.getPackageManager()
                                        .getLaunchIntentForPackage(cameraPackageName);
                                Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                if (intent_camera != null) {
                                    intent2.setPackage(cameraPackageName);
                                }
                                Uri uri = Uri.fromFile(file);
                                //为拍摄的图片指定一个存储的路径
                                intent2.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                                startActivityForResult(intent2, REQUEST_ORIGINAL);
                            }
                        });
                    }
                }, 0, 100);

                dialog.dismiss();
            });

            view.findViewById(R.id.album_textview).setOnClickListener(view2 -> {
                //调用相册
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                // Check if we have read permission
                int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
                if (permission != PackageManager.PERMISSION_GRANTED) {
                    // We don't have permission so prompt the user
                    ActivityCompat.requestPermissions(
                            this,
                            PERMISSIONS_STORAGE,
                            REQUEST_EXTERNAL_STORAGE
                    );
                }
                startActivityForResult(intent, IMAGE);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                dialog.dismiss();
            });

            dialog.setView(view);
            dialog.show();
        });

        changeNickname.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View view = LayoutInflater.from(this).inflate(R.layout.change_nickname_dialog, null);
            nicknameEdit = view.findViewById(R.id.nickname_edittext);
            builder.setTitle("请输入新的昵称");
            builder.setView(view);
            builder.setPositiveButton("确定", (dialog, which) -> {
                dialog.dismiss();
                ProgressDialog waitingDialog = new ProgressDialog(this);
                waitingDialog.setTitle("修改昵称");
                waitingDialog.setMessage("请稍候...");
                waitingDialog.setIndeterminate(true);
                waitingDialog.setCancelable(false);
                waitingDialog.show();
                // 保存用户昵称
                String newNickname = nicknameEdit.getText().toString();
                User user = getCurrentUser();
                user.setValue("nickname", newNickname);
                user.update(user.getObjectId(), new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            saveLocalUser(user);
                            nickname.setText(newNickname);
                            waitingDialog.dismiss();
                            Toast.makeText(UserInfoActivity.this, "修改成功！", Toast.LENGTH_SHORT).show();
                        } else {
                            waitingDialog.dismiss();
                            Toast.makeText(UserInfoActivity.this, "保存失败！\n" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            });
            builder.setNegativeButton("取消", ((dialog, which) -> {
                dialog.dismiss();
            }));
            builder.show();
        });

        changeUsername.setOnClickListener(listener -> {
            if (!username.getText().toString().equals("el" + getCurrentUser().getMobilePhoneNumber())) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("你的账号已经修改过，请勿重复操作！");
                builder.setPositiveButton("好的，知道了", ((dialog, which) -> {
                    dialog.dismiss();
                }));
                builder.show();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("设置新账号");
                View view = LayoutInflater.from(this).inflate(R.layout.change_username_dialog, null);
                EditText usernameEdit = view.findViewById(R.id.username_edittext);
                builder.setView(view);
                builder.setPositiveButton("确定修改", ((dialog, which) -> {
                    String newUsername = usernameEdit.getText().toString();
                    if (newUsername.equals(username.getText().toString())) {
                        dialog.dismiss();
                    } else {
                        ProgressDialog waitingDialog = new ProgressDialog(this);
                        waitingDialog.setTitle("设置账号");
                        waitingDialog.setMessage("请稍候...");
                        waitingDialog.setIndeterminate(true);
                        waitingDialog.setCancelable(false);
                        waitingDialog.show();
                        User user = getCurrentUser();
                        user.setUsername(newUsername);
                        user.update(user.getObjectId(), new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    saveLocalUser(user);
                                    username.setText(newUsername);
                                    waitingDialog.dismiss();
                                    Toast.makeText(UserInfoActivity.this, "设置成功！", Toast.LENGTH_SHORT).show();
                                } else {
                                    waitingDialog.dismiss();
                                    Toast.makeText(UserInfoActivity.this, "设置失败！", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }));
                builder.setNegativeButton("我再想想", ((dialog, which) -> {
                    dialog.dismiss();
                }));
                builder.show();
            }
        });

        changePassword.setOnClickListener(listener -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View view = LayoutInflater.from(this).inflate(R.layout.change_password_dialog, null);
            EditText oldPassword = view.findViewById(R.id.old_password_edittext);
            EditText newPassword = view.findViewById(R.id.new_password_edittext);
            EditText confirmPassword = view.findViewById(R.id.confirm_password_edittext);
            TextView notification = view.findViewById(R.id.notification_textview);
            AlertDialog changePwDialog = builder
                    .setView(view)
                    .setPositiveButton("确定", null)
                    .setNegativeButton("取消", null)
                    .create();
            changePwDialog.setOnShowListener(dialog -> {
                Button posButton = changePwDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                Button negButton = changePwDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                posButton.setOnClickListener(l -> {
                    // TODO: 2018/5/12 保存信息
                    if (oldPassword.getText().toString().length() < 8 || oldPassword.getText().toString().length() > 16) {
                        notification.setText("请输入正确的密码！");
                        return;
                    }
                    if (newPassword.getText().toString().length() < 8 || newPassword.getText().toString().length() > 16) {
                        notification.setText("密码长度必须不小于8位，且不大于16位！");
                        return;
                    }
                    if (!confirmPassword.getText().toString().equals(newPassword.getText().toString())) {
                        notification.setText("两次密码输入不一致，请检查！");
                        return;
                    }
                    changePwDialog.dismiss();
                    ProgressDialog waitingDialog = new ProgressDialog(this);
                    waitingDialog.setTitle("修改密码");
                    waitingDialog.setMessage("请稍候...");
                    waitingDialog.setIndeterminate(true);
                    waitingDialog.setCancelable(false);
                    waitingDialog.show();
                    User user = getCurrentUser();
                    if (!oldPassword.getText().toString().equals(user.getPassword())){
                        Toast.makeText(UserInfoActivity.this, "原密码错误，密码修改失败！", Toast.LENGTH_SHORT).show();
                        waitingDialog.dismiss();
                        return;
                    }
                    user.setPassword(newPassword.getText().toString());
                    user.setPw(user.getPassword());
                    user.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            waitingDialog.dismiss();
                            if (e == null) {
                                Toast.makeText(UserInfoActivity.this, "密码修改成功！", Toast.LENGTH_SHORT).show();
                                saveLocalUser(user);
                            } else {
                                Toast.makeText(UserInfoActivity.this, "密码修改失败！", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                });
                negButton.setOnClickListener(l -> {
                    changePwDialog.dismiss();
                });
            });
            changePwDialog.show();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //获取图片路径
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == IMAGE && data != null) {
                Uri selectedImage = data.getData();
                String[] filePathColumns = {MediaStore.Images.Media.DATA};
                Cursor c = getContentResolver().query(selectedImage, filePathColumns, null, null, null);
                if (c != null) {
                    c.moveToFirst();
                }
                int columnIndex = c.getColumnIndex(filePathColumns[0]);
                String imagePath = c.getString(columnIndex);
                File destFile = new File(avatarSrc + new File(imagePath).getName());
                //复制头像
                moveFile(imagePath, destFile);
                showImage(destFile.getAbsolutePath());
                c.close();
            }

            if (requestCode == REQUEST_ORIGINAL) {
                /**
                 * 这种方法是通过内存卡的路径进行读取图片，所以的到的图片是拍摄的原图
                 */
                showImage(picPath);
            }
        }
    }

    //复制文件
    private void moveFile(String imagePath, File destFile) {
        if (destFile.exists()) {
            destFile.delete();
        } else {
            if (!destFile.getParentFile().exists()) {
                destFile.mkdirs();
            }
        }
        //复制文件
        int byteread = 0;//读取的字节数
        InputStream in = null;
        OutputStream out = null;
        try {
            in = new FileInputStream(imagePath);
            out = new FileOutputStream(destFile);
            byte[] buffer = new byte[1024];
            /*
             * 方法说明：
             * ①：将指定 byte 数组中从偏移量 off 开始的 len 个字节写入此输出流。
             *      write(byte[] b, int off, int len)
             *          b - 数据
             *          off - 数据中的起始偏移量。
             *          len - 要写入的字节数。
             * ②：in.read(buffer))!=-1,是从流buffer中读取一个字节，当流结束的时候read返回-1
             */

            while ((byteread = in.read(buffer)) != -1) {
                out.write(buffer, 0, byteread);
            }
        } catch (IOException e) {
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {

                e.printStackTrace();
            }
        }
    }

    private void showImage(String imagePath) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.avatar_view, null);
        ImageView imageView = view.findViewById(R.id.avatar_view_imageview);
        try {
            imageView.setImageBitmap(BitmapFactory.decodeStream(new FileInputStream(imagePath)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        builder.setTitle("修改头像");
        builder.setView(view);
        builder.setNegativeButton("取消", (dialog, which) -> {
            dialog.dismiss();
        });
        builder.setNegativeButton("确定", (dialog, which) -> {
            ProgressDialog waitingDialog = new ProgressDialog(this);
            waitingDialog.setMessage("正在保存...");
            waitingDialog.setIndeterminate(true);
            waitingDialog.setCancelable(false);
            waitingDialog.show();
            BmobFile avatar = new BmobFile(new File(imagePath));
            avatar.upload(new UploadFileListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        currentUser.setAvatarUrl(avatar.getFileUrl());
                        currentUser.setAvatarFileName(new File(imagePath).getName());
                        saveLocalUser(currentUser);
                        currentUser.update(currentUser.getObjectId(), new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                waitingDialog.dismiss();
                                if (e != null) {
                                    e.printStackTrace();
                                    Toast.makeText(UserInfoActivity.this, "头像修改失败！请重试！\n" + e.toString(), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(UserInfoActivity.this, "头像修改成功！", Toast.LENGTH_SHORT).show();
                                    try {
                                        UserInfoActivity.this.avatar.setImageBitmap(BitmapFactory.decodeStream(new FileInputStream(imagePath)));
                                    } catch (FileNotFoundException e1) {
                                        e1.printStackTrace();
                                    }
                                }
                            }
                        });
                    } else {
                        Toast.makeText(UserInfoActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });
        builder.show();
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (event.getAction() == KeyEvent.ACTION_UP && event.getRepeatCount() == 0) {
                startActivity(new Intent(UserInfoActivity.this, UserActivity.class));
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        }
        return super.dispatchKeyEvent(event);
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

    //初始化用户信息
    private void initUserInfo() {
        User user = getCurrentUser();
        nickname.setText(user.getNickname());
        username.setText(user.getUsername());
        phoneNumber.setText(user.getMobilePhoneNumber());
    }

    //初始化控件
    private void initWidget() {
        changeNickname = findViewById(R.id.nickname_linearlayout);
        changeUsername = findViewById(R.id.change_username_linearlayout);
        nickname = findViewById(R.id.nickname_textview);
        username = findViewById(R.id.username_textview);
        phoneNumber = findViewById(R.id.phonenum_textview);
        changePassword = findViewById(R.id.pss_change_textview);
        logout = findViewById(R.id.logout_textview);
        back = findViewById(R.id.back_imageview);
        avatar = findViewById(R.id.avatar_imageview);

        //初始化头像
        if (new File(avatarSrc + currentUser.getAvatarFileName()).exists()) {
            avatar.setImageBitmap(BitmapFactory.decodeFile(avatarSrc + currentUser.getAvatarFileName()));
        } else if (!currentUser.getAvatarUrl().equals("null")) {
            BmobFile avatar = new BmobFile(currentUser.getAvatarFileName(), "", currentUser.getAvatarUrl());
            avatar.download(new File(avatarSrc + currentUser.getAvatarFileName()), new DownloadFileListener() {
                @Override
                public void done(String s, BmobException e) {
                    if (e != null) {
                        UserInfoActivity.this.avatar.setImageResource(R.mipmap.avatar);
                    } else {
                        UserInfoActivity.this.avatar.setImageBitmap(BitmapFactory.decodeFile(s + "/" + currentUser.getAvatarFileName()));
                    }
                }

                @Override
                public void onProgress(Integer integer, long l) {

                }
            });
        } else {
            UserInfoActivity.this.avatar.setImageResource(R.mipmap.avatar);
        }
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

    // 对使用系统拍照的处理
    //尽量使用系统相机，屏蔽第三方拍照软件
    public String getCameraPhoneAppInfos(Activity context) {
        try {
            String strCamera = "";
            List<PackageInfo> packages = context.getPackageManager()
                    .getInstalledPackages(0);
            for (int i = 0; i < packages.size(); i++) {
                try {
                    PackageInfo packageInfo = packages.get(i);
                    String strLabel = packageInfo.applicationInfo.loadLabel(
                            context.getPackageManager()).toString();
                    // 一般手机系统中拍照软件的名字
                    if ("相机,照相机,照相,拍照,摄像,Camera,camera".contains(strLabel)) {
                        strCamera = packageInfo.packageName;
                        if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
                            break;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (strCamera != null) {
                return strCamera;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
        editor.putString("avatar_file_name", user.getAvatarFileName());
        editor.apply();
    }
}
