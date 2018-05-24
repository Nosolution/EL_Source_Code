package com.hlxx.climber.thirdpage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.hlxx.climber.R;
import com.hlxx.climber.firstpage.TimeSettingActivity;
import com.hlxx.climber.services.AzureServiceAdapter;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;


public class EndingActivity extends AppCompatActivity {
    private static final String SAVE_PIC_PATH = Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED) ? Environment.getExternalStorageDirectory().getAbsolutePath() : "/mnt/sdcard";
    private static final String SAVE_REAL_PATH = SAVE_PIC_PATH + "/hlxx/ClimberPicture";
    private ImageView imageView;
    private int[] picarrary;
    private int picindex;
    private int realTemp;

    // /storage/emulated/0/netease/hlxx/climber相册


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.gc();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ending);

        ending_buttons();//按钮跳转：bt1,bt2，bt3
        picarrary = new int[]{R.mipmap.end_background, R.mipmap.end_background1, R.mipmap.end_background2, R.mipmap.end_background3, R.mipmap.end_background4};
        Random rnd = new Random();
        picindex = rnd.nextInt(4);
        realTemp = picarrary[picindex];
        imageView = (ImageView) findViewById(R.id.ending);
        imageView.setImageResource(realTemp);

    }

    //按钮
    protected void ending_buttons() {
        Button btn1 = (Button) findViewById(R.id.end_to_start);
        Button btn2 = (Button) findViewById(R.id.stone);
        Button btn3 = (Button) findViewById(R.id.catch_picture);

        btn1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                // 给bnt1添加点击响应事件
                Intent intent = new Intent(EndingActivity.this, TimeSettingActivity.class);
                startActivity(intent);
                finish();

            }
        });
        btn2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EndingActivity.this, StoneActivity.class);
                startActivity(intent);
            }
        });

        btn3.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {

                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), realTemp);
                saveImage(getApplicationContext(), bitmap);
                Log.d("data", "-----------------wqnmiaomiaomiao----------------");
                Toast.makeText(EndingActivity.this, "图片保存成功", Toast.LENGTH_SHORT).show();
            }
        });

    }


    //保存图片1
    public static void saveImage(Context context, Bitmap bmp) {
        Log.d("data", "___________成功调用函数_______________________");
        String fileName = null;
        File file = null;
        FileOutputStream outStream = null;
        try {
            file = new File(SAVE_REAL_PATH, System.currentTimeMillis() + ".jpg");
            if (!file.exists()) {
                //先得到文件的上级目录，并创建上级目录，在创建文件
                file.getParentFile().mkdir();
                try {
                    //创建文件
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Log.d("data", "___________成功建立相册_______________________");
            fileName = file.toString();
            outStream = new FileOutputStream(fileName);
            if (null != outStream) {
                bmp.compress(Bitmap.CompressFormat.JPEG, 90, outStream);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (outStream != null) {
                    outStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        MediaStore.Images.Media.insertImage(context.getContentResolver(), bmp, fileName, null);
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        context.sendBroadcast(intent);

    }

}
