package com.example.shutime;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;



public class Main4Activity extends AppCompatActivity implements View.OnClickListener {
    String date;
    String todo;
    TextView date1;
    TextView date2;
    TextView date3;
    TextView date4;
    TextView date5;
    TextView date6;
    TextView date7;
    TextView date8;
    TextView todo1;
    TextView todo2;
    TextView todo3;
    TextView todo4;
    TextView todo5;
    TextView todo6;
    TextView todo7;
    TextView todo8;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        Button button4=(Button)findViewById(R.id.button4);
        button4.setOnClickListener(this);
        Button button5=(Button)findViewById(R.id.button5);
        button5.setOnClickListener(this);
        TextView textView7=(TextView)findViewById(R.id.textView7);
        Typeface textFont1=Typeface.createFromAsset(getAssets(),"fonts/STXINGKA.ttf");
        textView7.setTypeface(textFont1);
        textView7.setTextColor(0xFF000000);

        todo1=(TextView) findViewById(R.id.todo1);
        todo2=(TextView) findViewById(R.id.todo2);
        todo3=(TextView) findViewById(R.id.todo3);
        todo4=(TextView) findViewById(R.id.todo4);
        todo5=(TextView) findViewById(R.id.todo5);
        todo6=(TextView) findViewById(R.id.todo6);
        todo7=(TextView) findViewById(R.id.todo7);
        todo8=(TextView) findViewById(R.id.todo8);
        date1=(TextView) findViewById(R.id.date1);
        date2=(TextView) findViewById(R.id.date2);
        date3=(TextView) findViewById(R.id.date3);
        date4=(TextView) findViewById(R.id.date4);
        date5=(TextView) findViewById(R.id.date5);
        date6=(TextView) findViewById(R.id.date6);
        date7=(TextView) findViewById(R.id.date7);
        date8=(TextView) findViewById(R.id.date8);

        SharedPreferences sp5 = getSharedPreferences("sp_demo", Context.MODE_PRIVATE);
        int looked_number=0;
        while(true){
            looked_number=looked_number+1;
            String x=sp5.getString("date"+looked_number,"h");
            if(x.equals("h")){
                break;
            }
            else {
                date=sp5.getString("date"+looked_number,"");
                todo=sp5.getString("todo"+looked_number,"");

                if(looked_number==1){
                    date1.setText(date);
                    todo1.setText(todo);
                }
                else if(looked_number==2){
                    date2.setText(date);
                    todo2.setText(todo);
                }
                else if(looked_number==3){
                    date3.setText(date);
                    todo3.setText(todo);
                }
                else if(looked_number==4){
                    date4.setText(date);
                    todo4.setText(todo);
                }
                else if(looked_number==5){
                    date5.setText(date);
                    todo5.setText(todo);
                }
                else if(looked_number==6){
                    date6.setText(date);
                    todo6.setText(todo);
                }
                else if(looked_number==7){
                    date7.setText(date);
                    todo7.setText(todo);
                }
                else if(looked_number==8){
                    date8.setText(date);
                    todo8.setText(todo);
                }
            }
        }


    }



    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.button5){
            Intent intent = new Intent(Main4Activity.this, MainActivity.class);
            startActivity(intent);
        }
        else if(view.getId()==R.id.button4){

            Calendar c=Calendar.getInstance();
            DatePickerDialog datePickerDialog=new DatePickerDialog(Main4Activity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    date=String.format("%d-%d-%d", year, month+1, dayOfMonth);
                    final AlertDialog dialog = new AlertDialog.Builder(Main4Activity.this).create();
                    dialog.setView(LayoutInflater.from(Main4Activity.this).inflate(R.layout.add, null));
                    dialog.show();
                    dialog.getWindow().setContentView(R.layout.add);
                    final EditText etContent = (EditText) dialog.findViewById(R.id.edit_text);
                    View btnPositive = (View) dialog.findViewById(R.id.yes);
                    View btnNegative = (View) dialog.findViewById(R.id.no);
                    btnPositive.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            todo = etContent.getText().toString();
                            int count=0;
                            SharedPreferences sp2 = getSharedPreferences("sp_demo", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp2.edit();
                            while(true){
                                count=count+1;
                                String x=sp2.getString("date"+count,"h");
                                if(x.equals("h")){
                                    break;
                                }
                            }
                            editor.putString("date"+count, date);
                            editor.putString("todo"+count, todo);
                            editor.commit();

                            if(count==1){
                                date1.setText(date);
                                todo1.setText(todo);
                            }
                            else if(count==2){
                                date2.setText(date);
                                todo2.setText(todo);
                            }
                            else if(count==3){
                                date3.setText(date);
                                todo3.setText(todo);
                            }
                            else if(count==4){
                                date4.setText(date);
                                todo4.setText(todo);
                            }
                            else if(count==5){
                                date5.setText(date);
                                todo5.setText(todo);
                            }
                            else if(count==6){
                                date6.setText(date);
                                todo6.setText(todo);
                            }
                            else if(count==7){
                                date7.setText(date);
                                todo7.setText(todo);
                            }
                            else if(count==8){
                                date8.setText(date);
                                todo8.setText(todo);
                            }

                            dialog.dismiss();
                        }
                    });


                    btnNegative.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View arg0) {
                            dialog.dismiss();
                        }
                    });

                }
            },c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();







        }
    }
}
