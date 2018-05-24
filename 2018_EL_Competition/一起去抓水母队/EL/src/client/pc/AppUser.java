package client.pc;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

class AppUser {
    String name = "";
    String password = "";
    String school = "";
    String roonID = "";
    //当前课程、模式和状态，单人为single为true，group为false
    String lesson = "";
    boolean mode;
    boolean leader;
    //总分
    int score = 0;
    //总计(0)、课程(1-11)、自习(12)、自定义(13)的时间、次数
    int[] time = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    int[] times = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    //网络组件
    Socket socket;
    DataInputStream dataInputStream;

    //关闭程序时同步
    void record() {
        try {
            if (!roonID.equals("")) {
                DataInputStream dataInputStream;
                DataOutputStream dataOutputStream;
                Socket socket;
                String[] strings;
                if (leader)
                    socket = new Socket(AppWheel.appHost, 23336);
                else
                    socket = new Socket(AppWheel.appHost, 23338);
                dataOutputStream = new DataOutputStream(socket.getOutputStream());
                dataInputStream = new DataInputStream(socket.getInputStream());
                //
                dataOutputStream.writeUTF(roonID + " " + name);
                strings = dataInputStream.readUTF().split(" ");
                if (strings[0].equals("success"))
                    socket.close();
                roonID = "";
            } else {
                DataInputStream dataInputStream;
                DataOutputStream dataOutputStream;
                Socket socket;
                String[] strings;
                socket = new Socket(AppWheel.appHost, 23339);
                dataOutputStream = new DataOutputStream(socket.getOutputStream());
                dataInputStream = new DataInputStream(socket.getInputStream());
                //
                StringBuilder stringBuilder = new StringBuilder(name);
                stringBuilder.append(" ").append(score);
                for (int i : time)
                    stringBuilder.append(" ").append(String.valueOf(i));
                for (int i : times)
                    stringBuilder.append(" ").append(String.valueOf(i));
                dataOutputStream.writeUTF(stringBuilder.toString());
                strings = dataInputStream.readUTF().split(" ");
                if (strings[0].equals("success"))
                    socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //绑定socket
    void setIOStream(Socket socket, DataInputStream dataInputStream) {
        this.socket = socket;
        this.dataInputStream = dataInputStream;
    }
}
