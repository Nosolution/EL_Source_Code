package com.example.el_project;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import java.io.File;

/**
 * The type Temp pic storage manager.
 * 临时图片的储存管理类
 *
 * @author NAiveD
 * @version 1.1
 */
public class TempPicStorageManager {
    private String dirPath;
    private Context context;

    /**
     * Instantiates a new Temp pic storage manager.
     *
     * @param context the context
     * @param dirName the dir name 需要管理的图片目录名
     */
    TempPicStorageManager(Context context, String dirName){
        this.context = context;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            dirPath = context.getExternalFilesDir(dirName).getAbsolutePath();
        }else {
            dirPath = context.getFilesDir()+ File.separator+dirName;
        }
        File file = new File(dirPath);
        if (!file.exists()){
            file.mkdirs();
        }
    }

    /**
     * Get dir path string.
     * 返回图片目录完整路径
     *
     * @return the string
     */
    public String getDirPath(){
        return dirPath;
    }

    /**
     * Clean dir boolean.
     * 清理一个目录，返回成功或失败
     *
     * @param dirPath the dir path 目录完整路径
     * @return the boolean
     */
    private boolean cleanDir(String dirPath){
        File dir = new File(dirPath);

        if(!dir.exists() || !dir.isDirectory()){
            Log.d("DIR_DELETE_ERROR", "clean: 文件夹不存在或非文件");
            return false;
        }
        boolean flag = true;
        File[] childrenFiles = dir.listFiles();
        for (File file: childrenFiles){
            if(file.isFile()){
                flag = file.delete();
            }else if(file.isDirectory()){
                flag = cleanDir(file.getAbsolutePath());
            }else {
                return false;
            }
            if (!flag){
                return false;
            }
        }

        return true;
    }

    /**
     * Clean boolean.
     * 清理此临时图片目录，返回清理成功或失败
     *
     * @return the boolean
     */
    public boolean clean(){
        return cleanDir(dirPath);
    }
}
