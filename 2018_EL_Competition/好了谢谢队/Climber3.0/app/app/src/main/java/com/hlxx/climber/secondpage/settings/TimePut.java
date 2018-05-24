package com.hlxx.climber.secondpage.settings;

public class TimePut {
    /**
     * @param millis:毫秒时间
     * @return 时：分：秒
     * @function 毫秒时间时间格式化为字符串
     */
    public static String intsToString(long millis) {

        int[] intsTimes = new int[3];
        intsTimes[2] = (int) (millis / 1000);
        intsTimes[1] = intsTimes[2] / 60;
        intsTimes[2] = intsTimes[2] % 60;
        intsTimes[0] = intsTimes[1] / 60;
        intsTimes[1] = intsTimes[1] % 60;

        String[] hour_Minute_Second = new String[3];
        if (intsTimes[0] < 10L) {
            hour_Minute_Second[0] = "0" + intsTimes[0];
        } else {
            hour_Minute_Second[0] = String.valueOf(intsTimes[0]);
        }

        if (intsTimes[1] < 10L) {
            hour_Minute_Second[1] = "0" + intsTimes[1];
        } else {
            hour_Minute_Second[1] = String.valueOf(intsTimes[1]);
        }

        if (intsTimes[2] < 10L) {
            hour_Minute_Second[2] = "0" + intsTimes[2];
        } else {
            hour_Minute_Second[2] = String.valueOf(intsTimes[2]);
        }
        return String.format("%1$s : %2$s : %3$s", hour_Minute_Second[0], hour_Minute_Second[1], hour_Minute_Second[2]);
    }

    /**
     * @param sTime:字符串时：分：秒
     * @return int数组形式
     * @function 对于字符串时：分：秒形式的时间格式化为数组
     */
    public static int[] stringToInts(String sTime) {
        String[] sTimes = sTime.split(":");
        int[] iTimes = new int[3];
        if (sTimes.length == 2) {
            for (int i = 1; i < 3; i++) {
                iTimes[i] = Integer.parseInt(sTimes[i-1].trim());
            }
        } else if (sTimes.length == 3) {
            for (int i = 0; i < 3; i++) {
                iTimes[i] = Integer.parseInt(sTimes[i].trim());
            }
        }
        return iTimes;
    }

    public static int intsToSecond(int[] times) {
        return times[0] * 3600 + times[1] * 60 + times[2];
    }
}
