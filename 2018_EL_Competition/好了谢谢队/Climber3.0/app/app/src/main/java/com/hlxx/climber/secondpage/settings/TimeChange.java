package com.hlxx.climber.secondpage.settings;

import static com.hlxx.climber.secondpage.settings.TimePut.intsToSecond;
import static com.hlxx.climber.secondpage.settings.TimePut.intsToString;
import static com.hlxx.climber.secondpage.settings.TimePut.stringToInts;

public class TimeChange {

    public static void changeTime(int[] originalTime) throws TooManyTimesException {
        originalTime = switches(originalTime);
        TimeGet.setTimeSecond(originalTime);
    }

    public static void changeTime(String sOriginalTime) throws TooManyTimesException {
        int[] iOriginalTime = stringToInts(sOriginalTime);
        changeTime(iOriginalTime);

    }

    private static int[] switches(int[] time) throws TooManyTimesException {
        int seconds = intsToSecond(time);
        switch (IsForeground.getTimes()) {
            case 1:
                seconds = (int) (seconds * 1.10);
                break;
            case 2:
                seconds = (int) (seconds * 1.25);
                break;
            case 3:
                seconds = (int) (seconds * 1.50);
                break;
            default:
                throw new TooManyTimesException();
        }
        time = stringToInts(intsToString(seconds * 1000));
        return time;
    }
}

