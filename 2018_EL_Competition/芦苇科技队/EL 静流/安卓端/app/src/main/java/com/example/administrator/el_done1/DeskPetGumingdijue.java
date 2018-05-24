package com.example.administrator.el_done1;

/**
 * Created by Administrator on 2018-5-23.
 */

public class DeskPetGumingdijue extends DeskPet {
    private String name = "DeskPetGumingdijue";

    private static int[] imageIdResources=
            {R.mipmap.gumingdijue1,
                    R.mipmap.gumingdijue2,
                    R.mipmap.gumingdijue3,
                    R.mipmap.gumingdijue4,
                    R.mipmap.gumingdijue5,
                    R.mipmap.gumingdijue6,
                    R.mipmap.gumingdijue7,
                    R.mipmap.gumingdijue8,
                    R.mipmap.gumingdijue9,
                    R.mipmap.gumingdijue10
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
