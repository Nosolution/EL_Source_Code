package Tmp_lib;

import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;

import com.example.lenovo.elapp.R;

import Activitys.MainActivity;
import Fragments.Fragment_Lib;
import Fragments.MainActivityLeftFragment;
import Fragments.MainActivityRightFragment;

public class BottomNavigationView_Lib {
    private MainActivity mainActivity=new MainActivity();
    /**
     * @param fragmentActivity :the source activity that contains the BottomNavigation
     */
    public static BottomNavigationView.OnNavigationItemSelectedListener
    Get_OnNavigationItemselectedListener(final FragmentActivity fragmentActivity) {
        return item -> {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Fragment_Lib.replaceFragment(fragmentActivity, new MainActivityLeftFragment()
                            , R.id.root_Frame_layout);
                    return true;
                case R.id.navigation_dashboard:
                    Intent intent=new Intent();



                    return true;
                case R.id.navigation_notifications:
                    Fragment_Lib.replaceFragment(fragmentActivity, new MainActivityRightFragment()
                            , R.id.root_Frame_layout);
                    return true;
            }
            return false;
        };
    }

}
