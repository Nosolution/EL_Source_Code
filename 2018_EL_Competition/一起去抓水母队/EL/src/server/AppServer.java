package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

class AppServer {
    //轮子，轮子！
    AppWheel appWheel;
    //网络组件
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    ServerSocket serverSocket;
    Socket socket;
    String[] strings;
    //每个控制器接口，用以绑定轮子
    void setAppWheel(AppWheel appWheel) {
        this.appWheel = appWheel;
    }
}
