package com.example.a11579.easylifework;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SetWork extends AppCompatActivity
        implements AbsListView.OnScrollListener,
        AdapterView.OnItemClickListener,
        AdapterView.OnItemLongClickListener{
    private Context mContext;
    private ListView listview2;
    private SimpleAdapter simp_adapter;
    private List<Map<String, Object>> dataList;
    private Button addNote;
    private Button setTime;
    private TextView tv_content;
    private NotesDB DB;
    private SQLiteDatabase dbread;
    int ifclick =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_set_work);
        tv_content = (TextView) findViewById(R.id.tv_content);
        listview2 = (ListView) findViewById(R.id.listview2);
        dataList = new ArrayList<>();

        addNote = (Button) findViewById(R.id.btn_editnote3);
        setTime=(Button)findViewById(R.id.btn_editnote2);
        setTime.setEnabled(false);
        setTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ifclick=1;
                Intent intent=new Intent(SetWork.this,SetTime.class);
                startActivity(intent);
                SetWork.this.finish();
            }
        });
        mContext = this;
        addNote.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                noteEdit.ENTER_STATE = 0;
                setTime.setEnabled(true);
                Intent intent = new Intent(mContext, noteEdit.class);
                Bundle bundle = new Bundle();
                bundle.putString("info", "");
                intent.putExtras(bundle);
                startActivityForResult(intent, 1);
            }
        });
        DB = new NotesDB(this);
        dbread = DB.getReadableDatabase();
        RefreshNotesList();

        listview2.setOnItemClickListener(this);
        listview2.setOnItemLongClickListener(this);
        listview2.setOnScrollListener(this);
    }
    public void RefreshNotesList() {

        int size = dataList.size();
        if (size > 0) {
            dataList.removeAll(dataList);
            simp_adapter.notifyDataSetChanged();
            listview2.setAdapter(simp_adapter);
        }
        simp_adapter = new SimpleAdapter(this, getData(), R.layout.item,
                new String[] { "tv_content", "tv_date" }, new int[] {
                R.id.tv_content, R.id.tv_date });
        listview2.setAdapter(simp_adapter);
    }
    private List<Map<String, Object>> getData() {

        Cursor cursor = dbread.query("note", null, "content!=\"\"", null, null,
                null, null);

        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex("content"));
            String date = cursor.getString(cursor.getColumnIndex("date"));
            Map<String, Object> map = new HashMap<>();
            map.put("tv_content", name);
            map.put("tv_date", date);
            dataList.add(map);
        }
        cursor.close();
        return dataList;

    }

    @Override
    public void onScrollStateChanged(AbsListView arg0, int arg1) {
        switch (arg1) {
            case SCROLL_STATE_FLING:
                Log.i("main", "用户在手指离开屏幕之前，由于用力的滑了一下，视图能依靠惯性继续滑动");
            case SCROLL_STATE_IDLE:
                Log.i("main", "视图已经停止滑动");
            case SCROLL_STATE_TOUCH_SCROLL:
                Log.i("main", "手指没有离开屏幕，试图正在滑动");
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        noteEdit.ENTER_STATE = 1;
        String content = listview2.getItemAtPosition(arg2) + "";
        String content1 = content.substring(content.indexOf("=") + 1,
                content.indexOf(","));
        Log.d("CONTENT", content1);
        Cursor c = dbread.query("note", null,
                "content=" + "'" + content1 + "'", null, null, null, null);
        while (c.moveToNext()) {
            String No = c.getString(c.getColumnIndex("_id"));
            Log.d("TEXT", No);
            Intent myIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString("info", content1);
            noteEdit.id = Integer.parseInt(No);
            myIntent.putExtras(bundle);
            myIntent.setClass(SetWork.this, noteEdit.class);
            startActivityForResult(myIntent, 1);
        }

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 2) {
            RefreshNotesList();
        }
    }
    @Override
    public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
                                   long arg3) {
        final int n=arg2;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("删除该任务");
        builder.setMessage("确认删除吗？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String content = listview2.getItemAtPosition(n) + "";
                String content1 = content.substring(content.indexOf("=") + 1,
                        content.indexOf(","));
                Cursor c = dbread.query("note", null, "content=" + "'"
                        + content1 + "'", null, null, null, null);
                while (c.moveToNext()) {
                    String id = c.getString(c.getColumnIndex("_id"));
                    String sql_del = "update note set content='' where _id="
                            + id;
                    dbread.execSQL(sql_del);
                    RefreshNotesList();
                }
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.create();
        builder.show();
        return true;
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (((keyCode == KeyEvent.KEYCODE_BACK) ||
                (keyCode == KeyEvent.KEYCODE_HOME))
                && event.getRepeatCount() == 0&&ifclick==0) {
            dialog_Exit(SetWork.this);
        }
        return false;
    }

    public static void dialog_Exit(Context context) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
        builder.setMessage("确定要退出吗?还没有设定任务或时间哦！");
        builder.setTitle("提示");
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setPositiveButton("确认",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        android.os.Process.killProcess(android.os.Process
                                .myPid());
                    }
                });

        builder.setNegativeButton("取消",
                new android.content.DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }
}

