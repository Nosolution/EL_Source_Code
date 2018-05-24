package com.example.a81271.timato;
//This project source is created by Zhaoheng Yin.
//Copyright 2018 Zhaoheng Yin.
//Catbro group Confidential.

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by 81271 on 2018/5/23.
 */

public class TimeoutFragment extends Fragment {

    PictureView Back;
    Button Start;
    Button  U1;
    Button  U2;
    Button  U3;
    Button  U4;
    Button  U5;
    Button  U6;
    Button  D1;
    Button  D2;
    Button  D3;
    Button  D4;
    Button  D5;
    Button  D6;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.timeout_fragment, container, false);
        return view;

    }

    public void onActivityCreated(Bundle SavedInstance)
    {
        super.onActivityCreated(SavedInstance);
        Back = (PictureView)getActivity().findViewById(R.id.pic);
        U1 = (Button)getActivity().findViewById(R.id.u1);
        U1.getBackground().setAlpha(0);
        U1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Back.switchh1(0);
            }
        });
        U2 = (Button)getActivity().findViewById(R.id.u2);
        U2.getBackground().setAlpha(0);
        U2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Back.switchh2(0);
            }
        });
        U3 = (Button)getActivity().findViewById(R.id.u3);
        U3.getBackground().setAlpha(0);
        U3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Back.switchm1(0);
            }
        });
        U4 = (Button)getActivity().findViewById(R.id.u4);
        U4.getBackground().setAlpha(0);
        U4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Back.switchm2(0);
            }
        });
        U5 = (Button)getActivity().findViewById(R.id.u5);
        U5.getBackground().setAlpha(0);
        U5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Back.switchs1(0);
            }
        });
        U6 = (Button)getActivity().findViewById(R.id.u6);
        U6.getBackground().setAlpha(0);
        U6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Back.switchs2(0);
            }
        });
        D1 = (Button)getActivity().findViewById(R.id.d1);
        D1.getBackground().setAlpha(0);
        D1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Back.switchh1(1);
            }
        });
        D2 = (Button)getActivity().findViewById(R.id.d2);
        D2.getBackground().setAlpha(0);
        D2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Back.switchh2(1);
            }
        });
        D3 = (Button)getActivity().findViewById(R.id.d3);
        D3.getBackground().setAlpha(0);
        D3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Back.switchm1(1);
            }
        });
        D4 = (Button)getActivity().findViewById(R.id.d4);
        D4.getBackground().setAlpha(0);
        D4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Back.switchm2(1);
            }
        });
        D5 = (Button)getActivity().findViewById(R.id.d5);
        D5.getBackground().setAlpha(0);
        D5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Back.switchs1(1);
            }
        });
        D6 = (Button)getActivity().findViewById(R.id.d6);
        D6.getBackground().setAlpha(0);
        D6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Back.switchs2(1);
            }
        });


    }

}