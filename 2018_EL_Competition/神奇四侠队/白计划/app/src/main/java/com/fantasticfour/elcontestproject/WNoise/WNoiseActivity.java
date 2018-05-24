package com.fantasticfour.elcontestproject.WNoise;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fantasticfour.elcontestproject.Instance.Ins_WNoise.WNoise;
import com.fantasticfour.elcontestproject.Instance.Ins_WNoise.WNoisePreset;
import com.fantasticfour.elcontestproject.Instance.Instance;
import com.fantasticfour.elcontestproject.R;

import java.util.List;
import java.util.Map;

public class WNoiseActivity extends AppCompatActivity implements WNoiseSceneSave.CallbackSave, WNoiseSceneLoad2.CallBackLoad{
    private WNoiseScenePresetAdapter mAdapterPreset;
    private WNoiseAdapter mAdapter;
    private TextView mTextViewScene;
    private Button mAddWNoise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wnoise);


        List<WNoise> wnoiseList = Instance.s_WNoiseController.GetWNoiseList();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.cWnoiseList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        //加载音乐列表

        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new WNoiseAdapter(wnoiseList, this);
        recyclerView.setAdapter(mAdapter);
        WNoiseTouchHelperCallback callback = new WNoiseTouchHelperCallback(mAdapter);
        ItemTouchHelper ith = new ItemTouchHelper(callback);
        ith.attachToRecyclerView(recyclerView);
        mAdapter.SetItemTouchHelperCallback(callback);

        //加载preset列表
        /*
        List<WNoisePreset> WNoisePresetList = Instance.s_WNoiseController.GetPresetList();

        recyclerView.setLayoutManager(layoutManager);
        mAdapterPreset = new WNoiseScenePresetAdapter(WNoisePresetList);
        recyclerView.setAdapter(mAdapter);
        */

        //添加
        mAddWNoise = findViewById(R.id.AddWnoise);
        mAddWNoise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(WNoiseActivity.this, WNoiseAdd.class);
                startActivityForResult(intent,2);
            }
        });


        //静音
        final Button mute = (Button) findViewById(R.id.Mute);
        mute.setBackground(this.getDrawable(R.drawable.start_red));
        mute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(WNoiseActivity.this, "Mute", Toast.LENGTH_SHORT).show();

                Instance.s_WNoiseController.SetSilent(!Instance.s_WNoiseController.GetSilent());
                if(!Instance.s_WNoiseController.GetSilent()){
                    //Log.d("TAG","bofang");
                    mute.setBackground(WNoiseActivity.this.getDrawable(R.drawable.pause_brown));
                    Toast.makeText(WNoiseActivity.this,"startmusic",Toast.LENGTH_SHORT).show();
                }else{
                    mute.setBackground(WNoiseActivity.this.getDrawable(R.drawable.start_red));
                }
            }
        });

        //重置音乐
        Button reset = (Button) findViewById(R.id.Reset);
        reset.setBackground(this.getDrawable(R.drawable.reset_black));
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(WNoiseActivity.this, "Reset", Toast.LENGTH_SHORT).show();
                Instance.s_WNoiseController.ResetWNoiseVolume();
                mAdapter.Reset();
                //测试之后删掉！！！！！！！！！！！！！
            }
        });



        //random
        Button loadScene = (Button) findViewById(R.id.Random);
        loadScene.setBackground(this.getDrawable(R.drawable.random_black));
        loadScene.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mAdapter.SetRandom();

            }
        });

        //设置title点击切换场景
        mTextViewScene = findViewById(R.id.title_middletext);
        mTextViewScene.setText(Instance.s_WNoiseController.GetCurrentPreset().m_Name);
        /*
        mTextViewScene.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                showWNoiseSceneLoad2(v);
            }
        });
        */
        mTextViewScene.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(WNoiseActivity.this, WNoiseSceneActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        //保存当前场景
        Button saveScene = (Button) findViewById(R.id.SaveScene);
        saveScene.setBackground(this.getDrawable(R.drawable.save_black));
        saveScene.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWNoiseSceneSave(v);
            }
        });

        Button back = findViewById(R.id.title_leftbutton);
        Drawable backDrawable = getDrawable(R.drawable.left_arrow);
        back.setBackground(backDrawable);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //点击设置
        final Button editWnoise = findViewById(R.id.title_rightbutton);
        //editWnoise.setText("编辑");
        final Drawable settingDrawable = getDrawable(R.drawable.spanner);
        final Drawable settingDone = getDrawable(R.drawable.tick);
        editWnoise.setBackground(settingDrawable);
        editWnoise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mAdapter.GetIsNormal()){
                    //editWnoise.setText("ok");
                    editWnoise.setBackground(settingDone);
                    mAdapter.ChangeDrage();
                    Toast.makeText(WNoiseActivity.this,"进入编辑模式",Toast.LENGTH_SHORT).show();
                }
                else {
                    //editWnoise.setText("编辑");
                    editWnoise.setBackground(settingDrawable);
                    mAdapter.ChangeDrage();
                    Toast.makeText(WNoiseActivity.this,"编辑模式完成",Toast.LENGTH_SHORT).show();
                }
            }
        });

        //第一次启动的时候随机保存一个场
    }

    public void showWNoiseSceneSave(View view){
        WNoiseSceneSave wnoiseSceneSave = new WNoiseSceneSave();
        wnoiseSceneSave.show(getFragmentManager());
    }

    private int index;
    private long loadPresetId;
    private String hahaname;

    public void showWNoiseSceneLoad2(View view) {
        WNoiseSceneLoad2 wNoiseSceneLoad2 = new WNoiseSceneLoad2();
        final List<WNoisePreset> wNoisePresetList = Instance.s_WNoiseController.GetPresetList();

        /*
        List<String> haha = new ArrayList<>();
        for(int i = 0; i < wNoisePresetList.size(); i++){
            haha.add(wNoisePresetList.get(i).m_Name);
        }
        int n = wNoisePresetList.size();
        String[] haha_array = new String[n];
        for(int i = 0; i < wNoisePresetList.size(); i++){
            haha_array[i] = haha.get(i);
        }
        */

        wNoiseSceneLoad2.show("Hi,你好", wNoisePresetList, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                index = which;
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(WNoiseActivity.this, "选择了第" + (index + 1) + "项", Toast.LENGTH_SHORT).show();
                hahaname = wNoisePresetList.get(index).m_Name;
                Toast.makeText(WNoiseActivity.this, "Load Scene " + hahaname, Toast.LENGTH_SHORT).show();
                Instance.s_WNoiseController.LoadPreset(wNoisePresetList.get(index).m_ID);
                mAdapter.Set(Instance.s_WNoiseController.GetWNoiseList());
                TextView textViewScene = (TextView) findViewById(R.id.title_middletext);
                textViewScene.setText(hahaname);
            }
        }, getFragmentManager());
    }

    @Override
    public void onClickSceneSave(String sceneName, Boolean isSave){
        if(isSave){
            Toast.makeText(WNoiseActivity.this, "scene is " + sceneName,Toast.LENGTH_SHORT).show();
            Instance.s_WNoiseController.SaveAsNewPreset(sceneName);
            mTextViewScene.setText(Instance.s_WNoiseController.GetCurrentPreset().m_Name);
        }
        else {
            Toast.makeText(WNoiseActivity.this, "Canceled",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClickSceneLoad(WNoisePreset wNoisePreset, Boolean isLoad){
        if(isLoad){
            Map<Long, Integer> wNoiseMap = wNoisePreset.GetWNoiseVolumeMap();
            for(Map.Entry<Long, Integer> item : wNoiseMap.entrySet())
                Instance.s_WNoiseController.SetWNoiseVolume(item.getKey(),item.getValue());
            List<WNoise> wNoiseList=Instance.s_WNoiseController.GetWNoiseList();
            mAdapter.Set(wNoiseList);
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
            //mAdapter.notifyDataSetChanged();

        switch (resultCode){
            case WNoiseAdd.s_add:
                //mAdapter.notifyDataSetChanged();
                mAdapter.AddWNoise();
                break;
            case WNoiseSceneActivity.s_LoadPresetCancelCode:
                Instance.s_WNoiseController.TryPresetRecover();
                break;
            case WNoiseSceneActivity.s_LoadPresetResultCode:
                mAdapter.notifyDataSetChanged();
                mTextViewScene.setText(Instance.s_WNoiseController.GetCurrentPreset().m_Name);
                break;
        }
    }


}
