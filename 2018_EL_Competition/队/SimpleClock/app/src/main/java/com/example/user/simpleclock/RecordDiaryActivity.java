package com.example.user.simpleclock;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import org.litepal.crud.DataSupport;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class RecordDiaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_record_diary);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        

        ImageButton diaryBack = (ImageButton) findViewById(R.id.record_diary_back);
        diaryBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ImageButton editButton = (ImageButton) findViewById(R.id.edit_button);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditDiaryActivity.actionStart(RecordDiaryActivity.this);
            }
        });

        final SwipeMenuRecyclerView recyclerView = (SwipeMenuRecyclerView) findViewById(R.id.record_diary_layout);


        SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
            @Override
            public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(RecordDiaryActivity.this).setHeight(MATCH_PARENT).setBackgroundColor(255).setImage(R.drawable.delete_label).setText("删除");
                swipeRightMenu.addMenuItem(deleteItem);
            }
        };


        recyclerView.setSwipeMenuCreator(swipeMenuCreator);

        SwipeMenuItemClickListener swipeMenuItemClickListener = new SwipeMenuItemClickListener() {
            @Override
            public void onItemClick(SwipeMenuBridge menuBridge) {
                menuBridge.closeMenu();
                int direction = menuBridge.getDirection();
                int adapterPosition = menuBridge.getAdapterPosition();
                int menuPosition = menuBridge.getPosition();

                DiaryRecord target = DiaryRecord.getDataFromDatabase().get(adapterPosition);
                int id = target.getDiaryId();
                DataSupport.delete(RecordDiary.class, id);
                RecordDiaryAdapter recordDiaryAdapter = new RecordDiaryAdapter(DiaryRecord.getDataFromDatabase());
                recyclerView.setAdapter(recordDiaryAdapter);
            }
        };

        recyclerView.setSwipeMenuItemClickListener(swipeMenuItemClickListener);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        RecordDiaryAdapter recordDiaryAdapter = new RecordDiaryAdapter(DiaryRecord.getDataFromDatabase());
        recyclerView.setAdapter(recordDiaryAdapter);




    }

    @Override
    public void onResume() {
        super.onResume();
        SwipeMenuRecyclerView recyclerView = (SwipeMenuRecyclerView) findViewById(R.id.record_diary_layout);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        RecordDiaryAdapter recordDiaryAdapter = new RecordDiaryAdapter(DiaryRecord.getDataFromDatabase());
        recyclerView.setAdapter(recordDiaryAdapter);
    }



    public static void actionStart(Context context) {
        Intent intent = new Intent(context, RecordDiaryActivity.class);
        context.startActivity(intent);
    }
}
