package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

//从客户端同步到服务器端
class RecordServer  extends AppServer implements Runnable {
    @Override
    public void run() {
        while (true) try {
            socket = serverSocket.accept();
            dataInputStream = new DataInputStream(socket.getInputStream());
            strings = dataInputStream.readUTF().split(" ");
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            //
            appWheel.appUsers.get(strings[0]).score = Integer.parseInt(strings[1]);
            for (int i = 0; i < 14; i++) {
                appWheel.appUsers.get(strings[0]).time[i] = Integer.parseInt(strings[i + 2]);
                appWheel.appUsers.get(strings[0]).times[i] = Integer.parseInt(strings[i + 16]);
            }
            dataOutputStream.writeUTF("success");
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
