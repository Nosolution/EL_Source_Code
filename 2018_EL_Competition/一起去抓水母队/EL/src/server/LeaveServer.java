package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

//离开房间
public class LeaveServer extends AppServer implements Runnable {
    @Override
    public void run() {
        while (true) try {
            socket = serverSocket.accept();
            dataInputStream = new DataInputStream(socket.getInputStream());
            strings = dataInputStream.readUTF().split(" ");
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            //
            appWheel.appRooms.get(strings[0]).members.remove(appWheel.appUsers.get(strings[1]));
            appWheel.appUsers.get(strings[1]).kickMember();
            appWheel.appRooms.get(strings[0]).tellMembers();
            dataOutputStream.writeUTF("success");
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
