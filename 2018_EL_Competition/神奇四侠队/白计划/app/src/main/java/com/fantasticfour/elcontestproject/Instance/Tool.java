package com.fantasticfour.elcontestproject.Instance;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Environment;

import java.io.InputStream;

public class Tool {
    public static Bitmap GetBitmapFromAssets(Context context, String fileName){
        Bitmap res = null;
        AssetManager am = context.getAssets();
        try{
            InputStream is = am.open(fileName);
            res = BitmapFactory.decodeStream(is);
            is.close();
        }catch (Exception e){e.printStackTrace();}
        return res;
    }
    public static Bitmap ToGrayScale(Bitmap bmpOriginal) {
        int width, height;
        height = bmpOriginal.getHeight();
        width = bmpOriginal.getWidth();

        Bitmap bmpGrayscale = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        Canvas c = new Canvas(bmpGrayscale);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(bmpOriginal, 0, 0, paint);
        return bmpGrayscale;
    }
    public static String AsciiToStr(int n){
        StringBuilder sb = new StringBuilder();
        sb.append((char)n);
        return sb.toString();
    }
    public static boolean HasSdcard() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            // 有存储的SDCard
            return true;
        } else {
            return false;
        }
    }


}
