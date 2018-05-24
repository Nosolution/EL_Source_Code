package com.example.administrator.el_done1;

import android.app.Activity;
import android.os.Bundle;



public class Main3Activity extends Activity {
    public static void times(){
        for_counting_times.times_done++;
    }



    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main3);

    }

}
