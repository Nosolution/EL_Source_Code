package com.example.administrator.el_done1;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 2018-5-23.
 */

public class UserInformation {
    private static Bitmap user_face_bitmap;
    private static String sex;
    private static String profession;
    private static String email;
    public static String user_name="admin";
    public static int score=getScore();
    public static String motto="";
    public static void setFaceBitmap(Bitmap b){
        user_face_bitmap = b;
    }
    public static Bitmap getBitmap(){
        return user_face_bitmap;
    }
    public static int getScore(){
        score = for_counting_times.times_done;
        return score;
    }
    public static void setUser_name(String a){
        user_name=a;

    }
    public static void set_sex(String a){
        sex=a;

    }
    public static void set_email(String a){
        email=a;

    }
    public static void set_profession(String a){
        profession=a;

    }
    public static void set_motto(String a){
        motto=a;

    }

}
