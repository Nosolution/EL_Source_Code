package com.fantasticfour.elcontestproject.Instance.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class CommonDAO {
    private CommonDataHelper m_CommonDataHelper;
    public SQLiteDatabase m_Database;

    public CommonDAO(Context context) {
        m_CommonDataHelper=new CommonDataHelper(context);
        m_Database = m_CommonDataHelper.getWritableDatabase();
    }

    public void Destroy(){
        m_Database.close();
    }
}
