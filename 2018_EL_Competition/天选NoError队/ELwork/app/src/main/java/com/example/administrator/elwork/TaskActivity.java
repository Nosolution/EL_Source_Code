package com.example.administrator.elwork;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import java.lang.String;

import android.widget.TextView;

public class TaskActivity extends AppCompatActivity {

    private String[] tasks = new String[100];
    final Context context=this;
    //悬浮按钮
    private FloatingActionButton floatingActionButton;
    //private EditText result;
    private EditText userInput;
    private SharedPreferences.Editor editor;
    private SharedPreferences pref;
    private ArrayAdapter<Task> adapter = null;
    private ListView listView;
    final static String KEY = "new_task";
    private TextView mTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        listView=(ListView)findViewById(R.id.List_view);
        //result = (EditText) findViewById(R.id.editTextResult);
        //userInput = (EditText)findViewById(R.id.editTextDialogUserInput);
        //mTextView = (TextView)findViewById(R.id.id_task);


        //实例化适配器
        adapter = new ArrayAdapter<Task>(getBaseContext(),
                android.R.layout.simple_list_item_1);

        readTask();//读取物理文件数据
        //绑定适配器
        listView.setAdapter(adapter);

        //点击悬浮按钮，添加任务
        floatingActionButton = (FloatingActionButton)findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.add(new Task("New Task"));

                saveTask();
                listView.setAdapter(adapter);
            }

        });


        //给列表添加点击事件,点击添加内容
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //get prompts.xml view
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.prompts,null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                mTextView = (TextView)promptsView.findViewById(R.id.id_task);

                //set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                userInput = (EditText)promptsView.findViewById(R.id.editTextDialogUserInput);

                //set dialog message
                //这是不可修改的？？？
                //对话框弹出的两个按钮可以修改掉
                alertDialogBuilder.setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        //get user input and set it to result
                                        //edit text
                                        String str = userInput.getText().toString();
                                        String[] arr = {str};
                                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(TaskActivity.this,
                                                android.R.layout.simple_list_item_1,arr);
                                        saveTask();
                                        listView.setAdapter(arrayAdapter);
                                        mTextView.setText(String.valueOf(userInput.getText()));
                                        //result.setText(userInput.getText());
                                        //mTextView.setText(String.valueOf(userInput.getText()));
                                    }
                                })
                        .setNegativeButton("Cancle",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int id) {
                                        dialogInterface.cancel();
                                    }
                                });

                //create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                //show
                alertDialog.show();

            }
        });

        //列表长按删除
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?>parent,View view,final int position,
                                           long id){
                //对话框
                new AlertDialog.Builder(TaskActivity.this).setTitle("Are you sure to delete it?")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                deleteTask(position);

                            }
                        }).setNegativeButton("Cancel",null).show();
                return true;
            }
        });
    }

    /**
     * SharedPreferences存储数据
     */
    public void saveTask(){
        editor = getSharedPreferences("tasks",MODE_PRIVATE).edit();
        //创建一个string buffer
        StringBuffer buffer = new StringBuffer();
        for(int i=0;i<adapter.getCount();i++){
            String dataString = adapter.getItem(i).getTask_name();
            buffer.append(dataString).append(",");
        }
        if(buffer.length()>1){
            //去掉后面的？？？
            String content = buffer.toString().substring(0,buffer.length()-1);
            editor.putString(KEY,content);
        }else{
            editor.putString(KEY,null);
        }
        editor.commit();
    }

    /**
     * SharedPreferences读取存储的数据
     */
    public void readTask(){
        pref = getSharedPreferences("tasks",MODE_PRIVATE);
        String content = pref.getString(KEY,null);
        if(content!=null){
            String[] contntStr=content.split(",");
            for(String string:contntStr){
                adapter.add(new Task(string));
            }
        }
    }

    /**
     * 删除数据
     */
    public void deleteTask(int position){
        adapter.remove(adapter.getItem(position));
        saveTask();
    }
}