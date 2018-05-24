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

public class TreasureDeskPetFragment extends Fragment implements View.OnClickListener{
    private List<TreasureDeskPetView> PetButtonList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view;

        //根据主题设置背景颜色和桌宠的选择碎片
        RelativeLayout treasure_case_layout = (RelativeLayout)getActivity().findViewById(R.id.treasure_case);
        switch (Theme.getTHEME()){
            case "SIMPLE":
                treasure_case_layout.setBackgroundColor(Color.parseColor("#ffffff"));
                view = inflater.inflate(R.layout.treasure_desk_pet_fragment_simple,container,false);
                break;
            case "OTAKU":
                treasure_case_layout.setBackgroundColor(Color.parseColor("#fabdf0"));
                view = inflater.inflate(R.layout.treasure_desk_pet_fragment_otaku,container,false);
                break;
            case "PET":
                treasure_case_layout.setBackgroundColor(Color.parseColor("#ffd9c1"));
                view = inflater.inflate(R.layout.treasure_desk_pet_fragment_cute_pet,container,false);
                break;
            default:
                treasure_case_layout.setBackgroundColor(Color.parseColor("#ffd9c1"));
                view = inflater.inflate(R.layout.treasure_desk_pet_fragment_simple,container,false);
                break;
        }

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        initBGButtonList();
        for (TreasureDeskPetView button:PetButtonList) {
            button.setOnClickListener(this);
        }
    }


    //里面删除了其他simple桌宠的按钮
    @Override
    public void onClick(View v){
        TreasureDeskPetView b = (TreasureDeskPetView)v;
        switch (b.getName()){
            case "丸子":
                Toast.makeText(getActivity(),b.getName(),
                        Toast.LENGTH_SHORT).show();
                /*ImageView imagePetView_0_0 = new ImageView (getActivity());
                imagePetView_0_0.setImageResource(R.drawable.p1);
                RelativeLayout.LayoutParams params_0_0 = new RelativeLayout.LayoutParams(100,100);
                params_0_0.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                params_0_0.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                imagePetView_0_0.setLayoutParams(params_0_0);
                ((RelativeLayout)getActivity().findViewById(R.id.centerLayout)).addView(imageView_0_0);
                */
                ((ImageView)getActivity().findViewById(R.id.deskPetView)).
                        setImageResource(R.mipmap.wanzi1);
                MainActivity.setDeskPet("DeskPetWanzi");
                break;
            case "小樱茉莉":
                Toast.makeText(getActivity(),b.getName(),
                        Toast.LENGTH_SHORT).show();
                ((ImageView)getActivity().findViewById(R.id.deskPetView)).
                        setImageResource(R.mipmap.moli1);
                MainActivity.setDeskPet("DeskPetMoli");
                break;
            case "初音未来":
                Toast.makeText(getActivity(),b.getName(),
                        Toast.LENGTH_SHORT).show();
                ((ImageView)getActivity().findViewById(R.id.deskPetView)).
                        setImageResource(R.mipmap.chuyin1);
                MainActivity.setDeskPet("DeskPetChuyin");
                break;
            case "古明地觉":
                Toast.makeText(getActivity(),b.getName(),
                        Toast.LENGTH_SHORT).show();
                ((ImageView)getActivity().findViewById(R.id.deskPetView)).
                        setImageResource(R.mipmap.gumingdijue1);
                MainActivity.setDeskPet("DeskPetGumingdijue");
                break;
            case "猫儿萌娘":
                Toast.makeText(getActivity(),b.getName(),
                        Toast.LENGTH_SHORT).show();
                ((ImageView)getActivity().findViewById(R.id.deskPetView)).
                        setImageResource(R.mipmap.maoerniang1);
                MainActivity.setDeskPet("DeskPetMaoerniang");
                break;
            case "猫耳酷男":
                Toast.makeText(getActivity(),b.getName(),
                        Toast.LENGTH_SHORT).show();
                ((ImageView)getActivity().findViewById(R.id.deskPetView)).
                        setImageResource(R.mipmap.maoerkunan1);
                MainActivity.setDeskPet("DeskPetMaoerkunan");
                break;
            case "坂田银时":
                Toast.makeText(getActivity(),b.getName(),
                        Toast.LENGTH_SHORT).show();
                ((ImageView)getActivity().findViewById(R.id.deskPetView)).
                        setImageResource(R.mipmap.yinshi1);
                MainActivity.setDeskPet("DeskPetYinshi");
                break;
            case "猫老师":
                Toast.makeText(getActivity(),b.getName(),
                        Toast.LENGTH_SHORT).show();
                ((ImageView)getActivity().findViewById(R.id.deskPetView)).
                        setImageResource(R.mipmap.maolaoshi1);
                MainActivity.setDeskPet("DeskPetMaolaoshi");
                break;
            case "皮卡丘":
                Toast.makeText(getActivity(),b.getName(),
                        Toast.LENGTH_SHORT).show();
                ((ImageView)getActivity().findViewById(R.id.deskPetView)).
                        setImageResource(R.mipmap.pikaqiu1);
                MainActivity.setDeskPet("DeskPetPikaqiu");
                break;
            default:
                break;
        }
    }


    //删了其他桌宠的按钮
    private void initBGButtonList(){
        TreasureDeskPetView petButton00;
        TreasureDeskPetView petButton01;
        TreasureDeskPetView petButton10;
        TreasureDeskPetView petButton11;
        TreasureDeskPetView petButton20;
        TreasureDeskPetView petButton21;

        switch (Theme.getTHEME()){
            case "SIMPLE":
                petButton00 = (TreasureDeskPetView) getActivity().findViewById(R.id.treasure_desk_pet_simple_0_0);
                petButton00.setName("丸子");
/*              TreasureDeskPetView petButton01 = (TreasureDeskPetView) getActivity().findViewById(R.id.treasure_desk_pet_0_1);
                petButton01.setName("petButton01");
                TreasureDeskPetView petButton10 = (TreasureDeskPetView) getActivity().findViewById(R.id.treasure_desk_pet_1_0);
                petButton10.setName("petButton10");
                TreasureDeskPetView petButton11 = (TreasureDeskPetView) getActivity().findViewById(R.id.treasure_desk_pet_1_1);
                petButton11.setName("petButton11");
                TreasureDeskPetView petButton20 = (TreasureDeskPetView) getActivity().findViewById(R.id.treasure_desk_pet_2_0);
                petButton20.setName("petButton20");
                TreasureDeskPetView petButton21 = (TreasureDeskPetView) getActivity().findViewById(R.id.treasure_desk_pet_2_1);
                petButton21.setName("petButton21");*/
                PetButtonList.add(petButton00);
/*              PetButtonList.add(petButton01);
                PetButtonList.add(petButton10);
                PetButtonList.add(petButton11);
                PetButtonList.add(petButton20);
                PetButtonList.add(petButton21);*/
                break;
            case "OTAKU":
                petButton00 = (TreasureDeskPetView) getActivity().findViewById(R.id.treasure_desk_pet_otaku_0_0);
                petButton00.setName("小樱茉莉");
                petButton01 = (TreasureDeskPetView) getActivity().findViewById(R.id.treasure_desk_pet_otaku_0_1);
                petButton01.setName("初音未来");
                petButton10 = (TreasureDeskPetView) getActivity().findViewById(R.id.treasure_desk_pet_otaku_1_0);
                petButton10.setName("古明地觉");
                petButton11 = (TreasureDeskPetView) getActivity().findViewById(R.id.treasure_desk_pet_otaku_1_1);
                petButton11.setName("猫儿萌娘");
                petButton20 = (TreasureDeskPetView) getActivity().findViewById(R.id.treasure_desk_pet_otaku_2_0);
                petButton20.setName("猫耳酷男");
                petButton21 = (TreasureDeskPetView) getActivity().findViewById(R.id.treasure_desk_pet_otaku_2_1);
                petButton21.setName("坂田银时");
                PetButtonList.add(petButton00);
                PetButtonList.add(petButton01);
                PetButtonList.add(petButton10);
                PetButtonList.add(petButton11);
                PetButtonList.add(petButton20);
                PetButtonList.add(petButton21);
                break;
            case "PET":
                petButton00 = (TreasureDeskPetView) getActivity().findViewById(R.id.treasure_desk_pet_cute_pet_0_0);
                petButton00.setName("猫老师");
                petButton01 = (TreasureDeskPetView) getActivity().findViewById(R.id.treasure_desk_pet_cute_pet_0_1);
                petButton01.setName("皮卡丘");
/*                petButton10 = (TreasureDeskPetView) getActivity().findViewById(R.id.treasure_desk_pet_otaku_1_0);
                petButton10.setName("petButton10");
                petButton11 = (TreasureDeskPetView) getActivity().findViewById(R.id.treasure_desk_pet_otaku_1_1);
                petButton11.setName("petButton11");
                petButton20 = (TreasureDeskPetView) getActivity().findViewById(R.id.treasure_desk_pet_otaku_2_0);
                petButton20.setName("petButton20");
                petButton21 = (TreasureDeskPetView) getActivity().findViewById(R.id.treasure_desk_pet_otaku_2_1);
                petButton21.setName("petButton21");*/
                PetButtonList.add(petButton00);
                PetButtonList.add(petButton01);
/*                PetButtonList.add(petButton10);
                PetButtonList.add(petButton11);
                PetButtonList.add(petButton20);
                PetButtonList.add(petButton21);*/
                break;
            default:
                break;
        }

    }
}
