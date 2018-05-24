package com.example.lenovo.elapp;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class NewTaskActivity extends AppCompatActivity {
    private CheckBox[] checkBoxes = new CheckBox[4];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.hide();
        }
        checkBoxes[0] = (CheckBox) findViewById(R.id.redCheckbox);
        checkBoxes[1] = (CheckBox) findViewById(R.id.orangeCheckbox);
        checkBoxes[2] = (CheckBox) findViewById(R.id.pinkCheckbox);
        checkBoxes[3] = (CheckBox) findViewById(R.id.blueCheckbox);
        checkBoxes[0].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    for (int i = 0; i < checkBoxes.length; i++) {
                        //不等于当前选中的就变成false
                        if (checkBoxes[i].getText().toString().equals(buttonView.getText().toString())) {
                            checkBoxes[i].setChecked(true);
                        } else {
                            checkBoxes[i].setChecked(false);
                        }
                    }
                }
            }
        });
        checkBoxes[1].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    for (int i = 0; i < checkBoxes.length; i++) {
                        //不等于当前选中的就变成false
                        if (checkBoxes[i].getText().toString().equals(buttonView.getText().toString())) {
                            checkBoxes[i].setChecked(true);
                        } else {
                            checkBoxes[i].setChecked(false);
                        }
                    }
                }
            }
        });
        checkBoxes[2].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    for (int i = 0; i < checkBoxes.length; i++) {
                        //不等于当前选中的就变成false
                        if (checkBoxes[i].getText().toString().equals(buttonView.getText().toString())) {
                            checkBoxes[i].setChecked(true);
                        } else {
                            checkBoxes[i].setChecked(false);
                        }
                    }
                }
            }
        });
        checkBoxes[3].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    for (int i = 0; i < checkBoxes.length; i++) {
                        //不等于当前选中的就变成false
                        if (checkBoxes[i].getText().toString().equals(buttonView.getText().toString())) {
                            checkBoxes[i].setChecked(true);
                        } else {
                            checkBoxes[i].setChecked(false);
                        }
                    }
                }
            }
        });

        Button backBtn = (Button) findViewById(R.id.newTask_btdBack);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }



}
