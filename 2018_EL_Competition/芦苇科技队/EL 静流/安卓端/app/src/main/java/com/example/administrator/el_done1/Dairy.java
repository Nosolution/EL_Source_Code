package com.example.administrator.el_done1;

/**
 * Created by Administrator on 2018-5-21.
 */

public class Dairy {
    private String name;
    private int dairyFaceId;
    private boolean atLeft;   //想用这个实现布局里一左一右的脚印图片

    public Dairy(String name, int friendFaceId){
        this.name = name;
        this.atLeft = Integer.parseInt(name.substring(1,name.length()))%2==0;
        this.dairyFaceId = friendFaceId;
/*        if (Integer.parseInt(name.substring(1,name.length()))%2==0){
            this.dairyFaceId = R.id.dairy_face_left;
        }
        else this.dairyFaceId = R.id.dairy_face_right;
    */
    }
    public String getName(){
        return name;
    }

    public int getDairyFaceId(){
        return dairyFaceId;
    }

    public boolean isAtLeft(){
        return this.atLeft;
    }
}
