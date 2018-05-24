package com.example.a11579.easylifework;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class noteEdit extends AppCompatActivity {
    private TextView tv_date;
    private EditText et_content;
    private Button btn_ok;
    private Button btn_cancel;
    private NotesDB DB;
    Toast toast=null;
    private SQLiteDatabase dbread;
    public static int ENTER_STATE = 0;
    public static String last_content;
    public static int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_edit);
        tv_date = (TextView) findViewById(R.id.tv_date);
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String dateString = sdf.format(date);
        tv_date.setText(dateString);

        et_content = (EditText) findViewById(R.id.et_content);
        // 设置软键盘自动弹出
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        DB = new NotesDB(this);
        dbread = DB.getReadableDatabase();

        Bundle myBundle = this.getIntent().getExtras();
        last_content = myBundle.getString("info");
        Log.d("LAST_CONTENT", last_content);
        et_content.setText(last_content);
        // 确认按钮的点击事件
        btn_ok = (Button) findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                // 获取日志内容
                String content = et_content.getText().toString();
                if (et_content.getText().toString().equals("")) {
                    toast = Toast.makeText(getApplicationContext(), "还没有输入内容哦！", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else {
                    Log.d("LOG1", content);
                    // 获取写日志时间
                    Date date = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String dateNum = sdf.format(date);
                    String sql;
                    String sql_count = "SELECT COUNT(*) FROM note";
                    SQLiteStatement statement = dbread.compileStatement(sql_count);
                    long count = statement.simpleQueryForLong();
                    Log.d("COUNT", count + "");
                    Log.d("ENTER_STATE", ENTER_STATE + "");
                    // 添加一个新的日志
                    if (ENTER_STATE == 0) {
                        if (!content.equals("")) {
                            sql = "insert into " + NotesDB.TABLE_NAME_NOTES
                                    + " values(" + count + "," + "'" + content
                                    + "'" + "," + "'" + dateNum + "')";
                            Log.d("LOG", sql);
                            dbread.execSQL(sql);
                        }
                    }
                    // 查看并修改一个已有的日志
                    else {
                        Log.d("执行命令", "执行了该函数");
                        String updatesql = "update note set content='"
                                + content + "' where _id=" + id;
                        dbread.execSQL(updatesql);
                        // et_content.setText(last_content);
                    }
                    Intent data = new Intent();
                    setResult(2, data);
                    finish();
                }
            }});
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                finish();
            }
        });
    }@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}
