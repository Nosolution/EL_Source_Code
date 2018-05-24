package com.hlxx.climber.secondpage.settings;

public class TimeGet {
    private static int timeSecond;
    private static int timeSecondSetted;
    private static int totalTime = 0;

    public static int getTimeSecondSetted() {
        return timeSecondSetted;
    }

    public static void setTimeSecondSetted(int minutes) {
        TimeGet.timeSecondSetted = minutes * 60;
        TimeGet.timeSecond = minutes * 60;
        TimeGet.totalTime = minutes * 60;
    }

    private TimeGet() {
    }

    /**
     * @return [hour, minute,second]
     */
    public static int[] getTime() {
        int[] varTime = new int[3];
        varTime[0] = timeSecond / 3600;
        timeSecond = timeSecond - varTime[0] * 3600;
        varTime[1] = timeSecond / 60;
        varTime[2] = timeSecond - varTime[1] * 60;
        return varTime;
    }

    /**
     * @return millisTime
     */
    public static long getTime(boolean b) {
        int[] varTime = getTime();
        return (varTime[0] * 3600 + varTime[1] * 60 + varTime[2]) * 1000;
    }

    /**
     * @param minutes 设定时间，分钟单位
     */
    public static void setTimeMinute(int minutes) {
        timeSecond = minutes * 60;
    }

    /**
     * @param times 设定时间，[hour, minute,second]
     */
    static void setTimeSecond(int[] times) {
        if (times[2] > 60) {
            times[1] = times[2] / 60 + times[1];
            times[2] = times[2] % 60;
        }
        if (times[1] > 60) {
            times[0] = times[1] / 60 + times[0];
            times[1] = times[1] % 60;
        }
        timeSecond = times[0] * 3600 + times[1] * 60 + times[2];
    }

}
