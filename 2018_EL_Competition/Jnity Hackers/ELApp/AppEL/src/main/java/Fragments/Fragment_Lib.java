package Fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class Fragment_Lib extends AppCompatActivity {

    /**
     * @param fragmentActivity:the source activity
     * @param fragment:Destination of the replacement
     * @param Root_frame_layout    :the id of the fragment layout___be careful of the param!!!
     */
    public static void replaceFragment(FragmentActivity fragmentActivity, Fragment fragment
            , int Root_frame_layout) {
        FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(Root_frame_layout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
//
//    public static void replaceFragment(Fragment fragment) {
//
//    }
}
