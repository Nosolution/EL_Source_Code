package com.example.administrator.el_done1;

/**
 * Created by Administrator on 2018-5-23.
 */

public class DeskPetMoli extends DeskPet {
    private String name = "DeskPetMoli";

    private static int[] imageIdResources=
                    {R.mipmap.moli1,
                    R.mipmap.moli2,
                    R.mipmap.moli3,
                    R.mipmap.moli4,
                    R.mipmap.moli5,
                    R.mipmap.moli6,
                    R.mipmap.moli7,
                    R.mipmap.moli8,
                    R.mipmap.moli9,
                    R.mipmap.moli10
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
