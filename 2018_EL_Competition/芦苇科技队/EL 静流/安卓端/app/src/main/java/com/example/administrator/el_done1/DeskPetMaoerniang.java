package com.example.administrator.el_done1;

/**
 * Created by Administrator on 2018-5-23.
 */

public class DeskPetMaoerniang extends DeskPet {
    private String name = "DeskPetMaoerniang";

    private static int[] imageIdResources=
            {R.mipmap.maoerniang1,
                    R.mipmap.maoerniang2,
                    R.mipmap.maoerniang3,
                    R.mipmap.maoerniang4,
                    R.mipmap.maoerniang5,
                    R.mipmap.maoerniang6,
                    R.mipmap.maoerniang7,
                    R.mipmap.maoerniang8,
                    R.mipmap.maoerniang9,
                    R.mipmap.maoerniang10
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
