package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class LessonServer extends AppServer implements Runnable {
    @Override
    public void run() {
        while (true) try {
            socket = serverSocket.accept();
            dataInputStream = new DataInputStream(socket.getInputStream());
            strings = dataInputStream.readUTF().split(" ");
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            //
            appWheel.appRooms.get(strings[0]).tellLesson(strings[1]);
            dataOutputStream.writeUTF("success");
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
