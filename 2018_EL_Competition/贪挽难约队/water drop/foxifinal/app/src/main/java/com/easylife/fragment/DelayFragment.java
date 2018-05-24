package com.easylife.fragment;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.easylife.activity.R;
import com.easylife.adapter.DelayPagerAdapter;
import com.easylife.entity.Task;
import com.easylife.entity.User;
import com.easylife.util.SpacesItemDecoration;
import com.easylife.util.ToastUtil;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

import static android.content.Context.MODE_PRIVATE;

public class DelayFragment extends Fragment {
    private RecyclerView recyclerView;
    private DelayPagerAdapter adapter;
    private StaggeredGridLayoutManager layoutManager;
    private ImageView imageView;
    private TextView taskType;
    Button master;
    TextView todo;
    TextView finished;
    TextView failed;
    public List<Task> taskSet = new ArrayList<>();  //事务集
    AssetManager assets = null;
    private SpacesItemDecoration decoration;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragementdelay_layout, container, false);
        imageView = view.findViewById(R.id.background);
        taskType = view.findViewById(R.id.task_type_to_show);

        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        initData();
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(decoration);
        adapter.notifyItemRangeChanged(0, taskSet.size());

        String path = FocusFragment.leftbackground;
        path = path.replaceAll("left", "right");
        InputStream is = null;
        assets = getActivity().getAssets();
        try {
            is = assets.open(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        master = view.findViewById(R.id.master);
        todo = view.findViewById(R.id.todo);
        finished = view.findViewById(R.id.finished);
        failed = view.findViewById(R.id.failed);


        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inSampleSize = 10;
        imageView.setImageBitmap(BitmapFactory.decodeStream(is));


        master = view.findViewById(R.id.master);
        master.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(getActivity(), v);//第二个参数是绑定的那个view
            MenuInflater inflater1 = popup.getMenuInflater();//填充菜单
            inflater1.inflate(R.menu.main, popup.getMenu());//绑定菜单项的点击事件
            popup.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.add:
                        showDialogtoAdd();
                        break;
                    case R.id.todo:
                        readData(Task.STATE_TODO);
                        taskType.setText("待办事务");
                        break;
                    case R.id.finished:
                        readData(Task.STATE_FINISH);
                        taskType.setText("已完成事务");
                        break;
                    case R.id.failed:
                        readData(Task.STATE_FAILED);
                        taskType.setText("已逾期事务");
                        break;
                }
                return false;
            });
            popup.show();
        });
        return view;
    }

    private void initData() {
        layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        decoration = new SpacesItemDecoration(16);
        readData(Task.STATE_TODO);
        if (taskSet == null) {
            taskSet = new ArrayList<>();
        }
        adapter = new DelayPagerAdapter(taskSet);
        adapter.setOnItemClickListener(this::showDialogtoChange);
    }

    //添加事务
    private void showDialogtoAdd() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        final View layout = inflater.inflate(R.layout.mydialogtoadd_layout, null);//获取自定义布局
        builder.setView(layout);

        final EditText mytext = layout.findViewById(R.id.tasks);
        MaterialCalendarView calendar = layout.findViewById(R.id.calendar);
        calendar.setSelectedDate(CalendarDay.today());
        calendar.state().edit()
                .setMinimumDate(CalendarDay.today())
                .commit();


        builder.setPositiveButton("保存", (arg0, arg1) -> {
            CalendarDay day = calendar.getSelectedDate();
            String date = day.getYear() + "-" + (day.getMonth() + 1) + "-" + day.getDay();

            Task task = new Task();
            task.setUserId(getCurrentUser().getObjectId());
            task.setState(Task.STATE_TODO);
            task.setTaskName(mytext.getText().toString());
            task.setDdl(date);
            DelayFragment.this.taskSet.add(task);

            ToastUtil toastUtil = new ToastUtil();
            toastUtil.Short(getContext(), "待办事务添加成功！").getToast().show();
            addTask(task);
        });
        //取消
        builder.setNegativeButton("取消", (arg0, arg1) -> {
            // TODO Auto-generated method stub

        });
        final AlertDialog dlg = builder.create();
        dlg.show();
    }

    //查看或者修改事务
    public void showDialogtoChange(View view, int position) {
        if (view.getId() == R.id.delete) {
            deleteTask(position);
        } else if (view.getId() == R.id.finish_task) {
            int index = DelayFragment.this.taskSet.indexOf(((DelayPagerAdapter) recyclerView.getAdapter()).getTaskSet().get(position));
            Task task = DelayFragment.this.taskSet.get(index);
            task.setState(Task.STATE_FINISH);
            setTaskFinished(task);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            LayoutInflater inflater = getLayoutInflater();
            final View layout = inflater.inflate(R.layout.mydialogtoadd_layout, null);//获取自定义布局
            builder.setView(layout);

            final EditText mytext = layout.findViewById(R.id.tasks);
            MaterialCalendarView calendar = layout.findViewById(R.id.calendar);
            calendar.setSelectedDate(CalendarDay.today());
            calendar.state().edit()
                    .setMinimumDate(CalendarDay.today())
                    .commit();

            //设置默认文本
            TextView textView = (TextView) view;
            mytext.setText(textView.getText().toString());


            builder.setPositiveButton("保存", (arg0, arg1) -> {
                CalendarDay day = calendar.getSelectedDate();
                String date = day.getYear() + "-" + (day.getMonth() + 1) + "-" + day.getDay();
                Toast.makeText(getActivity(), "保存修改成功！", Toast.LENGTH_SHORT).show();
                int index = DelayFragment.this.taskSet.indexOf(((DelayPagerAdapter) recyclerView.getAdapter()).getTaskSet().get(position));
                DelayFragment.this.taskSet.get(index).setTaskName(mytext.getText().toString());
                DelayFragment.this.taskSet.get(index).setDdl(date);
                modifyTask(DelayFragment.this.taskSet.get(index));
            });
            //取消
            builder.setNegativeButton("取消", (arg0, arg1) -> {
                // TODO 已完成逻辑

            });
            final AlertDialog dlg = builder.create();
            dlg.show();
        }
    }

    //标记事务为已完成
    private void setTaskFinished(Task task) {
        task.setState(Task.STATE_FINISH);
        task.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e != null) {
                    Log.i("modifyTask", e.toString());
                } else {
                    recyclerView.getAdapter().notifyItemRangeChanged(0, taskSet.size());
                }
            }
        });
    }

    //修改事务信息
    private void modifyTask(Task task) {
        task.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e != null) {
                    Log.i("modifyTask", e.toString());
                } else {
                    recyclerView.getAdapter().notifyItemRangeChanged(0, taskSet.size());
                }
            }
        });
    }

    //添加事务
    private void addTask(Task tasksToAdd) {
        tasksToAdd.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                BmobQuery<Task> taskBmobQuery = new BmobQuery<>();
                taskBmobQuery.addWhereEqualTo("userId", getCurrentUser().getObjectId());
                taskBmobQuery.findObjects(new FindListener<Task>() {
                    @Override
                    public void done(List<Task> list, BmobException e) {
                        if (e == null && list.size() > 0) {
                            taskSet = list;
                            List<Task> taskAdapter = new ArrayList<>();
                            for (Task task : list) {
                                if (task.getState() == Task.STATE_TODO) {
                                    taskAdapter.add(task);
                                }
                            }
                            ((DelayPagerAdapter) recyclerView.getAdapter()).setTaskSet(taskAdapter);
                            recyclerView.getAdapter().notifyDataSetChanged();
                        }
                    }
                });
            }
        });
    }

    //删除事务
    private void deleteTask(int position) {
        Task taskToDelete = taskSet.remove(position);
        taskToDelete.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e != null) {
                    Log.i("deleteTask", e.toString());
                } else {
                    ((DelayPagerAdapter) recyclerView.getAdapter()).setTaskSet(taskSet);
                    recyclerView.getAdapter().notifyDataSetChanged();
                }
            }
        });
    }

    //从服务器端读取事务数据
    public void readData(int taskType) {
        BmobQuery<Task> taskBmobQuery = new BmobQuery<>();
        taskBmobQuery.addWhereEqualTo("userId", getCurrentUser().getObjectId());
        taskBmobQuery.findObjects(new FindListener<Task>() {
            @Override
            public void done(List<Task> list, BmobException e) {
                if (e == null && list.size() > 0) {
                    taskSet = list;
                    List<Task> taskAdapter = new ArrayList<>();
                    for (Task task : list) {
                        if (task.getState() == taskType) {
                            taskAdapter.add(task);
                        }
                    }
                    ((DelayPagerAdapter) recyclerView.getAdapter()).setTaskSet(taskAdapter);
                    recyclerView.getAdapter().notifyDataSetChanged();
                }
            }
        });
    }

    //获取本地用户
    public User getCurrentUser() {
        User currentUser;
        SharedPreferences preferences = getActivity().getSharedPreferences("login_user", MODE_PRIVATE);
        currentUser = new User(
                preferences.getString("username", "null"),
                preferences.getString("nickname", "null"),
                preferences.getString("password", "null"),
                preferences.getString("user_phone", "null"));
        currentUser.setObjectId(preferences.getString("objectID", "null"));
        currentUser.setPassword(preferences.getString("password", "null"));
        currentUser.setAvatarUrl(preferences.getString("avatar", "null"));
        currentUser.setAvatarFileName(preferences.getString("avatar_file_name", "null"));
        return currentUser;
    }

}

