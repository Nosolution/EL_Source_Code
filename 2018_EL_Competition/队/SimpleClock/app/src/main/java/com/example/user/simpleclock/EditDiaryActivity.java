package com.example.user.simpleclock;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import org.litepal.crud.DataSupport;

import java.util.List;

public class EditDiaryActivity extends AppCompatActivity {

    private EditText diaryEditTitle;
    private EditText diaryEditContext;
    RecordDiary mDiary;
    private boolean append;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_edit_diary);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        Intent intent = getIntent();
        append = intent.getBooleanExtra("append", false);

        ImageButton editFinished = (ImageButton) findViewById(R.id.finish_edit);
        ImageButton editBack = (ImageButton) findViewById(R.id.diary_edit_back);
        diaryEditTitle = (EditText) findViewById(R.id.edit_title);
        diaryEditContext = (EditText) findViewById(R.id.edit_context);

        if (append) {
            id = intent.getIntExtra("diaryId", 1);
            mDiary = DataSupport.find(RecordDiary.class, id);
            diaryEditTitle.setText(mDiary.getTitle(), TextView.BufferType.EDITABLE);
            diaryEditContext.setText(mDiary.getContext(), TextView.BufferType.EDITABLE);
        }

        editFinished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(EditDiaryActivity.this);
                alert.setTitle("保存日记");
                alert.setMessage("您要保存日记吗？");
                alert.setCancelable(false);
                alert.setNegativeButton("继续编辑", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert.setPositiveButton("保存并退出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!append) {
                            RecordDiary rd = new RecordDiary();
                            rd.setDiarydate(System.currentTimeMillis());
                            rd.setTitle(diaryEditTitle.getText().toString());
                            rd.setContext(diaryEditContext.getText().toString());
                            rd.save();
                        } else {
                            RecordDiary update = new RecordDiary();
                            update.setTitle(diaryEditTitle.getText().toString());
                            update.setContext(diaryEditContext.getText().toString());
                            update.update(id);
                        }

                        finish();
                    }
                });
                alert.show();
            }
        });

        editBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(EditDiaryActivity.this);
                alert.setTitle("退出编辑");
                alert.setMessage("您还未保存日记，确定要退出吗？");
                alert.setCancelable(false);
                alert.setPositiveButton("继续编辑", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert.setNegativeButton("退出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                alert.show();
            }
        });
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, EditDiaryActivity.class);
        intent.putExtra("append", false);
        context.startActivity(intent);
    }

    public static void actionStart(Context context, int diaryId) {
        Intent intent = new Intent(context, EditDiaryActivity.class);
        intent.putExtra("diaryId", diaryId);
        intent.putExtra("append", true);
        context.startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alert = new AlertDialog.Builder(EditDiaryActivity.this);
        alert.setTitle("退出编辑");
        alert.setMessage("您还未保存日记，确定要退出吗？");
        alert.setCancelable(false);
        alert.setPositiveButton("继续编辑", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alert.setNegativeButton("退出", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        alert.show();
    }

    @Override
    protected void onPause() {
        overridePendingTransition(0,0);
        super.onPause();
    }
}
