package com.example.caea8.memoranduml;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListActivity extends AppCompatActivity {

    private ListView listView;
    private List<Map<String, Object>> dataList;
    private DatasDB DB;
    private SQLiteDatabase database;
    private SimpleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_layout);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }

        dataList = new ArrayList<Map<String, Object>>();
        listView = (ListView) findViewById(R.id.listview);

        DB = new DatasDB(this);
        database = DB.getReadableDatabase();
        RefreshNotesList();


        ImageView button=(ImageView) findViewById(R.id.image_listbutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditActivity.ENTER_STATE=0;
                Intent intent = new Intent(ListActivity.this, EditActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("info", "");
                intent.putExtras(bundle);
                startActivityForResult(intent, 1);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EditActivity.ENTER_STATE = 1;
                String content = listView.getItemAtPosition(position) + "";
                String content1 = content.substring(content.indexOf("=") + 1,
                        content.indexOf(","));
                Cursor c = database.query("note", null,
                        "content=" + "'" + content1 + "'", null, null, null, null);
                while (c.moveToNext()) {
                    String No = c.getString(c.getColumnIndex("_id"));
                    Intent myIntent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putString("info", content1);
                    EditActivity.id = Integer.parseInt(No);
                    myIntent.putExtras(bundle);
                    myIntent.setClass(ListActivity.this, EditActivity.class);
                    startActivityForResult(myIntent, 1);
                }
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                Builder builder = new AlertDialog.Builder(ListActivity.this);
                builder.setTitle("确定删除该记录吗？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String content = listView.getItemAtPosition(position) + "";
                        String content1 = content.substring(content.indexOf("=") + 1,
                                content.indexOf(","));
                        Cursor c = database.query("note", null, "content=" + "'"
                                + content1 + "'", null, null, null, null);
                        while (c.moveToNext()) {
                            String id = c.getString(c.getColumnIndex("_id"));
                            String sql_del = "update note set content='' where _id="
                                    + id;
                            database.execSQL(sql_del);
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
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 2) {
            RefreshNotesList();
        }
    }

    public void RefreshNotesList() {

        int size = dataList.size();
        if (size > 0) {
            dataList.removeAll(dataList);
            adapter.notifyDataSetChanged();
            listView.setAdapter(adapter);
        }
        adapter = new SimpleAdapter(this, getData(), R.layout.list_item,
                new String[] { "note_content", "note_date" }, new int[] {
                R.id.note_content, R.id.note_date });
        listView.setAdapter(adapter);
    }

    private List<Map<String, Object>> getData() {

        Cursor cursor = database.query("note", null, "content!=\"\"", null, null,
                null, null);

        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex("content"));
            String date = cursor.getString(cursor.getColumnIndex("date"));
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("note_content", name);
            map.put("note_date", date);
            dataList.add(map);
        }
        cursor.close();
        return dataList;

    }

}
