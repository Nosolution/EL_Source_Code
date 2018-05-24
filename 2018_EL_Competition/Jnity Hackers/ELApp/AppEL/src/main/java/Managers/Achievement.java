package Managers;

import android.content.Context;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class Achievement {
    private static HashMap<String, Integer> stringIntegerHashMap;
    private String coin;
    private String accomplishment;
    private String AchievementFilePath;
    private FileManager fileManager;
    private LoadingManager loadingManager;
    private String separator = System.getProperty("line.separator");

    public String getCoin() {
        String value = "0";
        try {
            HashMap<String, Integer> tmpHashMap = getDataMap();
            value = String.valueOf(tmpHashMap.get("coin"));
            this.coin = value;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return value;
    }

    public String getAccomplishment() {
        synchronized (this) {
            String value = "0";
            try {
                HashMap<String, Integer> tmpHashMap = getDataMap();
                value = String.valueOf(tmpHashMap.get("accomplishment"));
                this.accomplishment = value;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return value;
        }
    }

    public void setCoin(String coin) {
        synchronized (this) {
            this.coin = coin;
            stringIntegerHashMap = flushMap();
            stringIntegerHashMap = writeAchievement(this.coin, this.accomplishment);
        }
    }

    public void setAccomplishment(String accomplishment) {
        synchronized (this) {
            this.accomplishment = accomplishment;
            stringIntegerHashMap = flushMap();
            stringIntegerHashMap = writeAchievement(this.coin, this.accomplishment);
        }
    }


    private Achievement(Context context) {
        synchronized (this) {
            coin = "0";
            accomplishment = "0";
            loadingManager = LoadingManager.getLoadingManager();
            fileManager = FileManager.getFileManager();
            AchievementFilePath = fileManager.getAppPath(context) + "achievement.txt";
        }
    }

    public static Achievement getAchievement(Context context) {
        synchronized (Achievement.class) {
            Achievement achievement = new Achievement(context);
            stringIntegerHashMap = new HashMap<>();
            try {
                stringIntegerHashMap = achievement.getDataMap();
                achievement.coin = String.valueOf(stringIntegerHashMap.get("coin"));
                achievement.accomplishment = String.valueOf(stringIntegerHashMap.get("accomplishment"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return achievement;
        }
    }


    private File getAchievementFile() throws IOException {
        synchronized (this) {

            File achievementFile = new File(AchievementFilePath);
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(achievementFile, true));
            bufferedWriter.close();
            return achievementFile;
        }
    }

    private HashMap<String, Integer> getDataMap() throws IOException {
        synchronized (this) {
            File achievementFile = getAchievementFile();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(achievementFile));
            String data;
            while ((data = bufferedReader.readLine()) != null) {
                String[] Key_Value = data.split("=");
                stringIntegerHashMap.put(Key_Value[0], Integer.parseInt(Key_Value[1]));
            }
            bufferedReader.close();
            return stringIntegerHashMap;
        }
    }

    private synchronized HashMap<String, Integer> writeAchievement(String coin, String accomplishment) {
        try {
            File targetFile = getAchievementFile();
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(targetFile, false));
            this.coin = coin;
            this.accomplishment = accomplishment;
            stringIntegerHashMap = flushMap();
            String line_1 = "coin=" + coin;
            String line_2 = "accomplishment=" + accomplishment;
            bufferedWriter.write(line_1 + separator + line_2);
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringIntegerHashMap;
    }

    private synchronized HashMap<String, Integer> flushMap() {
        int coinInt = 0;
        int accInt = 0;
        try {

            coinInt = Integer.parseInt(this.coin);
            accInt = Integer.parseInt(this.accomplishment);
        } catch (Exception e) {
            stringIntegerHashMap.put("coin", 0);
            stringIntegerHashMap.put("accomplishment", 0);
        }
        stringIntegerHashMap.put("coin", coinInt);
        stringIntegerHashMap.put("accomplishment", accInt);
        return stringIntegerHashMap;
    }

}
