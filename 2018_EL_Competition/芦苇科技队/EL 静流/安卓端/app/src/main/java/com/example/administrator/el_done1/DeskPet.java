package com.example.administrator.el_done1;

import com.example.administrator.el_done1.R;

/**
 * Created by Administrator on 2018-5-23.
 */

public class DeskPet {
    private static int[] imageIdResources=
            {R.drawable.bg1,
            R.drawable.bg2};
    private String name;



    public String getName(){
        return this.name;
    }

    public static int getImageIdOf(int currentImageNum){
        if (currentImageNum>=6){
            currentImageNum-=5;
        }
        return imageIdResources[currentImageNum];
    }

}
