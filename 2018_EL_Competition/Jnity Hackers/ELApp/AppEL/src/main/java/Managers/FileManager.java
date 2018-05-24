package Managers;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

public class FileManager {

    private FileManager() {

    }

    public static FileManager getFileManager() {
        return new FileManager();
    }

    /**
     * @return SDcard Absolute Path of String
     */
    public String getSDPath() {
        return getSDFile().getAbsolutePath();
    }

    /**
     * @return SDcard rootPath's file
     */
    public File getSDFile() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory().getAbsoluteFile();// 获取根目录
        } else {
            Log.e("ERROR", "没有内存卡");
        }
        return sdDir;
    }


    public File getAppFile(Context context) {
        return context.getApplicationContext().getFilesDir();
    }

    public String getAppPath(Context context) {
        return getAppFile(context).getAbsolutePath();
    }

    /**
     * Get the inputStream of the assets file
     * You could use this to get the info from the files
     */
    public InputStream getAssets(Context context, String string) throws IOException {
        return context.getResources()
                .getAssets()
                .open(string);

    }

    public Uri getUriRes(Context context, int rawFile) {
        return Uri.parse("android.resource://" + context.getPackageName() + "/" + rawFile);
    }

    public String getAssetsInfo(Context context, String string) {
        try {
            return new String(InputStreamToByte(getAssets(context, string)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 从assets目录中复制整个文件夹内容
     *
     * @param context Context 使用CopyFiles类的Activity
     * @param oldPath String  原文件路径  如：/aa
     * @param newPath String  复制后路径  如：xx:/bb/cc
     */
    public void copyFilesFassets(Context context, String oldPath, String newPath) {
        try {
            String fileNames[] = context.getAssets().list(oldPath);//获取assets目录下的所有文件及目录名
            if (fileNames.length > 0) {//如果是目录
                File file = new File(newPath);
                file.mkdirs();//如果文件夹不存在，则递归
                for (String fileName : fileNames) {
                    copyFilesFassets(context, oldPath + "/" + fileName, newPath + "/" + fileName);
                }
            } else {//如果是文件
                InputStream is = context.getAssets().open(oldPath);
                FileOutputStream fos = new FileOutputStream(new File(newPath));
                byte[] buffer = new byte[1024];
                int byteCount = 0;
                while ((byteCount = is.read(buffer)) != -1) {//循环从输入流读取 buffer字节
                    fos.write(buffer, 0, byteCount);//将读取的输入流写入到输出流
                }
                fos.flush();//刷新缓冲区
                is.close();
                fos.close();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            //如果捕捉到错误则通知UI线程
        }
    }

    /**
     * The buffPath is in the appPath.
     */
    public File getAllSameSuffixPath(Context context, String suffix, String targetDicPath, boolean sd_app) throws IOException {
        List<String> data = new LinkedList<>();
        File targetDic;
        if (sd_app)
            targetDic = new File(getSDPath() + targetDicPath);
        else
            targetDic = new File(getAppPath(context) + targetDicPath);
        File des = new File(getAppPath(context) + "SameSuffixBuff.txt");
        data = getSuffixFile(data, targetDic.getAbsolutePath(), suffix);
        return copyFromList(des.getAbsolutePath(), data);
    }

    /**
     * You have to ask for permission the first time
     * you are going to visit the storage of the sdCard.
     */
    public static boolean IsPermitted(Context context) {
        return ContextCompat.checkSelfPermission(context
                , Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    public static void requestPermissions(Activity context) {
        ActivityCompat.requestPermissions(context, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        }, 1);
    }

    /**
     * 读取sd卡上指定后缀的所有文件
     *
     * @param files    返回的所有文件
     * @param filePath 路径(可传入sd卡路径)
     * @param suffere  后缀名称 比如 .gif
     * @return
     */
    private List<String> getSuffixFile(List<String> files, String filePath, String suffere) {

        File f = new File(filePath);

        if (!f.exists()) {
            return null;
        }

        File[] subFiles = f.listFiles();
        for (File subFile : subFiles) {
            if (subFile.isFile() && subFile.getName().endsWith(suffere)) {
                files.add(subFile.getAbsolutePath());
            } else if (subFile.isDirectory()) {
                getSuffixFile(files, subFile.getAbsolutePath(), suffere);
            } else {
                //非指定目录文件 不做处理
            }

        }

        return files;
    }

    private File copyFromList(String path, List<String> list) throws IOException {
        File file = new File(path);
        BufferedWriter bufferedWriter =
                new BufferedWriter(new FileWriter(file));
        for (String string : list) {
            bufferedWriter.write(string + "\n");
            bufferedWriter.flush();
        }
        bufferedWriter.close();
        return file;
    }


    private byte[] InputStreamToByte(InputStream is) throws IOException {
        ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
        int ch;
        while ((ch = is.read()) != -1) {
            bytestream.write(ch);
        }
        byte imgdata[] = bytestream.toByteArray();
        bytestream.close();
        return imgdata;
    }

    /**
     * 获取缓存文件夹目录 如果不存在创建 否则则创建文件夹
     *
     * @return filePath
     */
    private String isExistsFilePath() {
        String filePath = getSDPath();
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return filePath;
    }
}
