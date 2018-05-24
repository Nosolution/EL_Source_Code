package com.example.caea8.memoranduml;

import android.app.WallpaperManager;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EditActivity extends AppCompatActivity {


    private EditText edit_content;
    private ImageView buttonSave;
    private ImageView buttonBack;
    private ImageView buttonMain;
    private DatasDB DB;
    private SQLiteDatabase dbread;
    public static int ENTER_STATE = 0;
    public static String last_content;
    public static int id;
    private String content;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_layout);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }

        edit_content=(EditText) findViewById(R.id.et_content) ;
        DB = new DatasDB(this);
        dbread = DB.getReadableDatabase();

        Bundle myBundle = this.getIntent().getExtras();
        last_content = myBundle.getString("info");
        edit_content.setText(last_content);

        buttonMain= (ImageView) findViewById(R.id.edit_main);
        buttonMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //绘制Bitmap
                content = edit_content.getText().toString().trim();
                bitmap = BitmapFactory.decodeResource(EditActivity.this.getResources(), R.drawable.background);
                if (content != null && content != "") {

                    int verticalPosition=910;   //第一行起始位置
                    int max=11; //一行最大长度
                    int line;//行数
                    if(content.length()%max==0){
                        line=content.length()/max;
                    }
                    else {
                        line=content.length()/max+1;
                    }
                    int textSize=75;

                    TextPaint textPaint = new TextPaint();
                    textPaint.setAntiAlias(true);
                    textPaint.setTextSkewX((float)-0.25);   //倾斜度
                    textPaint.setTextSize(textSize);
                    textPaint.setTypeface(Typeface.DEFAULT_BOLD);

                    bitmap = Bitmap.createBitmap(1080,1920, Bitmap.Config.ARGB_8888);
                    Canvas canvas = new Canvas(bitmap);
                    canvas.drawColor(Color.WHITE);
                    for(int n=0;n<line;n++){
                        if(n==(line-1)||line==1){
                            canvas.drawText(content.substring(n*max), 120, verticalPosition+textSize*n+25, textPaint);
                            break;
                        }
                        canvas.drawText(content.substring(n*max,max*(n+1)), 120, verticalPosition+textSize*n+25, textPaint);
                    }

                    canvas.drawBitmap(bitmap, 0, 0, null);
                }else{
                    Toast.makeText(EditActivity.this,"输入内容不能为空",Toast.LENGTH_SHORT);
                }



            //设置壁纸
                WallpaperManager mWallManager = WallpaperManager.getInstance(EditActivity.this);
                try {
                    mWallManager.setBitmap(bitmap);
                    Toast.makeText(EditActivity.this, "导出壁纸成功", Toast.LENGTH_SHORT) .show(); }
                catch (IOException e) { e.printStackTrace(); }

            }
        });


        buttonSave = (ImageView) findViewById(R.id.button_save);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                // 获取内容
                String content = edit_content.getText().toString();
                Date date = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd k:m");
                String dateNum = dateFormat.format(date);
                String sql;
                String sql_count = "SELECT COUNT(*) FROM note";
                SQLiteStatement statement = dbread.compileStatement(sql_count);
                long count = statement.simpleQueryForLong();

                // 添加一个新的备忘录
                if (ENTER_STATE == 0) {
                    if (!content.equals("")) {
                        sql = "insert into " + DatasDB.TABLE_NAME_NOTES
                                + " values(" + count + "," + "'" + content
                                + "'" + "," + "'" + dateNum + "')";
                        dbread.execSQL(sql);
                    }
                }

                // 查看并修改一个已有的备忘录
                else {
                    String updatesql = "update note set content='"
                            + content + "' where _id=" + id;
                    dbread.execSQL(updatesql);
                }
                Intent data = new Intent();
                setResult(2, data);
                finish();
            }
        });

        buttonBack=(ImageView)findViewById(R.id.button_back) ;
        buttonBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                finish();
            }
        });

    }

}

