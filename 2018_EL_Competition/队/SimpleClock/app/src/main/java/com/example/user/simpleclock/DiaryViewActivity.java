package com.example.user.simpleclock;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import org.litepal.crud.DataSupport;
import org.w3c.dom.Text;

public class DiaryViewActivity extends AppCompatActivity {

    RecordDiary mDiary;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_diary_view);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        ImageButton buttonBack = (ImageButton) findViewById(R.id.diary_view_back);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ImageButton buttonEdit = (ImageButton) findViewById(R.id.diary_edit_button);


        Intent intent = getIntent();

        TextView title = (TextView) findViewById(R.id.diary_view_title);
        TextView context = (TextView) findViewById(R.id.diary_view_context);

        id = intent.getIntExtra("diaryId", 1);
        mDiary = DataSupport.find(RecordDiary.class, id);
        title.setText(mDiary.getTitle(), TextView.BufferType.EDITABLE);
        context.setText(mDiary.getContext(), TextView.BufferType.EDITABLE);

        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditDiaryActivity.actionStart(DiaryViewActivity.this, id);
            }
        });

    }

    public static void actionStart(Context context, int diaryId) {
        Intent intent = new Intent(context, DiaryViewActivity.class);
        intent.putExtra("diaryId", diaryId);
        context.startActivity(intent);
    }
}
