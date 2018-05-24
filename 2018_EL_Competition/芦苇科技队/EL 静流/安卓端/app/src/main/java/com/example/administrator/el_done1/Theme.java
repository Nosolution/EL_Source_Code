package com.example.administrator.el_done1;

/**
 * Created by Administrator on 2018-5-19.
 */

public class Theme {
    private static String THEME = "SIMPLE";

    /*目前只有SIMPLE（简易）、OTAKU（宅）、PET（萌宠）三种*/


    public static void setTHEME(String theme){
        Theme.THEME =theme;
    }
    public static String getTHEME(){
        return THEME;
    }
}
