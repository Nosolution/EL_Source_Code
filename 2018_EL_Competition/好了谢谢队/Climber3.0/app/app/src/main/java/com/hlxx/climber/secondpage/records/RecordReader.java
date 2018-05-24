package com.hlxx.climber.secondpage.records;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Calendar;

public class RecordReader {
    private File fileMonth;
    private File fileDay;
    private File applicationDir;
    private File timeOfDay;


    public RecordReader(File applicationDir) {
        this.applicationDir = applicationDir;
        fileMonth = new File(applicationDir, String.valueOf(Calendar.getInstance().get(Calendar.MONTH) + 1));
        fileDay = new File(fileMonth, "" + Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        timeOfDay = new File(fileDay, "timeOfDay.hlxx");
    }

    public static Record oneRecordReaded(File recordPath) throws IOException, ClassNotFoundException {
        return objectReader(recordPath);
    }

    public static Records recordsReaded(File recordPath) throws IOException, ClassNotFoundException {
        return objectReader(recordPath);
    }

    public static int[] timeOfDayGet(File timeOfDayPath) throws IOException, ClassNotFoundException {
        return objectReader(timeOfDayPath);
    }


    public static <T> T objectReader(File objectPath) throws IOException, ClassNotFoundException {
        FileInputStream objectFIS = new FileInputStream(objectPath);
        ObjectInputStream objectOIS = new ObjectInputStream(objectFIS);
        T object = (T) objectOIS.readObject();
        objectOIS.close();
        objectFIS.close();
        return object;
    }
}
