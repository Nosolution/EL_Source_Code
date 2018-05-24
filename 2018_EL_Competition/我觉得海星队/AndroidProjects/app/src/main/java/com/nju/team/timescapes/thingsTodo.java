package com.nju.team.timescapes;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 2018/5/23.
 */

public class thingsTodo extends AppCompatActivity{
    private ImageButton ib;
    private ListView lv;
    private ArrayAdapter<String> arrayAdapter;
    private List<String> list;
    private Handler handler;
    private ImageButton back_to_cal;
    private Button buttonClean;
    private MyAdapter myAdapter;
    private ImageView default_pic;
    private TextView default_text;
    private SharedPreferences msharedPreferences;


    //activity生命周期
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thingstodo);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        //任务栏透明
        changeStatusBarColor();


        //读calender的数据
        Intent intent = getIntent();
        final String datetime = intent.getStringExtra("datetime");
        msharedPreferences = getSharedPreferences("YEAR_MONTH_DATE",MODE_PRIVATE);
        SharedPreferences.Editor editor = msharedPreferences.edit();
        String thing = msharedPreferences.getString("INTENT","1970年1月1日");
        editor.putString("YEAR_MONTH_DATE",thing);
        editor.commit();


        list=readData("info.txt");

        //转换字符存入新arraylist
        final int size = list.size();

        for(int i=0;i<size;i++){
            String word = list.get(i);
            String replace = word.replaceAll("@@@@@","   |   ");
            list.set(i,replace);
        }

        lv=findViewById(R.id.lv_1);
        myAdapter = new MyAdapter(thingsTodo.this, R.layout.layout_item_recycleview, list);
        lv.setAdapter(myAdapter);
        lv.setDividerHeight(2);
        lv.setDivider(new ColorDrawable(Color.argb(255,124,113,67)));
        lv.setDividerHeight(2);
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                dialog1(list, position, myAdapter);
                myAdapter.notifyDataSetChanged();
                lv.setAdapter(myAdapter);
                return true;
            }
        });

        //如果有日程则不显示图片和语句
        default_pic = (ImageView) findViewById(R.id.no_arrange_pic);
        default_text = (TextView) findViewById(R.id.no_arrange_hint);
        if(list.size() != 0){
            default_pic.setVisibility(View.GONE);
            default_text.setVisibility(View.GONE);
        }else{
            default_pic.setVisibility(View.VISIBLE);
            default_text.setVisibility(View.VISIBLE);
        }

        ib=findViewById(R.id.ib_1);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent first=new Intent(thingsTodo.this,add_thing.class);

                first.putExtra("date_time",datetime);

                startActivity(new Intent(first));
                thingsTodo.this.finish();
            }
        });

        back_to_cal = (ImageButton) findViewById(R.id.ib_back);
        back_to_cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(thingsTodo.this, calendar.class);
                thingsTodo.this.startActivity(mainIntent);
                thingsTodo.this.finish();
            }
        });

        //一件清空
        buttonClean=findViewById(R.id.btn_clean_out);
        buttonClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog2(list,myAdapter);
                lv.setAdapter(myAdapter);
            }
        });
    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                Intent mainIntent = new Intent(thingsTodo.this, calendar.class);
                thingsTodo.this.startActivity(mainIntent);
                thingsTodo.this.finish();
                break;

            default:
                break;
        }
        return false;
    }

    protected void onResume() {
        super.onResume();
        list = readData("info.txt");

        //换行符设置
        int size = list.size();
        for(int i=0;i<size;i++){
            String word = list.get(i);
            String replace = word.replaceAll("@@@@@","   |   ");
            list.set(i,replace);
        }

        lv = findViewById(R.id.lv_1);
        myAdapter = new MyAdapter(thingsTodo.this, android.R.layout.simple_list_item_1, list);
        lv.setAdapter(myAdapter);
    }


    //读取
    private ArrayList<String> readData(String filename){
        File textsDir = new File(this.getFilesDir().getAbsolutePath() + File.separator + "texts");
        if(!textsDir.exists()){
            textsDir.mkdir();
        }

        File file=new File(textsDir,filename);
        BufferedReader reader=null;
        String temp=null;
        ArrayList<String> settings = new ArrayList<String>();
        try{
            reader=new BufferedReader(new FileReader(file));
            while((temp=reader.readLine())!=null){
                settings.add(temp+"\n");
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            if(reader!=null){
                try{
                    reader.close();
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
        return settings;
    }

    //存数据
    private void savenew(List<String> strings,String filename) {

        String content = listToString(strings);
        File textsDir = new File(this.getFilesDir().getAbsolutePath() + File.separator + "texts");
        if(!textsDir.exists()){
            textsDir.mkdir();
        }
        File file = new File(textsDir,filename);
        try {

            FileOutputStream fos = new FileOutputStream(file);
            fos.write((content).getBytes());
            fos.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static String listToString(List<String> list) {
        if (list == null) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        boolean first = true;
        //第一个前面不拼接","
        for (String string : list) {
            if (first) {
                first = false;
            } else {
                result.append(",");
            }
            result.append(string);
        }
        return result.toString();

    }

    private void dialog1(final List<String> list1, final int position, final ArrayAdapter arrayAdapter){
        //先new出一个监听器，设置好监听
        DialogInterface.OnClickListener dialogOnclicListener=new DialogInterface.OnClickListener(){

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch(which){
                    case Dialog.BUTTON_POSITIVE:
                        list1.remove(0);
                        arrayAdapter.remove(position);
                        savenew(list1,"info.txt");
                        if(list.size() != 0){
                            default_pic.setVisibility(View.GONE);
                            default_text.setVisibility(View.GONE);
                        }else{
                            default_pic.setVisibility(View.VISIBLE);
                            default_text.setVisibility(View.VISIBLE);
                        }

                        break;
                    case Dialog.BUTTON_NEGATIVE:
                        //Toast.makeText(thingsTodo.this, "取消" + which, Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
        //dialog参数设置
        AlertDialog.Builder builder=new AlertDialog.Builder(this);  //先得到构造器
        builder.setTitle("删除日程"); //设置标题
        builder.setMessage("您是否要删除这项日程?"); //设置内容
        builder.setPositiveButton("确定",dialogOnclicListener);
        builder.setNegativeButton("取消", dialogOnclicListener);
        builder.create().show();
    }


    //判断清空的dilog
    private void dialog2(final List<String> list1, final ArrayAdapter arrayAdapter){
        //先new出一个监听器，设置好监听
        DialogInterface.OnClickListener dialogOnclicListener=new DialogInterface.OnClickListener(){

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch(which){
                    case Dialog.BUTTON_POSITIVE:
                        list1.clear();
                        savenew(list1,"info.txt");
                        arrayAdapter.clear();
                        default_pic.setVisibility(View.VISIBLE);
                        default_text.setVisibility(View.VISIBLE);

                        break;
                    case Dialog.BUTTON_NEGATIVE:
                        //Toast.makeText(thingsTodo.this, "取消" + which, Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
        //dialog参数设置
        AlertDialog.Builder builder=new AlertDialog.Builder(this);  //先得到构造器
        builder.setTitle("删除日程"); //设置标题
        builder.setMessage("您是否要删除这项日程?"); //设置内容
        builder.setPositiveButton("确定",dialogOnclicListener);
        builder.setNegativeButton("取消", dialogOnclicListener);
        builder.create().show();
    }

    private void saveData(String data, String filename) {


        String content = data + "\n";
        File textsDir = new File(this.getFilesDir().getAbsolutePath() + File.separator + "texts");
        if (!textsDir.exists()) {
            textsDir.mkdir();
        }
        File file = new File(textsDir, filename);
        try {
            FileOutputStream fos = new FileOutputStream(file, true);
            fos.write((content).getBytes());
            fos.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private class MyAdapter<T> extends ArrayAdapter {
        public MyAdapter(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
        }


        //重载getView函数，等于说该函数完全接管ArrayAdapter的设置TextView操作
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            String str = (String) getItem(position);//通过position获取当前要赋值的内容
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_item_recycleview, parent, false);
            }
            TextView tv = (TextView) convertView.findViewById(R.id.item_1);
            tv.setText(str);//设置TextView中的字符串
            tv.setTextSize(14);//设置大小
            tv.setTextColor(Color.rgb(255, 229, 125));
            return convertView;
        }

    }
}
