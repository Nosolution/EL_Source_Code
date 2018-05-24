package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

//创建用户名和密码
public class SigninServer extends AppServer implements Runnable {
    @Override
    public void run() {
        while (true) try {
            socket = serverSocket.accept();
            dataInputStream = new DataInputStream(socket.getInputStream());
            strings = dataInputStream.readUTF().split(" ");
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            //
            if (appWheel.appUsers.get(strings[0]) == null) {
                appWheel.appUsers.put(strings[0], new AppUser(strings[0], strings[1]));
                dataOutputStream.writeUTF("success");
            } else
                dataOutputStream.writeUTF("fail");
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
