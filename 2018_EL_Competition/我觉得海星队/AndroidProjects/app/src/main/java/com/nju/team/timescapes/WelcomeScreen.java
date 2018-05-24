package com.nju.team.timescapes;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import java.io.*;
import java.util.ArrayList;


/**
 * Created by ASUS on 2018/5/6.
 */

public class WelcomeScreen extends AppCompatActivity{
    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private int[] layouts;
    private PrefManager prefManager;
    private Button btn_entry;

    private String filename = "user_settings.txt";
    private String DEFAULT_SETTINGS = "false"+"\r\n"+"default_name"+"\r\n"+"default_pass"+"\r\n"+"vibrate"+
            "\r\n"+"false"+"\r\n"+"10"+"\r\n"+"true"+"\r\n"+"true"+"\r\n"+"true"+"\r\n"+"false"+"\r\n"+"0"+"\r\n"+
            "0"+"\r\n"+"0"+"\r\n"+"0"+"\r\n"+"0"+"\r\n"+"false"+"\r\n"+"false"+"\r\n"+"false"+"\r\n"+"false"+"\r\n"
            +"false"+"\r\n"+"false"+"\r\n"+"未设定"; //除末尾外后5位为成就标志位

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //      根据版本号判断是不是第一次使用
        PackageInfo info=null;
        try {
            info=getPackageManager().getPackageInfo(getPackageName(),0);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        int currentVersion = info.versionCode;
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);

        int lastVersion = sp.getInt("VERSION_KEY", 0);
        if (currentVersion<=lastVersion){
//            第一次启动将当前版本进行存储

            Intent intent=new Intent(this,MainScreenActivity.class);
            intent.setClass(this, MainScreenActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        } else{
            saveData(DEFAULT_SETTINGS,filename);
        }

        sp.edit().putInt("VERSION_KEY",currentVersion).commit();

        //在setContentView()前检查是否第一次运行
        prefManager = new PrefManager(this);
        if(!prefManager.isFirstTimeLaunch()){
            launchHomeScreen();
            finish();
        }

        //让状态栏透明
        if(Build.VERSION.SDK_INT >= 21){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE|View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        setContentView(R.layout.viewpager_welcome);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);

        //指定按钮
        btn_entry = (Button) findViewById(R.id.entrance_button);

        //添加欢迎页面
        layouts = new int[]{
                R.layout.welcome_screen_one,
                R.layout.welcome_screen_two,
                R.layout.welcome_screen_three,
                R.layout.welcome_screen_four,
                R.layout.welcome_screen_five
        };
        //添加点
        addBottomDots(0);

        //让状态栏透明
        changeStatusBarColor();

        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        //按钮事件
        btn_entry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(WelcomeScreen.this, MainScreenActivity.class);
                WelcomeScreen.this.startActivity(mainIntent);
                WelcomeScreen.this.finish();
            }
        });

    }

    private void addBottomDots(int currentPage){
        dots = new TextView[layouts.length];

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        dotsLayout.removeAllViews();
        for(int i = 0; i < dots.length; i++){
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive[currentPage]);
            dotsLayout.addView(dots[i]);
        }

        if(dots.length > 0){
            dots[currentPage].setTextColor(colorsActive[currentPage]);
        }
    }

    private int getItem(int i){
        return viewPager.getCurrentItem() + i;
    }

    private void launchHomeScreen(){
        prefManager.setFirstTimeLaunch(false);
        startActivity(new Intent(this,MainScreenActivity.class));
        finish();
    }

    /**
     * 让状态栏变透明
     */
    private void changeStatusBarColor(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }


    //存数据
    private void saveData(String data,String filename) {

        String content = data + "\n";
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


    //读数据
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


    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener(){

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);

            if(position == layouts.length - 1){
                btn_entry.setVisibility(View.VISIBLE);
            }else{
                btn_entry.setVisibility(View.GONE);
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    public class MyViewPagerAdapter extends PagerAdapter{

        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter(){}

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(layouts[position],container,false);
            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View)object;
            container.removeView(view);
        }
    }
}
