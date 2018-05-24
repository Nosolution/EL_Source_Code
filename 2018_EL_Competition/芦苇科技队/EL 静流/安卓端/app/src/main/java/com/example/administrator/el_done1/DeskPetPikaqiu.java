package com.example.administrator.el_done1;

/**
 * Created by Administrator on 2018-5-23.
 */

public class DeskPetPikaqiu extends DeskPet {
    private String name = "DeskPetPikaqiu";

    private static int[] imageIdResources=
            {R.mipmap.pikaqiu1,
                    R.mipmap.pikaqiu2,
                    R.mipmap.pikaqiu3,
                    R.mipmap.pikaqiu4,
                    R.mipmap.pikaqiu5,
                    R.mipmap.pikaqiu6,
                    R.mipmap.pikaqiu7,
                    R.mipmap.pikaqiu8,
                    R.mipmap.pikaqiu9,
                    R.mipmap.pikaqiu10
            };
    public static int getImageIdOf(int currentImageNum){
        if (currentImageNum>=6){
            currentImageNum-=5;
        }
        else{
            currentImageNum=0;
        }
        return imageIdResources[currentImageNum];
    }
}
