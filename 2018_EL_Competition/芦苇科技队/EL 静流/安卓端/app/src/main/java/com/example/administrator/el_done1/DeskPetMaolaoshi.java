package com.example.administrator.el_done1;

/**
 * Created by Administrator on 2018-5-23.
 */

public class DeskPetMaolaoshi extends DeskPet {
    private String name = "DeskPetMaolaoshi";

    private static int[] imageIdResources=
            {R.mipmap.maolaoshi1,
                    R.mipmap.maolaoshi2,
                    R.mipmap.maolaoshi3,
                    R.mipmap.maolaoshi4,
                    R.mipmap.maolaoshi5,
                    R.mipmap.maolaoshi6,
                    R.mipmap.maolaoshi7,
                    R.mipmap.maolaoshi8,
                    R.mipmap.maolaoshi9,
                    R.mipmap.maolaoshi10,
                    R.mipmap.maolaoshi11,
                    R.mipmap.maolaoshi12
            };


    public static int getImageIdOf(int currentImageNum){
        if (currentImageNum>=6){
            currentImageNum-=3;
        }
        else{
            currentImageNum=0;
        }
        return imageIdResources[currentImageNum];
    }
}
