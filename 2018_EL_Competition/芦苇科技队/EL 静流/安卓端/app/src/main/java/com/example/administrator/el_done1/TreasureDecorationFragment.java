package com.example.administrator.el_done1;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018-5-14.
 */

public class TreasureDecorationFragment extends Fragment implements View.OnClickListener{
//    RelativeLayout relativeLayout = (RelativeLayout) getActivity().findViewById(R.id.centerLayout);
    private List<TreasureDecorationView> DecorationButtonList = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.treasure_decoration_fragment,container,false);

        //根据主题设置背景颜色
        switch (Theme.getTHEME()){
            case "SIMPLE":
                view.setBackgroundColor(Color.parseColor("#ffffff"));
                break;
            case "OTAKU":
                view.setBackgroundColor(Color.parseColor("#fabdf0"));
                break;
            case "PET":
                view.setBackgroundColor(Color.parseColor("#ffd9c1"));
                break;
        }

        return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        initBGButtonList();
        for (TreasureDecorationView button:DecorationButtonList) {
            button.setOnClickListener(this);
        }
    }
    @Override
    public void onClick(View v){
        TreasureDecorationView b = (TreasureDecorationView)v;
        switch (b.getName()){
            case "DecorationButton00":
                Toast.makeText(getActivity(),b.getName(),
                        Toast.LENGTH_SHORT).show();
                ImageView imageView_0_0 = new ImageView (getActivity());
                imageView_0_0.setImageResource(R.drawable.d6);
                RelativeLayout.LayoutParams params_0_0 = new RelativeLayout.LayoutParams(100,100);
                params_0_0.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                params_0_0.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                imageView_0_0.setLayoutParams(params_0_0);
                ((RelativeLayout)getActivity().findViewById(R.id.centerLayout)).addView(imageView_0_0);
                break;
            case "DecorationButton01":
                Toast.makeText(getActivity(),b.getName(),
                        Toast.LENGTH_SHORT).show();
                ImageView imageView_0_1 = new ImageView (getActivity());
                imageView_0_1.setImageResource(R.drawable.d5);
                RelativeLayout.LayoutParams params_0_1 = new RelativeLayout.LayoutParams(100,100);
                params_0_1.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                params_0_1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                imageView_0_1.setLayoutParams(params_0_1);
                ((RelativeLayout)getActivity().findViewById(R.id.centerLayout)).addView(imageView_0_1);
                break;
            case "DecorationButton10":
                Toast.makeText(getActivity(),b.getName(),
                        Toast.LENGTH_SHORT).show();
                ImageView imageView_1_0 = new ImageView (getActivity());
                imageView_1_0.setImageResource(R.drawable.d4);
                RelativeLayout.LayoutParams params_1_0 = new RelativeLayout.LayoutParams(100,100);
                params_1_0.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                params_1_0.addRule(RelativeLayout.CENTER_VERTICAL);
                imageView_1_0.setLayoutParams(params_1_0);
                ((RelativeLayout)getActivity().findViewById(R.id.centerLayout)).addView(imageView_1_0);
                break;
            case "DecorationButton11":
                Toast.makeText(getActivity(),b.getName(),
                        Toast.LENGTH_SHORT).show();
                ImageView imageView_1_1 = new ImageView (getActivity());
                imageView_1_1.setImageResource(R.drawable.d3);
                RelativeLayout.LayoutParams params_1_1 = new RelativeLayout.LayoutParams(100,100);
                params_1_1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                params_1_1.addRule(RelativeLayout.CENTER_VERTICAL);
                imageView_1_1.setLayoutParams(params_1_1);
                ((RelativeLayout)getActivity().findViewById(R.id.centerLayout)).addView(imageView_1_1);
                break;
            case "DecorationButton20":
                Toast.makeText(getActivity(),b.getName(),
                        Toast.LENGTH_SHORT).show();
                ImageView imageView_2_0 = new ImageView (getActivity());
                imageView_2_0.setImageResource(R.drawable.d2);
                RelativeLayout.LayoutParams params_2_0 = new RelativeLayout.LayoutParams(100,100);
                params_2_0.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                params_2_0.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                imageView_2_0.setLayoutParams(params_2_0);
                ((RelativeLayout)getActivity().findViewById(R.id.centerLayout)).addView(imageView_2_0);
                break;
            case "DecorationButton21":
                Toast.makeText(getActivity(),b.getName(),
                        Toast.LENGTH_SHORT).show();
                ImageView imageView_2_1 = new ImageView (getActivity());
                imageView_2_1.setImageResource(R.drawable.d1);
                RelativeLayout.LayoutParams params_2_1 = new RelativeLayout.LayoutParams(100,100);
                params_2_1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                params_2_1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                imageView_2_1.setLayoutParams(params_2_1);
                ((RelativeLayout)getActivity().findViewById(R.id.centerLayout)).addView(imageView_2_1);
                break;
            default:
                break;
        }
    }
    private void initBGButtonList(){
        TreasureDecorationView decorationButton00 = (TreasureDecorationView) getActivity().findViewById(R.id.treasure_decoration_0_0);
        decorationButton00.setName("DecorationButton00");
        TreasureDecorationView decorationButton01 = (TreasureDecorationView) getActivity().findViewById(R.id.treasure_decoration_0_1);
        decorationButton01.setName("DecorationButton01");
        TreasureDecorationView decorationButton10 = (TreasureDecorationView) getActivity().findViewById(R.id.treasure_decoration_1_0);
        decorationButton10.setName("DecorationButton10");
        TreasureDecorationView decorationButton11 = (TreasureDecorationView) getActivity().findViewById(R.id.treasure_decoration_1_1);
        decorationButton11.setName("DecorationButton11");
        TreasureDecorationView decorationButton20 = (TreasureDecorationView) getActivity().findViewById(R.id.treasure_decoration_2_0);
        decorationButton20.setName("DecorationButton20");
        TreasureDecorationView decorationButton21 = (TreasureDecorationView) getActivity().findViewById(R.id.treasure_decoration_2_1);
        decorationButton21.setName("DecorationButton21");
        DecorationButtonList.add(decorationButton00);
        DecorationButtonList.add(decorationButton01);
        DecorationButtonList.add(decorationButton10);
        DecorationButtonList.add(decorationButton11);
        DecorationButtonList.add(decorationButton20);
        DecorationButtonList.add(decorationButton21);
    }
}
