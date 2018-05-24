package com.example.administrator.el_done1;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.el_done1.view.DragCircleProgress;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private ActionBarDrawerToggle drawerbar;
    public DrawerLayout drawerLayout;
    private RelativeLayout leftSide,rightSide;
    public static String DeskPetName="DeskPetWanzi";//用来选择桌宠，默认为第一个

    public static String name=UserInformation.user_name;
    public static int times=for_counting_times.times_done;
    public static String motto=UserInformation.motto;




    //xjb的部分
    private MediaPlayer mp=new MediaPlayer();
    private RoundedImageView play;
    boolean onPlay = false;
    //xjb的部分


    //这是时钟的完成次数统计
    public static void times(){
        for_counting_times.times_done++;
    }

    //重写活动销毁方式，用ActivityManager来销毁
    @Override
    protected void onDestroy(){
        super.onDestroy();

        //xjb的部分
        if (mp != null) {
            mp.stop();
            mp.release();
        }
        //xjb的部分

        ActivityManager.removeActivity(this);
    }

    public static void setDeskPet(String s){
        DeskPetName = s;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //根据主题设置字体
        switch (Theme.getTHEME()){
            case "SIMPLE":
                setTheme(R.style.SIMPLE_THEME);
                break;
            case "OTAKU":
                setTheme(R.style.OTAKU_THEME);
                break;
            case "PET":
                setTheme(R.style.PET_THEME);
                break;
        }

        //设立layout
        setContentView(R.layout.main_layout);

        //存入ActivityManager
        ActivityManager.addActivity(this);

//xjb的部分
        play=(RoundedImageView) findViewById(R.id.music_button);
        mp =MediaPlayer.create(MainActivity.this, R.raw.my);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onPlay){
                    MainActivity.this.mp.stop();
                    onPlay=false;
                }
                else {
                    MainActivity.this.mp =MediaPlayer.create(MainActivity.this, R.raw.my);
                    mp.start();
                    onPlay=true;
                }
            }
        });
//xjb的部分


        Button test = (Button)findViewById(R.id.test);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,TimerCancelDialogActivity.class);
                startActivity(intent);
            }
        });

        //设置头像
        RoundedImageView button_drawer_left = (RoundedImageView)findViewById(R.id.button_drawer_left);
        RoundedImageView face_image = (RoundedImageView)findViewById(R.id.face_image);


        final TextView set_motto=(TextView)findViewById(R.id.motto);
        final TextView set_na=(TextView)findViewById(R.id.name);
        final TextView set_time=(TextView)findViewById(R.id.points);



        set_na.setText(name);
        set_time.setText(Integer.toString(times));
        set_motto.setText(motto);




        if (UserInformation.getBitmap()!=null){
            face_image.setImageBitmap(UserInformation.getBitmap());
            button_drawer_left.setImageBitmap(UserInformation.getBitmap());
        }


        //根据主题设置字体和左滑栏窗口背景颜色、按钮风格
        RelativeLayout left_side_layout =(RelativeLayout)findViewById(R.id.left_side);
        RoundedImageView button_drawer_right = (RoundedImageView)findViewById(R.id.button_drawer_right);
        RoundedImageView center_timer_image = (RoundedImageView)findViewById(R.id.center_timer_image);
        TextView deskPetTalkView = (TextView)findViewById(R.id.deskPetTalk);
        switch (Theme.getTHEME()){
            case "SIMPLE":
                button_drawer_right.setImageResource(R.drawable.user_treasure_button_simple);
                left_side_layout.setBackgroundResource(R.drawable.left_bg_simple);
                center_timer_image.setImageResource(R.drawable.clock_simple);
                deskPetTalkView.setBackgroundResource(R.drawable.pet_talk_bg_simple);
                break;
            case "OTAKU":
                button_drawer_right.setImageResource(R.drawable.user_treasure_button_otaku);
                left_side_layout.setBackgroundResource(R.drawable.left_bg_otaku);
                center_timer_image.setImageResource(R.drawable.clock_otaku);
                deskPetTalkView.setBackgroundResource(R.drawable.pet_talk_bg_otaku);
                break;
            case "PET":
                button_drawer_right.setImageResource(R.drawable.user_treasure_button_cut_pet);
                left_side_layout.setBackgroundResource(R.drawable.left_bg_cute_pet);
                center_timer_image.setImageResource(R.drawable.clock_cute_pet);
                deskPetTalkView.setBackgroundResource(R.drawable.pet_talk_bg_cute_pet);
                break;
        }

        //隐藏应用默认标题栏
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!= null){
            actionBar.hide();
        }

        initLayout();
        initEvent();
        Button button_treasure_scene = (Button)findViewById(R.id.treasure_case_scene);
//        Button button_treasure_decoration = (Button)findViewById(R.id.treasure_case_decorations);
        Button button_treasure_pet = (Button)findViewById(R.id.treasure_case_pet);
        replaceFragment(new TreasureDeskPetFragment());
/*        button_treasure_decoration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new TreasureDecorationFragment());
            }
        });
 删了装饰按钮*/

        button_treasure_pet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new TreasureDeskPetFragment());
            }
        });
        button_treasure_scene.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new TreasureBGFragment());
            }
        });



        //左滑栏按钮
        Button button_dairy = (Button)findViewById(R.id.dairy);
        Button button_weekReport = (Button)findViewById(R.id.weekReport);
        Button button_friends = (Button)findViewById(R.id.friends);
        Button button_setting = (Button)findViewById(R.id.setting);
        RoundedImageView shuaxin = (RoundedImageView)findViewById(R.id.self_refresh_button);
        button_dairy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DairyActivity.class);
                startActivity(intent);
            }
        });
        button_friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FriendsActivity.class);
                startActivity(intent);
            }
        });
        button_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });
        button_weekReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, WeekReportActivity.class);
                startActivity(intent);
            }
        });
        shuaxin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                name=UserInformation.user_name;
                times=for_counting_times.times_done;
                motto=UserInformation.motto;
                set_na.setText(name);
                set_time.setText(Integer.toString(times));
                set_motto.setText(motto);

            }
        });



        //底部功能栏按钮
        center_timer_image = (RoundedImageView)findViewById(R.id.center_timer_image);
        RoundedImageView center_browser_image = (RoundedImageView)findViewById(R.id.center_browser_image);
        center_timer_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DragCircleProgress timer = (DragCircleProgress)findViewById(R.id.timer);
                RoundedImageView center_timer_image = (RoundedImageView)findViewById(R.id.center_timer_image);
                if (timer.getVisibility()==View.VISIBLE){
                    timer.setVisibility(View.INVISIBLE);
                    Toast.makeText(MainActivity.this,"时钟已隐藏",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    timer.setVisibility(View.VISIBLE);
                    Toast.makeText(MainActivity.this,"拖动指针设置专注时间",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        center_browser_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http:www.baidu.com"));
                startActivity(intent);
                Toast.makeText(MainActivity.this,"百度一下你就知道",
                        Toast.LENGTH_SHORT).show();
            }
        });


        //点击桌宠说话
        ImageView deskPetView = (ImageView)findViewById(R.id.deskPetView);
        deskPetView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"主人~你想我了吗？",
                        Toast.LENGTH_SHORT).show();
                TextView deskPetTalkView = (TextView)findViewById(R.id.deskPetTalk);
                deskPetTalkView.setText("主人你想我了吗?");
                deskPetTalkView.setVisibility(View.VISIBLE);

            }
        });
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                petViewHandler.sendEmptyMessage(0x123);
            }
        },0,250);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                petTalkHandler.sendEmptyMessage(0x124);
            }
        },0,5000);
    }

/*
    //xjb的部分

    //判断文件是否存在
    private boolean  isFileExist(){
        file=new File(Environment.getExternalStorageDirectory()+File.separator+"myMP32.mp3");
        if(file.exists()){
            //  mediaPlayer=new MediaPlayer();
            mediaPlayer=MediaPlayer.create(this,R.raw.my);
            try {
//                mediaPlayer.setDataSource(file.getAbsolutePath());
//                mediaPlayer.prepare();//预加载音频
//                mediaPlayer.start();//播放音乐
            }catch (Exception e) {
                e.printStackTrace();
            }
            Toast.makeText(this,"file exist",Toast.LENGTH_LONG).show();
            return true;
        }else{
            Toast.makeText(this,"file don't exist",Toast.LENGTH_LONG).show();
        }
        return false;
    }
    //播放音乐的方法
    private void play(){
        try{
            mediaPlayer.reset();//从新设置要播放的音乐
            //           mediaPlayer.setDataSource(file.getAbsolutePath());
//            mediaPlayer.prepare();//预加载音频
            mediaPlayer=MediaPlayer.create(this,R.raw.my);
            mediaPlayer.start();//播放音乐
            hint.setText("开始播放");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("err",e.getMessage());
        }
        return ;
    }

    //xjb的部分
*/



    //-----------------以下是桌宠动作部分---------------------------------------

        //桌宠运动
    Handler petViewHandler = new Handler(){
        int i = 0;
        @Override
        public void handleMessage(Message msg){
            //判断信息是否为本应用发出的
            if (msg.what == 0x123){
                i++;
                if (i==15){
                    i=0;
                }
                ImageView deskPetView = (ImageView)findViewById(R.id.deskPetView);
                switch (MainActivity.DeskPetName){
                    case "DeskPetWanzi":
                        deskPetView.setImageResource(DeskPetWanzi.getImageIdOf(i));
                        break;
                    case "DeskPetMoli":
                        deskPetView.setImageResource(DeskPetMoli.getImageIdOf(i));
                        break;
                    case "DeskPetChuyin":
                        deskPetView.setImageResource(DeskPetChuyin.getImageIdOf(i));
                        break;
                    case "DeskPetGumingdijue":
                        deskPetView.setImageResource(DeskPetGumingdijue.getImageIdOf(i));
                        break;
                    case "DeskPetMaoerniang":
                        deskPetView.setImageResource(DeskPetMaoerniang.getImageIdOf(i));
                        break;
                    case "DeskPetMaoerkunan":
                        deskPetView.setImageResource(DeskPetMaoerkunan.getImageIdOf(i));
                        break;
                    case "DeskPetYinshi":
                        deskPetView.setImageResource(DeskPetYinshi.getImageIdOf(i));
                        break;
                    case "DeskPetMaolaoshi":
                        deskPetView.setImageResource(DeskPetMaolaoshi.getImageIdOf(i));
                        break;
                    case "DeskPetPikaqiu":
                        deskPetView.setImageResource(DeskPetPikaqiu.getImageIdOf(i));
                        break;
                    default:
                        break;
                }
            }
            super.handleMessage(msg);
        }
    };

        //桌宠说话
    Handler petTalkHandler =new Handler(){
        int randomTalkCount;//用来是桌宠随机说话
        @Override
        public void handleMessage(Message msg){
            TextView deskPetTalkView = (TextView)findViewById(R.id.deskPetTalk);

            String text = "主人你想我了吗？\nO(∩_∩)O~";
            if (msg.what == 0x124){
                randomTalkCount=(int)(4*Math.random());
                if (deskPetTalkView.getVisibility()==View.VISIBLE){
                    deskPetTalkView.setVisibility(View.INVISIBLE);
                }
                else {
                    switch (randomTalkCount) {
                        case 0:
                            text = "主人你想我了吗？\nO(∩_∩)O~";
                            break;
                        case 1:
                            text = "主人要好好学习哦~\nヽ(￣▽￣)و";
                            break;
                        case 2:
                            text = "点击中间的时钟可以开始专注哦！";
                            break;
                        case 3:
                            text = "好无聊\nε=(´ο｀*)))唉";
                            break;
                        default:
                            break;
                    }
                    deskPetTalkView.setText(text);
                    deskPetTalkView.setVisibility(View.VISIBLE);
                }

            }


            super.handleMessage(msg);
        }
    };
    //--------------------以上是桌宠部分---------------------------



    //----------------以下是侧滑栏--------------------------------
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.treasure_fragment_view,fragment);
        transaction.commit();
    }

    public void initLayout(){
        drawerLayout = (DrawerLayout)findViewById(R.id.drawerLayout);

        leftSide = (RelativeLayout)findViewById(R.id.left_side);
        rightSide = (RelativeLayout)findViewById(R.id.treasure_case);
    }

    private void initEvent(){
        drawerbar =new ActionBarDrawerToggle(this,drawerLayout,
                R.string.open,R.string.close){
            @Override
            public void onDrawerOpened(View drawerView){
                super.onDrawerOpened(drawerView);
            }
            @Override
            public void onDrawerClosed(View drawerView){
                super.onDrawerClosed(drawerView);
            }
        };
        drawerLayout.setDrawerListener(drawerbar);
    }

    public void openLeftLayout(View view){
        if (drawerLayout.isDrawerOpen(leftSide)){
            drawerLayout.closeDrawer(leftSide);
        }
        else {
            drawerLayout.openDrawer(leftSide);
        }
    }
    public void openRightLayout(View view){
        if (drawerLayout.isDrawerOpen(rightSide)){
            drawerLayout.closeDrawer(rightSide);
        }
        else {
            drawerLayout.openDrawer(rightSide);
        }
    }
    //-----------------以上是侧滑栏-----------------------------------------





}
