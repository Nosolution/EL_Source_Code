package com.elcontest;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

import com.facebook.react.ReactActivity;
import org.devio.rn.splashscreen.SplashScreen;

public class MainActivity extends ReactActivity {

    /**
     * Returns the name of the main component registered from JavaScript.
     * This is used to schedule rendering of the component.
     */
     @Override
     protected void onCreate(Bundle savedInstanceState) {
            SplashScreen.show(this);
            super.onCreate(savedInstanceState);
         final Activity activity = this;
         final Handler handler = new Handler();
         handler.postDelayed(new Runnable() {
             @Override
             public void run() {
                 SplashScreen.hide(activity);
             }
         }, 3000);
     }
    @Override
    protected String getMainComponentName() {
        return "ELcontest";
    }

}
