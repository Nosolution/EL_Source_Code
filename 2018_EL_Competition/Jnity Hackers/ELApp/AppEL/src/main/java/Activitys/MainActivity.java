package Activitys;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.elapp.AchievementActivity;
import com.example.lenovo.elapp.HelpActivity;
import com.example.lenovo.elapp.NewTaskActivity;
import com.example.lenovo.elapp.R;
import com.example.lenovo.elapp.RemindActivity;
import com.example.lenovo.elapp.StartActivity;

import Fragments.Fragment_Lib;
import Fragments.MainActivityLeftFragment;
import Tmp_lib.BottomNavigationView_Lib;
import Tmp_lib.Music_lib;


public class MainActivity extends AppCompatActivity {
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationTask();
        NavigationViewTask();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //通过菜单加载器获得菜单的布局
        getMenuInflater().inflate(R.menu.left_navigation_view_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * 重写的父类方法（用作点击事件）
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //判断item的id
        switch (item.getItemId()) {
            case R.id.nav_achievement:
                Intent intent = new Intent(MainActivity.this, AchievementActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_greeting:
                Toast.makeText(this, "ROOM!!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_store:
                Toast.makeText(this, "斯摩格", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void NavigationViewTask() {
        NavigationView navigationView = findViewById(R.id.navigation_view_left);
        navigationView.setItemIconTintList(null);
        TextView slideshow = (TextView) MenuItemCompat.getActionView(navigationView.getMenu().
                findItem(R.id.nav_coin));
        slideshow.setGravity(Gravity.CENTER_VERTICAL);
        slideshow.setTypeface(null, Typeface.BOLD);
        slideshow.setTextColor(getResources().getColor(R.color.colorAccent));
        slideshow.setText("1");

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_achievement:
                        Intent intent1 = new Intent(MainActivity.this, AchievementActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.nav_greeting:
                        Intent intent2 = new Intent(MainActivity.this, RemindActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.nav_help:
                        Intent intent3 = new Intent(MainActivity.this, HelpActivity.class);
                        startActivity(intent3);
                        break;
                }

                return true;
            }
        });

    }

    private void BottomNavigationTask() {
        Fragment_Lib.replaceFragment(this, new MainActivityLeftFragment(), R.id.root_Frame_layout);
        BottomNavigationView navigation = findViewById(R.id.MainActivityNavigation);
        navigation.setItemIconTintList(null);
        navigation.setOnNavigationItemSelectedListener(
                BottomNavigationView_Lib.Get_OnNavigationItemselectedListener(this));
        navigation.setItemIconTintList(null);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Music_lib.playExternal(new MediaPlayer(), "bgm1.mp3");//还未确定
                } else {
                    Toast.makeText(this, "拒绝权限无法访问", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }

    private boolean IsPermitted() {
        return ContextCompat.checkSelfPermission(MainActivity.this
                , Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(MainActivity.this
                , new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, 1);
    }
}
