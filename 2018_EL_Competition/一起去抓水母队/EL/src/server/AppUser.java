package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

class AppUser {
    String name;
    String password;
    String school;
    //总分
    int score = 0;
    //总计(0)、课程(1-11)、自习(12)、自定义(13)的时间、次数
    int[] time = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    int[] times = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    //网络组件
    Socket socket;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    //构造函数
    AppUser(String name, String password) {
        this.name = name;
        this.password = password;
    }
    //绑定socket
    void setIOStream(Socket socket, DataInputStream dataInputStream, DataOutputStream dataOutputStream) {
        this.socket = socket;
        this.dataInputStream = dataInputStream;
        this.dataOutputStream = dataOutputStream;
    }
    //被踢出房间
    void kickMember() throws IOException {
        dataOutputStream.writeUTF("kick");
        socket.close();
    }
}
