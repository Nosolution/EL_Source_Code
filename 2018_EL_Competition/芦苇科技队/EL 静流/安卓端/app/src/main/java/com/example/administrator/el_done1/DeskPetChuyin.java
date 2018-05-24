package com.example.administrator.el_done1;

/**
 * Created by Administrator on 2018-5-23.
 */

public class DeskPetChuyin extends DeskPet {
    private String name = "DeskPetChuyin";

    private static int[] imageIdResources=
            {R.mipmap.chuyin1,
                    R.mipmap.chuyin2,
                    R.mipmap.chuyin3,
                    R.mipmap.chuyin4,
                    R.mipmap.chuyin5,
                    R.mipmap.chuyin6,
                    R.mipmap.chuyin7,
                    R.mipmap.chuyin8,
                    R.mipmap.chuyin9,
                    R.mipmap.chuyin10
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
