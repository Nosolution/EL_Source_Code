package com.easylife.fragment;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.easylife.activity.FocusTimerActivity;
import com.easylife.activity.R;
import com.easylife.util.LoadingSettingsManager;
import com.easylife.util.Pickers;
import com.easylife.view.PickerScrollView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class FocusFragment extends Fragment {
    private PickerScrollView pickerscrolllview1; // 滚动选择器
    private PickerScrollView pickerscrolllview2;
    private List<Pickers> list; // 滚动选择器数据
    private String[] id;
    private String[] name;
    private LinearLayout picker_rel; // 选择器布局

    private long millisInFuture1 = 0;
    private long millisInFuture2 = 0;//60s

    private LinearLayout linearLayout;

    public static String leftbackground;
    public static int flag = 0;
    private String focus_mode = "null";
    private String[] focusSentence = new String[]{
            "不管未来，不管过去，不管周边，专注做好当下的事!",
            "保持安静、耐心和专注，那么幸福就在你身边!",
            "世界上没有奇迹，只有专注和聚焦的力量。",
            "你频回首，哪里顾得了前面的路; 你不专注，又如何把这路走得远。",
            "但求日积月累，收获于细微；不要左顾右盼，专注于自我。",
            "那些专注的人，他们眼睛里闪耀着光芒，闪耀着对未来的渴望!",
            "过路的风景与我无关，我只专注脚下的路。",
            "失败的最大原因是破碎的专注力。"
    };
    private boolean isSentence = false;
    private boolean isReadingSelected = false;
    private boolean isWorkSelected = false;

    private Button button1;
    private Button button2;
    private Button startFocus;
    private ImageView imageView;
    private TextView focusSentenceButton;


    List<String> Images = null;
    String[] files = null;
    static AssetManager assets = null;
    InputStream is = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.focusmain_layout, container, false);
        imageView = view.findViewById(R.id.background);

        if (flag == 0) {
            try {
                assets = getActivity().getAssets();         /*获取assets目录*/
                files = assets.list("backgroundleft_pic");     /*返回给定路径中所有资源的字符串数组*/
                Images = new ArrayList<>();
                for (String s : files) {
                    Images.add("backgroundleft_pic/" + s);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            int a = (int) (Math.random() * 7);
            String path = Images.get(a);
            leftbackground = path;
            flag = 1;
        }
        try {
            is = assets.open(leftbackground);
        } catch (IOException e) {
            e.printStackTrace();
        }


        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inSampleSize = 10;
        imageView.setImageBitmap(BitmapFactory.decodeStream(is));
        linearLayout = view.findViewById(R.id.timer);


        //阅读按钮
        button1 = view.findViewById(R.id.button1);
        button1.setOnClickListener(v -> {
            focus_mode = "read";
            if (isReadingSelected) {
                button1.setBackground(getResources().getDrawable(R.drawable.focus_unselected));
            } else {
                isWorkSelected = false;
                button1.setBackground(getResources().getDrawable(R.drawable.focus_selected));
            }
            isReadingSelected = !isReadingSelected;
            button2.setBackground(getResources().getDrawable(R.drawable.focus_unselected));
            if (isReadingSelected || isWorkSelected) {
                startFocus.setVisibility(View.VISIBLE);
                picker_rel.setVisibility(View.VISIBLE);
            } else {
                startFocus.setVisibility(View.GONE);
                picker_rel.setVisibility(View.GONE);
            }

        });

        //工作按钮
        button2 = view.findViewById(R.id.button2);
        button2.setOnClickListener(v -> {
            focus_mode = "work";
            if (isWorkSelected) {
                button2.setBackground(getResources().getDrawable(R.drawable.focus_unselected));
            } else {
                isReadingSelected = false;
                button2.setBackground(getResources().getDrawable(R.drawable.focus_selected));
            }
            isWorkSelected = !isWorkSelected;
            button1.setBackground(getResources().getDrawable(R.drawable.focus_unselected));
            if (isReadingSelected || isWorkSelected) {
                startFocus.setVisibility(View.VISIBLE);
                picker_rel.setVisibility(View.VISIBLE);
            } else {
                startFocus.setVisibility(View.GONE);
                picker_rel.setVisibility(View.GONE);
            }
        });

        startFocus = view.findViewById(R.id.start_focus_button);
        startFocus.setOnClickListener(v -> {
            LoadingSettingsManager manager = new LoadingSettingsManager(getContext());
            int focusTime = Integer.parseInt(manager.getFocusTimeValue());
            if (millisInFuture1 * 60 + millisInFuture2 * 5 < focusTime) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("专注的时间少于" + focusTime + "分钟效率比较低哦!");
                builder.setPositiveButton("了解", (dialog, which) -> {

                });
                builder.show();
                return;
            }
            Intent intent = new Intent(getActivity(), FocusTimerActivity.class);
            intent.putExtra("mode", focus_mode);
            intent.putExtra("time1", millisInFuture1);
            intent.putExtra("time2", millisInFuture2 * 5);
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity(), linearLayout, "shared").toBundle());
//            getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            getActivity().finish();
        });

        focusSentenceButton = view.findViewById(R.id.focus_sentence_textview);
        focusSentenceButton.setOnClickListener(v -> {
            if (isSentence) {
                focusSentenceButton.setText("专注");
                focusSentenceButton.setTextSize(36);
            } else {
                focusSentenceButton.setText(focusSentence[(int) (Math.random() * focusSentence.length)]);
                focusSentenceButton.setTextSize(18);
            }
            isSentence = !isSentence;
        });

        initView(view);
        initLinstener();
        initData1();
        initData2();


        return view;
    }

//    @Override
//    public void onPause() {
//        super.onPause();
//        Toast.makeText(getContext(), "adsfasdf", Toast.LENGTH_LONG).show();
//    }
//    @Override
//    public void onResume() {
//        super.onResume();
//        BitmapFactory.Options opts = new BitmapFactory.Options();
//        opts.inSampleSize = 3;
//        imageView.setImageBitmap(BitmapFactory.decodeStream(is));
//
//    }

    private void initView(View view) {
        picker_rel = view.findViewById(R.id.picker_rel);
        pickerscrolllview1 = view.findViewById(R.id.pickerscrolllview1);
        pickerscrolllview2 = view.findViewById(R.id.pickerscrolllview2);
    }

    /**
     * 设置监听事件
     */
    private void initLinstener() {
        pickerscrolllview1.setOnSelectListener(pickerListener1);
        pickerscrolllview2.setOnSelectListener(pickerListener2);
    }

    /**
     * 初始化数据
     */
    private void initData1() {
        list = new ArrayList<Pickers>();
        id = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
        name = new String[]{"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
        for (int i = 0; i < name.length; i++) {
            list.add(new Pickers(name[i], id[i]));
        }
        // 设置数据，默认选择第一条
        pickerscrolllview1.setData(list);
        pickerscrolllview1.setSelected(0);

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();   //获取屏幕宽度
        int widthPixels = displayMetrics.widthPixels;

        pickerscrolllview1.setMyWidth(widthPixels / 5 * 4 - 80);
    }

    // 滚动选择器选中事件
    PickerScrollView.onSelectListener pickerListener1 = pickers -> millisInFuture1 = Long.parseLong(pickers.getShowId()) * 1000 * 3600;

    private void initData2() {
        list = new ArrayList<>();
//        id = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59"};
//        name = new String[]{"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59"};
        id = new String[12];
        name = new String[12];
        for (int i = 0; i < 12; i++) {
            id[i] = i + "";
            name[i] = i * 5 < 10 ? "0" + i * 5 : "" + i * 5;
        }
        for (int i = 0; i < name.length; i++) {
            list.add(new Pickers(name[i], id[i]));
        }
        // 设置数据，默认选择第一条
        pickerscrolllview2.setData(list);
        pickerscrolllview2.setSelected(0);

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();   //获取屏幕宽度
        int widthPixels = displayMetrics.widthPixels;

        pickerscrolllview2.setMyWidth(80);
    }

    // 滚动选择器选中事件
    PickerScrollView.onSelectListener pickerListener2 = pickers -> millisInFuture2 = Long.parseLong(pickers.getShowId()) * 1000 * 60;
}
