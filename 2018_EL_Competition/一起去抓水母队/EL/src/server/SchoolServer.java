package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

//创建院系
public class SchoolServer extends AppServer implements Runnable {
    @Override
    public void run() {
        while (true) try {
            socket = serverSocket.accept();
            dataInputStream = new DataInputStream(socket.getInputStream());
            strings = dataInputStream.readUTF().split(" ");
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            //
            appWheel.appUsers.get(strings[0]).school = strings[1];
            dataOutputStream.writeUTF("success");
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
