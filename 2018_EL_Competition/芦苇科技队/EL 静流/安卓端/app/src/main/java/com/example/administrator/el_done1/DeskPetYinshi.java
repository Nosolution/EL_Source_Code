package com.example.administrator.el_done1;

/**
 * Created by Administrator on 2018-5-23.
 */

public class DeskPetYinshi extends DeskPet {
    private String name = "DeskPetYinshi";

    private static int[] imageIdResources=
            {R.mipmap.yinshi1,
                    R.mipmap.yinshi2,
                    R.mipmap.yinshi3,
                    R.mipmap.yinshi4,
                    R.mipmap.yinshi5,
                    R.mipmap.yinshi6,
                    R.mipmap.yinshi7,
                    R.mipmap.yinshi8,
                    R.mipmap.yinshi9,
                    R.mipmap.yinshi10
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
