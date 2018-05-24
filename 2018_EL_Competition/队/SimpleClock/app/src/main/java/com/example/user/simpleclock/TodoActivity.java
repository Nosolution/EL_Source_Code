package com.example.user.simpleclock;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import org.litepal.crud.DataSupport;

import java.util.List;

import static android.support.v7.widget.helper.ItemTouchHelper.RIGHT;
import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class TodoActivity extends AppCompatActivity {

    SwipeMenuRecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_todo);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        ImageButton buttonBack = (ImageButton) findViewById(R.id.todo_back);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();

        final TextView text1 = (TextView) findViewById(R.id.todo_left);
        final TextView text2 = (TextView) findViewById(R.id.todo_min);

        text1.setText(getTodoAmount());
        text2.setText(getTodoMin());

        recyclerView = (SwipeMenuRecyclerView) findViewById(R.id.todolist);
        recyclerView.setItemViewSwipeEnabled(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);



        SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
            @Override
            public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(TodoActivity.this).setHeight(MATCH_PARENT).setImage(R.drawable.foxus_label);
                swipeRightMenu.addMenuItem(deleteItem);
                SwipeMenuItem startItem = new SwipeMenuItem(TodoActivity.this).setHeight(MATCH_PARENT).setImage(R.drawable.delete_label);
                swipeLeftMenu.addMenuItem(startItem);
            }
        };
        recyclerView.setItemViewSwipeEnabled(false);
        recyclerView.setSwipeMenuCreator(swipeMenuCreator);
        SwipeMenuItemClickListener swipeMenuItemClickListener = new SwipeMenuItemClickListener() {
            @Override
            public void onItemClick(SwipeMenuBridge menuBridge) {
                menuBridge.closeMenu();
                int direction = menuBridge.getDirection();
                int adapterPosition = menuBridge.getAdapterPosition();
                int menuPosition = menuBridge.getPosition();

                if (direction == 1) {
                    TodoItem target = TodoItem.getDataFromDatabase().get(adapterPosition);
                    int id = target.getId();
                    DataSupport.delete(TodoDatabase.class, id);
                    text1.setText(getTodoAmount());
                    text2.setText(getTodoMin());
                    TodoAdapter todoAdapter = new TodoAdapter(TodoItem.getDataFromDatabase());
                    recyclerView.setAdapter(todoAdapter);
                } else {
                    TodoItem target = TodoItem.getDataFromDatabase().get(adapterPosition);
                    Clock.actionStart(TodoActivity.this, target.getMinutes(), target.getId());
                }
            }
        };

        recyclerView.setSwipeMenuItemClickListener(swipeMenuItemClickListener);

        TodoAdapter todoAdapter = new TodoAdapter(TodoItem.getDataFromDatabase());
        recyclerView.setAdapter(todoAdapter);

        //
        final EditText content = (EditText) findViewById(R.id.editText);
        final EditText time = (EditText) findViewById(R.id.time_text);

        ImageButton imageButton = (ImageButton) findViewById(R.id.todo_done);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (time.getText().toString().trim().equals("") || Integer.parseInt(time.getText().toString()) > 60 || Integer.parseInt(time.getText().toString()) <= 0) {
                    Toast.makeText(TodoActivity.this, "专注时间至少1分钟，最长60分钟！", Toast.LENGTH_SHORT).show();
                } else {
                    int min = Integer.parseInt(time.getText().toString());
                    String todo = content.getText().toString();
                    TodoDatabase td = new TodoDatabase();
                    td.setMinutes(min);
                    td.setTodo(todo);
                    td.save();
                    time.getText().clear();
                    content.getText().clear();
                    text1.setText(getTodoAmount());
                    text2.setText(getTodoMin());
                    TodoAdapter todoAdapter = new TodoAdapter(TodoItem.getDataFromDatabase());
                    recyclerView.setAdapter(todoAdapter);
                }
            }
        });
    }

/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bundle bundle = data.getExtras();
        boolean isFinished = bundle.getBoolean("result", false);
        if (isFinished) {
            int id = bundle.getInt("reid", 0);
            DataSupport.delete(TodoDatabase.class, id);
            TodoAdapter todoAdapter = new TodoAdapter(TodoItem.getDataFromDatabase());
            recyclerView.setAdapter(todoAdapter);
        }
    }
    */


    public static void actionStart(Context context) {
        Intent intent = new Intent(context, TodoActivity.class);
        context.startActivity(intent);
    }

    private String getTodoAmount() {
        int total = 0;
        List<TodoDatabase> list = DataSupport.findAll(TodoDatabase.class);
        if (list.size() != 0) {
            for (TodoDatabase td : list) {
                total += 1;
            }
        }

        return total + "个事项";
    }

    private String getTodoMin() {
        int total = 0;
        List<TodoDatabase> list = DataSupport.findAll(TodoDatabase.class);
        if (list.size() != 0) {
            for (TodoDatabase td : list) {
                total += td.getMinutes();
            }
        }

        return "共" + total + "分钟";
    }
}
