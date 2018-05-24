package com.example.administrator.el_done1;

/**
 * Created by Administrator on 2018-5-23.
 */

public class DeskPetMaoerkunan extends DeskPet {
    private String name = "DeskPetMaoerkunan";

    private static int[] imageIdResources=
            {R.mipmap.maoerkunan1,
                    R.mipmap.maoerkunan2,
                    R.mipmap.maoerkunan3,
                    R.mipmap.maoerkunan4,
                    R.mipmap.maoerkunan5,
                    R.mipmap.maoerkunan6,
                    R.mipmap.maoerkunan7,
                    R.mipmap.maoerkunan8,
                    R.mipmap.maoerkunan9,
                    R.mipmap.maoerkunan10
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
