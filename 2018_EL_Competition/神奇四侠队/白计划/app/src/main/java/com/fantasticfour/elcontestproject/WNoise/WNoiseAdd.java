package com.fantasticfour.elcontestproject.WNoise;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;

import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fantasticfour.elcontestproject.Instance.Ins_WNoise.WNoise;
import com.fantasticfour.elcontestproject.Instance.Instance;
import com.fantasticfour.elcontestproject.R;
import com.fantasticfour.elcontestproject.User.UserSetProfileActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import static com.fantasticfour.elcontestproject.Instance.Instance.s_WNoiseController;
/**

 * Created by 12509 on 2018/5/18.
 */

public class WNoiseAdd extends AppCompatActivity {
    Button mAddSystemWnoise;
    Button mAddOnlineWnoise;
    Button mAddRecordWnoise;
    String mFilePath;
    private int mResultCode;
    public static final int s_add = 456;
    public static final int s_unAdded = 234;
    public static final int s_FileRequestCode = 0xa0;
    private Boolean isRecording = false;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wnoise_activity_add);
        ModifyDisplay();
        TextView mTextViewScene = findViewById(R.id.title_middletext);
        mTextViewScene.setText("添加白噪音");
        mResultCode = s_unAdded;
        setResult(mResultCode);
        mAddSystemWnoise = (Button) findViewById(R.id.wnoise_btn_activity_wnoise_add_system);
        mAddOnlineWnoise = (Button) findViewById(R.id.wnoise_btn_activity_wnoise_add_online);
        mAddRecordWnoise = (Button) findViewById(R.id.wnoise_btn_activity_wnoise_add_record);

        if((ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) || (ContextCompat.checkSelfPermission(this, android.Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) || (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)){
        mAddSystemWnoise = findViewById(R.id.wnoise_btn_activity_wnoise_add_system);
        mAddOnlineWnoise = findViewById(R.id.wnoise_btn_activity_wnoise_add_online);
        mAddRecordWnoise = findViewById(R.id.wnoise_btn_activity_wnoise_add_record);
            ActivityCompat.requestPermissions(WNoiseAdd.this,new String[]{
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.RECORD_AUDIO,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            },1);
        }
        final WNoiseRecorder mWNoiseRecorder;
        mFilePath = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFilePath = getFilesDir().getPath();
        mWNoiseRecorder = new WNoiseRecorder(mFilePath, "haha");
        mAddRecordWnoise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isRecording){
                    isRecording = !isRecording;
                    mAddSystemWnoise.setEnabled(true);
                    mAddOnlineWnoise.setEnabled(true);
                    mWNoiseRecorder.stopRecording();
                    mAddRecordWnoise.setText("Record");
                    Toast.makeText(WNoiseAdd.this, "done", Toast.LENGTH_SHORT).show();
                    //final WNoise newWNoise = Instance.s_WNoiseController.GenerateNewWNoise();
                    Context addContext = v.getContext();
                    View view = getLayoutInflater().inflate(R.layout.save_wnoise, null);
                    final EditText editText = (EditText) view.findViewById(R.id.scene_name);
                    editText.setHint("enter the White Noise's name");
                    AlertDialog dialog = new AlertDialog.Builder(addContext)
                            .setTitle("设置白噪音名称")//设置对话框的标题
                            .setView(view)
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    finish();
                                }
                            })
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //将新的白噪音与文件关联起来
                                    WNoise newWNoise = Instance.s_WNoiseController.GenerateNewWNoise();
                                    String content = editText.getText().toString();
                                    File target = new File(mFilePath, "haha.mp4");
                                    target.renameTo(new File(mFilePath, newWNoise.m_ID + ".mp4"));
                                    newWNoise.m_Name = content;
                                    newWNoise.m_FileName = newWNoise.m_ID + ".mp4";
                                    Instance.s_WNoiseController.SetWNoiseNameAndFile(newWNoise);
                                    mResultCode = s_add;
                                    setResult(mResultCode);
                                    dialog.dismiss();
                                    finish();
                                }
                            }).create();
                    dialog.show();
                }
                else{
                    isRecording = !isRecording;
                    mAddSystemWnoise.setEnabled(false);
                    mAddOnlineWnoise.setEnabled(false);
                    mWNoiseRecorder.startRecording();
                    mAddRecordWnoise.setText("End");
                    Toast.makeText(WNoiseAdd.this, "start", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mAddSystemWnoise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAddWnoise = new Intent(Intent.ACTION_GET_CONTENT);
                // 设置文件类型
                intentAddWnoise.setType("audio/*");
                mResultCode = s_add;
                setResult(mResultCode);
                intentAddWnoise.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(Intent.createChooser(intentAddWnoise, "选择文件"), s_FileRequestCode);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            Uri uri = data.getData();
            SetWNoise(uri);
            super.onActivityResult(requestCode, resultCode, data);
            finish();
        }
    }

    private void SetWNoise(Uri uri){

        //网上的好心人写的，谢谢他全家///////
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor actualImageCursor = getContentResolver().query(uri, projection, null, null, null);
        int actual_image_column_index = actualImageCursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        actualImageCursor.moveToFirst();
        String img_path = actualImageCursor.getString(actual_image_column_index);
        actualImageCursor.close();
        File source = new File(img_path);
        ///////////////////////////////////

        WNoise newWNoise = Instance.s_WNoiseController.GenerateNewWNoise();
        String FileName = source.getName();
        String WNoiseName = FileName.substring(0, FileName.lastIndexOf("."));
        String suffix = FileName.substring(FileName.lastIndexOf(".") + 1);
        File target = new File(getFilesDir(), newWNoise.m_ID+"."+ suffix);
        CopySdcardFile(source, target);
        newWNoise.m_FileName = target.getName();
        newWNoise.m_Name = WNoiseName;
        Instance.s_WNoiseController.SetWNoiseNameAndFile(newWNoise);
    }

    public int CopySdcardFile(File source, File target){
        try{
            InputStream sourceStream = new FileInputStream(source);
            OutputStream targetStream = new FileOutputStream(target);
            byte bt[] = new byte[sourceStream.available()];
            int c;
            while ((c = sourceStream.read(bt)) > 0) {
                targetStream.write(bt, 0, c);
            }
            sourceStream.close();
            targetStream.close();
            return 0;

        } catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    private void ModifyDisplay(){
        Display display = getWindowManager().getDefaultDisplay();
        WindowManager.LayoutParams params = getWindow().getAttributes();
        Point size = new Point();display.getSize(size);
        params.width = (int) (size.x*0.9);
        getWindow().setAttributes(params);
    }


}
