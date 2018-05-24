package com.example.administrator.el_done1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;

public class SettingSelfInformationActivity extends AppCompatActivity {


    protected static final int CHOOSE_PICTURE = 0;
    protected static final int TAKE_PICTURE = 1;
    private static final int CROP_SMALL_PICTURE = 2;
    protected static Uri tempUri;
    ImageButton setFaceImage_Button;
    boolean a=false;
    boolean b=false;
    boolean c=false;


    //重写活动销毁方式，用ActivityManager来销毁
    @Override
    protected void onDestroy(){
        super.onDestroy();
        ActivityManager.removeActivity(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //根据主题设置字体
        switch (Theme.getTHEME()){
            case "SIMPLE":
                setTheme(R.style.SIMPLE_THEME);
                break;
            case "OTAKU":
                setTheme(R.style.OTAKU_THEME);
                break;
            case "PET":
                setTheme(R.style.PET_THEME);
                break;
        }

        //设立layout
        setContentView(R.layout.setting_self_information);

        //存入ActivityManager
        ActivityManager.addActivity(this);


        //隐藏应用默认标题栏
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!= null){
            actionBar.hide();
        }

        Button setSelfInformation_back=(Button)findViewById(R.id.set_self_information_back);
        setFaceImage_Button = (ImageButton)findViewById(R.id.set_face_image);
        setSelfInformation_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityManager.removeActivity(SettingSelfInformationActivity.this);
            }
        });
        setFaceImage_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openAlbumIntent = new Intent(
                        Intent.ACTION_GET_CONTENT);
                openAlbumIntent.setType("image/*");
                startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
                //                showChoosePicDialog();    //拍照功能有bug。。。。
            }
        });

    }
    /**
     * 显示修改头像的对话框
     */
    protected void showChoosePicDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("设置头像");
        String[] items = { "选择本地照片"/*, "拍照" */};        //拍照不知道哪里出了bug，所以算是废了
        builder.setNegativeButton("取消", null);
        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case CHOOSE_PICTURE: // 选择本地照片
                        Intent openAlbumIntent = new Intent(
                                Intent.ACTION_GET_CONTENT);
                        openAlbumIntent.setType("image/*");
                        startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
                        break;
                    case TAKE_PICTURE: // 拍照
                        Intent openCameraIntent = new Intent(
                                MediaStore.ACTION_IMAGE_CAPTURE);
                        tempUri = Uri.fromFile(new File(Environment
                                .getExternalStorageDirectory(), "image.jpg"));
                        // 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
                        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
                        startActivityForResult(openCameraIntent, TAKE_PICTURE);
                        break;
                }
            }
        });
        builder.create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) { // 如果返回码是可以用的
            switch (requestCode) {
                case TAKE_PICTURE:
                    startPhotoZoom(tempUri); // 开始对图片进行裁剪处理
                    break;
                case CHOOSE_PICTURE:
                    startPhotoZoom(data.getData()); // 开始对图片进行裁剪处理
                    break;
                case CROP_SMALL_PICTURE:
                    if (data != null) {
                        setImageToView(data); // 让刚才选择裁剪得到的图片显示在界面上
                    }
                    break;
            }
        }
    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    protected void startPhotoZoom(Uri uri) {
        if (uri == null) {
            Log.i("tag", "The uri is not exist.");
        }
        tempUri = uri;
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CROP_SMALL_PICTURE);
    }

    /**
     * 保存裁剪之后的图片数据
     */
    protected void setImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            photo = Utils.toRoundBitmap(photo, tempUri); // 这个时候的图片已经被处理成圆形的了
            UserInformation.setFaceBitmap(photo);
            setFaceImage_Button.setImageBitmap(photo);
            uploadPic(photo);
        }
    }


    private void uploadPic(Bitmap bitmap) {
        // 上传至服务器
        // ... 可以在这里把Bitmap转换成file，然后得到file的url，做文件上传操作
        // 注意这里得到的图片已经是圆形图片了
        // bitmap是没有做个圆形处理的，但已经被裁剪了

        String imagePath = Utils.savePhoto(bitmap, Environment
                .getExternalStorageDirectory().getAbsolutePath(), String
                .valueOf(System.currentTimeMillis()));
        Log.e("imagePath", imagePath+"");
        if(imagePath != null){
            // 拿着imagePath上传了
            // ...
        }
    }
    //确定事件
    public void confirm(View view) {

        EditText name=(EditText) findViewById(R.id.set_name);
        EditText zuoyouming=(EditText) findViewById(R.id.set_motto) ;
        EditText sex=(EditText)findViewById(R.id.set_sex);
        EditText pro=(EditText)findViewById(R.id.set_profession);
        EditText email=(EditText)findViewById(R.id.set_email);

        if( name.getText().toString().equals("")||zuoyouming.getText().toString().equals("")||email.getText().toString().equals("")||sex.getText().toString().equals("")||pro.getText().toString().equals("")){
            Toast.makeText(this,"请将个人信息填写完整",Toast.LENGTH_LONG).show();
        }else {
            UserInformation.setUser_name(name.getText().toString());
            UserInformation.set_motto(zuoyouming.getText().toString());





            UserInformation.set_profession(pro.getText().toString());
            b=true;
            if (email.getText().toString().indexOf("@")==-1){
                Toast.makeText(this,"请输入正确的邮箱",Toast.LENGTH_LONG).show();
            }else {
                UserInformation.set_email(email.getText().toString());
                c=true;
            }

            if (sex.getText().toString().equals("男") || sex.getText().toString().equals("女")||sex.getText().toString().equals("male")||sex.getText().toString().equals("female")) {
                UserInformation.set_sex(sex.getText().toString());a=true;
            } else Toast.makeText(this, "这不是人类的性别哦", Toast.LENGTH_LONG).show();

            if((a&&b&&c)){
                Toast.makeText(this,"成功",Toast.LENGTH_LONG).show();
            }



        }


    }
}
