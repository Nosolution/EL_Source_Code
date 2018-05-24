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

public class NickNameManager {
    private String nickPath;
    private FileManager fileManager;
    private List<NickName> nickNameList;

    private NickNameManager(Context context) {
        this.nickNameList = new LinkedList<>();
        this.fileManager = FileManager.getFileManager();
        this.nickPath = fileManager.getAppPath(context) + "NickName.txt";
    }

    public static NickNameManager getNickNameManager(Context context) {
        return new NickNameManager(context);
    }

    public String setNickName(String str) {
        NickName nickName = NickName.getNick(str);
        try {
            this.nickNameList.add(nickName);
            writeObjFile(nickNameList);
            return getNickName();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return nickName.getNickName();
    }

    public String getNickName() {
        try {
            nickNameList = flushList();
            NickName nickName = nickNameList.get(0);
            return nickName.getNickName();
        } catch (Exception e) {
            return nickPath;
        }
    }

    private List<NickName> flushList() throws IOException {
        this.nickNameList = getFormFile();
        return this.nickNameList;
    }

    private void writeObjFile(List<NickName> nickNameList) throws IOException {
        BufferedOutputStream bufferedOutputStream =
                new BufferedOutputStream(new FileOutputStream(getBuffFile()));
        String output = JSON.toJSONString(nickNameList, true);
        bufferedOutputStream.write(output.getBytes());
        bufferedOutputStream.flush();
        bufferedOutputStream.close();
    }

    private List<NickName> getFormFile() throws IOException {
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
        return JSON.parseArray(result, NickName.class);
    }

    private File getBuffFile() {
        return new File(this.nickPath);
    }
}
