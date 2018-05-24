package com.fantasticfour.elcontestproject.Instance.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.fantasticfour.elcontestproject.Instance.Ins_Achievement.AchievementDataNode;
import com.fantasticfour.elcontestproject.Instance.Ins_Schedule.PlanDataNode;
import com.fantasticfour.elcontestproject.Instance.Ins_WNoise.WNoiseDataNode;
import com.fantasticfour.elcontestproject.Instance.Ins_WNoise.WNoisePresetDataNode;

public class CommonDataHelper extends SQLiteOpenHelper {
    private static final String s_DatabaseName="total_data.db";
    private static final int s_SQLiteVersion=1;
    private static Context m_Context;
    public CommonDataHelper(Context context){
        super(context,s_DatabaseName,null,s_SQLiteVersion);
        m_Context=context;
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(PlanDataNode.s_CreatePlanTable);
        db.execSQL(WNoiseDataNode.s_CreateWNoiseTable);
        db.execSQL(WNoisePresetDataNode.s_CreateWNoisePresetTable);
        db.execSQL(AchievementDataNode.s_CreateAchievementTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
    }
}
