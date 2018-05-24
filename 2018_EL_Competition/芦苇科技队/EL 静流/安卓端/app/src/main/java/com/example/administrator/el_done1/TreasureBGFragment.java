package com.example.administrator.el_done1;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018-5-14.
 */

public class TreasureBGFragment extends Fragment implements View.OnClickListener{
//    MainActivity mainActivity = (MainActivity)getActivity();
//    RelativeLayout RelativeLayout = (RelativeLayout)mainActivity.findViewById(R.id.centerLayout);
    private List<TreasureBGView> BGButtonList = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.treasure_bg_fragment,container,false);

        //根据主题设置背景颜色
        RelativeLayout treasure_case_layout = (RelativeLayout)getActivity().findViewById(R.id.treasure_case);
        switch (Theme.getTHEME()){
            case "SIMPLE":
                treasure_case_layout.setBackgroundColor(Color.parseColor("#ffffff"));
                break;
            case "OTAKU":
                treasure_case_layout.setBackgroundColor(Color.parseColor("#fabdf0"));
                break;
            case "PET":
                treasure_case_layout.setBackgroundColor(Color.parseColor("#ffd9c1"));
                break;
        }
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        initBGButtonList();
        for (TreasureBGView button:BGButtonList) {
            button.setOnClickListener(this);
        }

    }
    @Override
    public void onClick(View v){
        TreasureBGView b = (TreasureBGView)v;
        switch (b.getName()){
            case "bgButton00":
                Toast.makeText(getActivity(),b.getName(),
                        Toast.LENGTH_SHORT).show();
                getActivity().findViewById(R.id.centerLayout).
                        setBackgroundResource(R.drawable.bg1);
                break;
            case "bgButton01":
                Toast.makeText(getActivity(),b.getName(),
                        Toast.LENGTH_SHORT).show();
                getActivity().findViewById(R.id.centerLayout).
                        setBackgroundResource(R.drawable.bg2);
                break;
            case "bgButton02":
                Toast.makeText(getActivity(),b.getName(),
                        Toast.LENGTH_SHORT).show();
                getActivity().findViewById(R.id.centerLayout).
                        setBackgroundResource(R.drawable.bg3);
                break;
            case "bgButton10":
                Toast.makeText(getActivity(),b.getName(),
                        Toast.LENGTH_SHORT).show();
                getActivity().findViewById(R.id.centerLayout).
                        setBackgroundResource(R.drawable.bg4);
                break;
            case "bgButton11":
                Toast.makeText(getActivity(),b.getName(),
                        Toast.LENGTH_SHORT).show();
                getActivity().findViewById(R.id.centerLayout).
                        setBackgroundResource(R.drawable.bg5);
                break;
            case "bgButton12":
                Toast.makeText(getActivity(),b.getName(),
                        Toast.LENGTH_SHORT).show();
                getActivity().findViewById(R.id.centerLayout).
                        setBackgroundResource(R.drawable.bg6);
                break;
            case "bgButton20":
                Toast.makeText(getActivity(),b.getName(),
                        Toast.LENGTH_SHORT).show();
                getActivity().findViewById(R.id.centerLayout).
                        setBackgroundResource(R.drawable.bg7);
                break;
            case "bgButton21":
                Toast.makeText(getActivity(),b.getName(),
                        Toast.LENGTH_SHORT).show();
                getActivity().findViewById(R.id.centerLayout).
                        setBackgroundResource(R.drawable.bg8);
                break;
            case "bgButton22":
                Toast.makeText(getActivity(),b.getName(),
                        Toast.LENGTH_SHORT).show();
                getActivity().findViewById(R.id.centerLayout).
                        setBackgroundResource(R.drawable.bg9);
                break;
            case "bgButton30":
                Toast.makeText(getActivity(),b.getName(),
                        Toast.LENGTH_SHORT).show();
                getActivity().findViewById(R.id.centerLayout).
                        setBackgroundResource(R.drawable.bg10);
                break;
            case "bgButton31":
                Toast.makeText(getActivity(),b.getName(),
                        Toast.LENGTH_SHORT).show();
                getActivity().findViewById(R.id.centerLayout).
                        setBackgroundResource(R.drawable.bg11);
                break;
            case "bgButton32":
                Toast.makeText(getActivity(),b.getName(),
                        Toast.LENGTH_SHORT).show();
                getActivity().findViewById(R.id.centerLayout).
                        setBackgroundResource(R.drawable.bg12);
                break;
            default:
                break;
        }
    }
    public void setBG(Button b){

    }
    public int getBGSrc(){
        return 0;
    }

    private void initBGButtonList(){
        TreasureBGView bgButton00 = (TreasureBGView) getActivity().findViewById(R.id.treasure_bg_0_0);
        bgButton00.setName("bgButton00");
        TreasureBGView bgButton01 = (TreasureBGView) getActivity().findViewById(R.id.treasure_bg_0_1);
        bgButton01.setName("bgButton01");
        TreasureBGView bgButton02 = (TreasureBGView) getActivity().findViewById(R.id.treasure_bg_0_2);
        bgButton02.setName("bgButton02");
        TreasureBGView bgButton10 = (TreasureBGView) getActivity().findViewById(R.id.treasure_bg_1_0);
        bgButton10.setName("bgButton10");
        TreasureBGView bgButton11 = (TreasureBGView) getActivity().findViewById(R.id.treasure_bg_1_1);
        bgButton11.setName("bgButton11");
        TreasureBGView bgButton12 = (TreasureBGView) getActivity().findViewById(R.id.treasure_bg_1_2);
        bgButton12.setName("bgButton12");
        TreasureBGView bgButton20 = (TreasureBGView) getActivity().findViewById(R.id.treasure_bg_2_0);
        bgButton20.setName("bgButton20");
        TreasureBGView bgButton21 = (TreasureBGView) getActivity().findViewById(R.id.treasure_bg_2_1);
        bgButton21.setName("bgButton21");
        TreasureBGView bgButton22 = (TreasureBGView) getActivity().findViewById(R.id.treasure_bg_2_2);
        bgButton22.setName("bgButton22");
        TreasureBGView bgButton30 = (TreasureBGView) getActivity().findViewById(R.id.treasure_bg_3_0);
        bgButton30.setName("bgButton30");
        TreasureBGView bgButton31 = (TreasureBGView) getActivity().findViewById(R.id.treasure_bg_3_1);
        bgButton31.setName("bgButton31");
        TreasureBGView bgButton32 = (TreasureBGView) getActivity().findViewById(R.id.treasure_bg_3_2);
        bgButton32.setName("bgButton32");
        BGButtonList.add(bgButton00);
        BGButtonList.add(bgButton01);
        BGButtonList.add(bgButton02);
        BGButtonList.add(bgButton10);
        BGButtonList.add(bgButton11);
        BGButtonList.add(bgButton12);
        BGButtonList.add(bgButton20);
        BGButtonList.add(bgButton21);
        BGButtonList.add(bgButton22);
        BGButtonList.add(bgButton30);
        BGButtonList.add(bgButton31);
        BGButtonList.add(bgButton32);
    }
}
