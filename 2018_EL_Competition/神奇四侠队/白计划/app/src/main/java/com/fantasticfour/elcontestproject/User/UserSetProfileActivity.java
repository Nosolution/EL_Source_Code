package com.fantasticfour.elcontestproject.User;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.fantasticfour.elcontestproject.Instance.Tool;
import com.fantasticfour.elcontestproject.R;
import com.soundcloud.android.crop.Crop;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class UserSetProfileActivity extends AppCompatActivity {
    public static final String IMAGE_FILE_NAME = "temp_profile.jpg";

    public static final int s_FileRequestCode = 0xa0;
    public static final int s_CameraRequestCode = 0xa1;

    public static final int s_SetProfileSuccessResultCode = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_set_profile);
        ModifyDisplay();
        DealWithTitle();
        DealWithMethods();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_CANCELED)return;
        if(requestCode == s_FileRequestCode)
            CropRawPhoto(data.getData());
        else if(requestCode == s_CameraRequestCode) {
            if(Tool.HasSdcard()) {
                File tempFile = new File(
                        getExternalCacheDir(),
                        IMAGE_FILE_NAME);
                CropRawPhoto(Uri.fromFile(tempFile));
            }
        }
        else if(requestCode == Crop.REQUEST_CROP && resultCode == RESULT_OK) {
            setResult(s_SetProfileSuccessResultCode);
            finish();
        }
    }

    private void ModifyDisplay() {
        Display display = getWindowManager().getDefaultDisplay();
        WindowManager.LayoutParams params = getWindow().getAttributes();
        Point size = new Point();display.getSize(size);
        params.width = (int) (size.x*0.7);
        getWindow().setAttributes(params);
    }
    private void DealWithTitle() {
        //左侧按钮禁用
        Button btn_Left = findViewById(R.id.title_leftbutton);
        btn_Left.setClickable(false);

        //右侧按钮禁用
        Button btn_Right = findViewById(R.id.title_rightbutton);
        btn_Right.setClickable(false);

        //中间文本
        TextView tv_Middle = findViewById(R.id.title_middletext);
        tv_Middle.setText("上传头像");
    }
    private void DealWithMethods() {
        RecyclerView rv_Methods = findViewById(R.id.user_rv_activity_set_profile_methods);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv_Methods.setLayoutManager(linearLayoutManager);

        UserSetProfileMethodsAdapter userSetProfileMethodsAdapter = new UserSetProfileMethodsAdapter(this);
        rv_Methods.setAdapter(userSetProfileMethodsAdapter);
    }
    public void CropRawPhoto(Uri uri) {
        Crop.of(uri, Uri.fromFile(new File(getFilesDir(), UserActivity.GetProfileFileName()))).asSquare().start(this);
    }
}

