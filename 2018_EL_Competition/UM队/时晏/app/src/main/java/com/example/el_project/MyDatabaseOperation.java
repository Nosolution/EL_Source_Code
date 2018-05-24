package com.example.el_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
/**
 * Created by ns on 2018/5/1.
 * 数据库操作类，封装了一层常用操作，均为静态方法，直接调用即可
 *
 * @author NAiveD, ns
 * @version 1.5
 */
public class MyDatabaseOperation {
    /**
     * Delete task.
     *
     * @param context the context
     * @param id      the id 任务id
     */
    public static void deleteTask(Context context,int id){
        MyDatabaseHelper dbHelper=new MyDatabaseHelper(context,"TaskStore.db",null,4);
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        db.delete("Tasklist","id=?",new String[]{String.valueOf(id)});
    }

    /**
     * Query latest task id int.
     * 查询最后添加的任务的id
     *
     * @param context the context
     * @return the int
     */
    public static int queryLatestTaskId(Context context){
        MyDatabaseHelper dbHelper=new MyDatabaseHelper(context,"TaskStore.db",null,4);
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        Cursor cursor = db.query("Tasklist", null, null, null, null, null, null);
        if(cursor.moveToPosition(cursor.getCount()-1)){
            int id=Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
            cursor.close();
            return id;
        }
        else return 0;
    }

    /**
     * Give up finishing task.
     * 放弃完成一个任务，这会保存他的本次用时，下次继续任务时会累计
     *
     * @param context the context
     * @param id      the id 任务id
     * @param time    the time 存入的时间，以秒计
     */
    /*
    * 放弃任务时更新已进行的时间
     */
    public static void giveUpFinishingTask(Context context,int id,int time){
        MyDatabaseHelper dbHelper=new MyDatabaseHelper(context,"TaskStore.db",null,4);
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        Cursor cursor = db.query("Tasklist", null, "id=?", new String[]{String.valueOf(id)}, null, null, null);
        if(cursor.moveToFirst()){
            int oldTime=cursor.getInt(cursor.getColumnIndex("time_used"));
            ContentValues values = new ContentValues();
            values.put("time_used",time+oldTime);
            db.update("Tasklist",values,"id=?",new String[]{String.valueOf(id)});
        }
    }

    /**
     * Is daily task boolean.
     * 通过任务id判断某任务是否是每日任务
     *
     * @param context the context
     * @param id      the id 任务id
     * @return the boolean
     */
//提供两种查询方法，可分别根据id和任务名查询是否是每日任务
    public static boolean isDailyTask(Context context,int id){
        MyDatabaseHelper dbHelper=new MyDatabaseHelper(context,"TaskStore.db",null,4);
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        Cursor cursor=db.query("Tasklist",null,"id=?",new String[]{String.valueOf(id)},null,null,null);
        if(cursor.moveToFirst()){
            String temp=cursor.getString(cursor.getColumnIndex("isdailytask"));
            if(temp.equals("0"))
                return false;
            else
                return true;
        }
        cursor.close();
        return false;

    }

    /**
     * Is daily task boolean.
     * 通过任务名判断某任务是否是每日任务
     *
     * @param context  the context
     * @param taskName the task name 任务名
     * @return the boolean
     */
    public static boolean isDailyTask(Context context,String taskName){
        MyDatabaseHelper dbHelper=new MyDatabaseHelper(context,"TaskStore.db",null,4);
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        Cursor cursor=db.query("Tasklist",null,"task=?",new String[]{taskName},null,null,null);
        if(cursor.moveToFirst()){
            String temp=cursor.getString(cursor.getColumnIndex("isdailytask"));
            if(temp.equals("0"))
                return false;
            else
                return true;
        }
        cursor.close();
        return false;
    }

    /**
     * Last finished date int.
     * 得到一条每日任务最后完成的日期
     *
     * @param context the context
     * @param id      the id 任务id
     * @return the int
     */
    public static int lastFinishedDate(Context context,int id){
        MyDatabaseHelper dbHelper=new MyDatabaseHelper(context,"TaskStore.db",null,4);
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        Cursor cursor=db.query("Tasklist",null,"id=?",new String[]{String.valueOf(id)},null,null,null);
        if(cursor.moveToFirst()){
            int lastFinishedDate=cursor.getInt(cursor.getColumnIndex("last_finished_date"));
            return lastFinishedDate;
        }
        return 0;
    }

    /**
     * Set daily task finished.
     * 通过任务id设置每日任务完成
     *
     * @param context the context
     * @param id      the id 任务id
     */
//方法重载，完成每日任务，更新状态
    public static void setDailyTaskFinished(Context context,int id){
        MyDatabaseHelper dbHelper=new MyDatabaseHelper(context,"TaskStore.db",null,4);
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        Calendar calendar = new GregorianCalendar();
        SimpleDateFormat formatDate = new SimpleDateFormat("yyMMdd", Locale.getDefault());
        ContentValues values = new ContentValues();
        values.put("isdailytask",2);
        values.put("last_finished_date",Integer.parseInt(formatDate.format(calendar.getTime())));
        db.update("Tasklist",values,"id=?",new String[]{String.valueOf(id)});
    }

    /**
     * Set daily task finished.
     * 通过任务名设置每日任务完成
     *
     * @param context  the context
     * @param taskName the task name 任务名
     */
    public static void setDailyTaskFinished(Context context,String taskName){
        MyDatabaseHelper dbHelper=new MyDatabaseHelper(context,"TaskStore.db",null,4);
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        Calendar calendar = new GregorianCalendar();
        SimpleDateFormat formatDate = new SimpleDateFormat("yyMMdd", Locale.getDefault());
        ContentValues values = new ContentValues();
        values.put("isdailytask",2);
        values.put("last_finished_date",Integer.parseInt(formatDate.format(calendar.getTime())));
        db.update("Tasklist",values,"task=?",new String[]{taskName});
    }

    /**
     * Refresh all daily task.
     * 刷新所有的每日任务
     *
     * @param context the context
     */
//刷新所有已完成的每日任务
    public static void refreshAllDailyTask(Context context) {
        ArrayList<String> idList = new ArrayList<>();
        MyDatabaseHelper dbHelper = new MyDatabaseHelper(context, "TaskStore.db", null, 4);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Calendar calendar = new GregorianCalendar();
        SimpleDateFormat formatDate = new SimpleDateFormat("yyMMdd", Locale.getDefault());
        ContentValues values = new ContentValues();
        values.put("isdailytask", 1);

        Cursor cursor = db.query("Tasklist", null, "isdailytask=?", new String[]{"2"}, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                if (cursor.getInt(cursor.getColumnIndex("last_finished_date")) != Integer.parseInt(formatDate.format(calendar.getTime()))) {
                    db.update("Tasklist", values, "id=?", new String[]{cursor.getString(cursor.getColumnIndex("id"))});
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    /**
     * Get task list list.
     * 获得任务列表
     *
     * @param context the context
     * @return the list
     */
    public static List<Task> getTaskList(Context context){
        MyDatabaseHelper dbHelper = new MyDatabaseHelper(context, "TaskStore.db", null, 4);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor=db.query("Tasklist",null,null,null,null,null,null);
        if(cursor != null && cursor.moveToFirst()){
            List<Task> taskList=new ArrayList<>();
            do{
                int isDailyTask=cursor.getInt(cursor.getColumnIndex("isdailytask"));
                if(isDailyTask!=2){
                    Task tempTask=new Task(
                            cursor.getInt(cursor.getColumnIndex("id")),
                            cursor.getString(cursor.getColumnIndex("task")),
                            cursor.getString(cursor.getColumnIndex("assumedtime")),
                            cursor.getString(cursor.getColumnIndex("deadline")),
                            cursor.getInt(cursor.getColumnIndex("emergencydegree")),
                            isDailyTask,
                            cursor.getString(cursor.getColumnIndex("comments")),
                            cursor.getInt(cursor.getColumnIndex("time_used"))
                    );
                    taskList.add(tempTask);
                }
            }while(cursor.moveToNext());
            cursor.close();
            Collections.sort(taskList);
            return taskList;
        }
        cursor.close();
        return null;
    }

    /**
     * Get recommended task list list.
     * 获得推荐任务任务列表，最多3条
     *
     * @param context the context
     * @return the list
     */
    public static List<Task> getRecommendedTaskList(Context context){
        List<Task> finalList=getTaskList(context);
        if(finalList != null && finalList.size()>3)
            return finalList.subList(0,3);
        else
            return finalList;
    }

    /**
     * Add finish task with start time string.
     * 在开始计时时向数据库添加一条任务完成信息，以开始具体时间唯一标识
     *
     * @param context  the context
     * @param taskName the task name 任务名
     * @return the string 任务开始时间，也是任务完成信息唯一标识的id
     */
//任务开始时向数据库插入一条完成信息，此信息只保存具体开始时间，任务名，其他置为默认
    public static String addFinishTaskWithStartTime(Context context, String taskName){
        MyDatabaseHelper dbHelper = new MyDatabaseHelper(context, "TaskStore.db", null, 4);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        Calendar calendar = new GregorianCalendar();
        SimpleDateFormat format = new SimpleDateFormat("yyMMddHHmmss", Locale.getDefault());
        String startTime = format.format(calendar.getTime());
        values.put("detail_time_start",startTime);
        values.put("date", 0);
        values.put("week_count", 0);
        values.put("week", 0);
        values.put("task_name", taskName);
        values.put("task_time_used", 0);
        values.put("statue", 0);
        values.put("break_count", 0);
        db.insert("FinishTaskTable", null, values);
        values.clear();
        return startTime;
    }

    /**
     * Edit finish task when finishing.
     * 当任务完成或放弃后修改对应的完成信息，保存完成时日期，总任务耗时，完成状态，切换次数等
     *
     * @param context      the context
     * @param startTime    the start time 任务开始时间，也是唯一标识任务完成信息的id
     * @param taskTimeUsed the task time used 任务专注时间
     * @param statue       the statue 任务完成状态，0放弃， 1完成
     * @param breakCount   the break count 切出应用的计数
     */
    public static void editFinishTaskWhenFinishing(Context context, String startTime, int taskTimeUsed, int statue, int breakCount){
        MyDatabaseHelper dbHelper = new MyDatabaseHelper(context, "TaskStore.db", null, 4);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Calendar calendar = new GregorianCalendar();
        SimpleDateFormat formatDate = new SimpleDateFormat("yyMMdd", Locale.getDefault());
        SimpleDateFormat formatWeekCount = new SimpleDateFormat("yyw", Locale.getDefault());
        SimpleDateFormat formatWeek = new SimpleDateFormat("EEE", Locale.getDefault());
        ContentValues values = new ContentValues();
        values.put("date", Integer.parseInt(formatDate.format(calendar.getTime())));
        values.put("week_count", Integer.parseInt(formatWeekCount.format(calendar.getTime())));
        values.put("week", getWeekNum());
        values.put("task_time_used", taskTimeUsed);
        values.put("statue", statue);
        values.put("break_count", breakCount);

        db.update("FinishTaskTable", values, "detail_time_start = ?", new String[]{startTime});
    }

    /**
     * Refresh finish task table.
     * 更新任务计时记录表，删除无效记录
     * 无效记录为任务开始时添加的但因各种原因未在结束时更改的任务信息
     *
     * @param context the context
     */
    public static void refreshFinishTaskTable(Context context){
        MyDatabaseHelper dbHelper = new MyDatabaseHelper(context, "TaskStore.db", null, 4);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("FinishTaskTable", "week = ?", new String[]{"0"});
    }

    /**
     * Get total some day time used int.
     * 得到某日的总计专注时间
     *
     * @param context the context
     * @param date    the date 日期format:yyMMdd
     * @return the int
     */
//得到某日总计学习工作时间
    public static int getTotalSomeDayTimeUsed(Context context, int date){
        MyDatabaseHelper dbHelper = new MyDatabaseHelper(context, "TaskStore.db", null, 4);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        int totalTime = 0;

        Cursor cursor = db.query("FinishTaskTable", null, "date = ?",
                new String[]{Integer.toString(date)}, null, null, null);
        if(cursor.moveToFirst()){
            do{
                int timeUsed = cursor.getInt(cursor.getColumnIndex("task_time_used"));
                Log.d("TEST", "getTotalSomeDayTimeUsed: " + timeUsed);
                totalTime += timeUsed;
            }while (cursor.moveToNext());
        }
        cursor.close();
        return totalTime;
    }

    /**
     * Get total time used today int.
     * 得到今天的总计专注时长
     *
     * @param context the context
     * @return the int
     */
//得到今日总计学习工作时间
    public static int getTotalTimeUsedToday(Context context){
        Calendar calendar = new GregorianCalendar();
        SimpleDateFormat format = new SimpleDateFormat("yyMMdd", Locale.getDefault());
        return getTotalSomeDayTimeUsed(context, Integer.parseInt(format.format(calendar.getTime())));
    }

    /**
     * Get week per day time used int [ ].
     * 得到某一周的每日专注时长，从周日开始
     *
     * @param context   the context
     * @param weekCount the week count 某年的第几周format:yyw
     * @return the int [ ]
     */
    public static int[] getWeekPerDayTimeUsed(Context context, int weekCount){
        int[] timesWeek = new int[7];

        MyDatabaseHelper dbHelper = new MyDatabaseHelper(context, "TaskStore.db", null, 4);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query("FinishTaskTable", null, "week_count = ?",
                new String[]{Integer.toString(weekCount)}, null, null, null);
        if(cursor.moveToFirst()){
            do {
                int week = cursor.getInt(cursor.getColumnIndex("week"));
                timesWeek[week-1] += cursor.getInt(cursor.getColumnIndex("task_time_used"));
            }while (cursor.moveToNext());
        }
        return timesWeek;
    }

    /**
     * Get this week per day time used int [ ].
     * 得到本周每日专注时长，从周日开始，注意：未修正，当前完成的任务因为数据库存的慢所以未计算
     *
     * @param context the context
     * @return the int [ ]
     */
    public static int[] getThisWeekPerDayTimeUsed(Context context){
        Calendar calendar = new GregorianCalendar();
        SimpleDateFormat format = new SimpleDateFormat("yyw", Locale.getDefault());
        return getWeekPerDayTimeUsed(context, Integer.parseInt(format.format(calendar.getTime())));
    }

    /**
     * Get this week per day time used with correction int [ ].
     * 得到修正了的本周每日专注时长，从周日开始
     *
     * @param context    the context
     * @param correction the correction
     * @return the int [ ]
     */
    public static int[] getThisWeekPerDayTimeUsedWithCorrection(Context context, int correction){
        Calendar calendar = new GregorianCalendar();
        SimpleDateFormat format = new SimpleDateFormat("yyw", Locale.getDefault());
        int[] result = getWeekPerDayTimeUsed(context, Integer.parseInt(format.format(calendar.getTime())));
        result[getWeekNum() - 1] += correction;
        return result;
    }

    /**
     * Get week num int.
     * 得到今天以数字表示的周几序号，从1到7表示从周一到周日
     *
     * @return the int
     */
    public static int getWeekNum(){
        Calendar calendar = new GregorianCalendar();
        SimpleDateFormat format = new SimpleDateFormat("EEE", Locale.getDefault());
        String week = format.format(calendar.getTime());
        int weekNum = 1;
        switch (week){
            case "周一":
            case "Mon": weekNum = 1; break;
            case "周二":
            case "Tue": weekNum = 2; break;
            case "周三":
            case "Wed": weekNum = 3; break;
            case "周四":
            case "The": weekNum = 4; break;
            case "周五":
            case "Fri": weekNum = 5; break;
            case "周六":
            case "Sat": weekNum = 6; break;
            case "周日":
            case "Sun": weekNum = 7; break;
        }
        Log.d("TEST", "getWeekNum: " + weekNum + week);
        return weekNum;
    }
}
