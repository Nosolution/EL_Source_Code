package Managers;


import android.content.Context;

import com.alibaba.fastjson.JSON;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

//任务名，重要性（颜色：四种颜色）
//开始时间，完成时间
//所需交互APP
//单次事项or每周每天
//提醒时间
//备注


//Json解析
public class TaskManager {
    private String tasksPath;
    private List<Task> taskList;
    private MusicManager music_manager;
    private FileManager file_manager;
    private Achievement achievement;

    private TaskManager(Context context) {
        taskList = new LinkedList<>();
        music_manager = MusicManager.getMusicManager();
        file_manager = FileManager.getFileManager();
        achievement = Achievement.getAchievement(context);
        tasksPath = file_manager.getAppPath(context) + "tasks.txt";
    }

    public static TaskManager getTaskManager(Context context) {
        return new TaskManager(context);
    }

    public Task addTask(Task task) {
        try {
            if (!taskList.contains(task))
                taskList.add(task);
            writeObjFile(taskList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return task;
    }

    public List<Task> getTaskList() {
        try {
            this.taskList = getTasksFormFile();
            return taskList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this.taskList;
    }

    public void deleteTask(Task task) {
        try {
            this.taskList.remove(task);
            writeObjFile(taskList);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                this.taskList = flushTask();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void TaskReady(Task task) {
        task.setCondition("ready");
    }

    public void TaskBegin(Task task) {
        task.setCondition("begin");
    }

    public void TaskFinish(Task task) {
        task.setCondition("finish");
    }

    public boolean IsReady(Task task) {
        return task.getCondition().equals("ready");
    }

    public boolean IsBegin(Task task) {
        return task.getCondition().equals("begin");
    }

    public boolean IsFinish(Task task) {
        return task.getCondition().equals("finish");
    }

    public void TaskSuccess(Task task) {
        task.setAccession("succeeded");
        addTask(task);
    }

    public void TaskFail(Task task) {

        synchronized (this) {
            task.setAccession("failed");
            addTask(task);
        }
    }

    private List<Task> flushTask() throws IOException {
        this.taskList = getTasksFormFile();
        return this.taskList;
    }

    private List<Task> getTasksFormFile() throws IOException {
        BufferedOutputStream bufferedOutputStream =
                new BufferedOutputStream(new FileOutputStream(getBuffFile(), true));
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(getBuffFile())));
        StringBuilder tmp = new StringBuilder();
        String result;
        while ((result = br.readLine()) != null) {
            tmp.append(result);
        }
        result = tmp.toString();
        br.close();
        bufferedOutputStream.close();
        return JSON.parseArray(result, Task.class);
    }

    private File getBuffFile() {
        return new File(this.tasksPath);
    }

    private void writeObjFile(List<Task> jsonArray) throws IOException {
        synchronized (this) {
            BufferedOutputStream bufferedOutputStream =
                    new BufferedOutputStream(new FileOutputStream(getBuffFile()));
            String output = JSON.toJSONString(jsonArray, true);
            bufferedOutputStream.write(output.getBytes());
            bufferedOutputStream.flush();
            bufferedOutputStream.close();
        }
    }
}
