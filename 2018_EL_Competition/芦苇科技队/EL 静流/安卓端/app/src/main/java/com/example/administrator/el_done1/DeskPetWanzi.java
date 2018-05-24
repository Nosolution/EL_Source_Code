package com.example.administrator.el_done1;

/**
 * Created by Administrator on 2018-5-23.
 */

public class DeskPetWanzi extends DeskPet{
    private static int[] imageIdResources=
                    {R.mipmap.wanzi1,
                    R.mipmap.wanzi2,
                    R.mipmap.wanzi3,
                    R.mipmap.wanzi4,
                    R.mipmap.wanzi5,
                    R.mipmap.wanzi6,
                    R.mipmap.wanzi7,
                    R.mipmap.wanzi8,
                    R.mipmap.wanzi9,
                    R.mipmap.wanzi10
                    };

    private static String name = "DeskPetWanzi";
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
