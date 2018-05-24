package Tmp_lib;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;

/**                                         ---created by zhouzheng on 4.25
 **---This is a function to open another app in the phone and go to its main activity.
 * --First: the name of the function is "openApp"
 * --Second: it needs two arguments:
 *
 *           1.Context :  it means where you use this function(type : context)
 *
 *           2.packageName : it's just the package name of the app you want to start(type: string)
 *                        for example: "com.gotokeep.keep" (Keep)
 *                                     "com.shanbay.words" (扇贝单词）
 *                                     “com.android.chrome” （Chrome Browser）
 *                                     “com.google.android.apps.maps” （Google Map）
 *
 * --Third: if you start it correctly , we'll go to the target app
 *          if the target app isn't installed, we go to the relevant website to download.
 *
 */

public class appInteraction {
    final String keep = "http://gotokeep.com/";
    final String Scallop_word = "https://www.shanbay.com/";
    String website;

    private static boolean isApkInstalled(Context context, String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            return false;
        }
        try {
            ApplicationInfo info = context.getPackageManager().getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void openApp(Context context, String packageName){
        if (packageName=="com.gotokeep.keep"){
            website = keep;
        }
        if (packageName=="com.shanbay.words"){
            website = Scallop_word;
        }
        if (isApkInstalled(context, packageName)){
            Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
            if (intent != null) {
                intent.putExtra("another", "110");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        } else{
            Intent viewIntent = new Intent();
            viewIntent.setAction("android.intent.action.VIEW");
            Uri content_url = Uri.parse(website);
            viewIntent.setData(content_url);
            context.startActivity(viewIntent);
        }
    }
}

/*
还需完善功能，应该出现异常时，先弹出一个窗口，确定是否要跳转到下载界面。
 */