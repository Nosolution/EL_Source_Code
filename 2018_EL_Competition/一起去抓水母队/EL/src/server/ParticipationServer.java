package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

//加入房间
public class ParticipationServer extends AppServer implements Runnable {
    @Override
    public void run() {
        while (true) try {
            socket = serverSocket.accept();
            dataInputStream = new DataInputStream(socket.getInputStream());
            strings = dataInputStream.readUTF().split(" ");
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            //
            String roomID = strings[0];
            if (appWheel.appRooms.get(roomID) == null)
                dataOutputStream.writeUTF("fail");
            else if (appWheel.appRooms.get(roomID).members.size() == 4)
                dataOutputStream.writeUTF("full");
            else {
                appWheel.appUsers.get(strings[1]).setIOStream(socket, dataInputStream, dataOutputStream);
                dataOutputStream.writeUTF("success");
                appWheel.appRooms.get(roomID).members.add(appWheel.appUsers.get(strings[1]));
                appWheel.appRooms.get(roomID).tellMembers();
            }
//            System.out.println(appWheel.appRooms.get(strings[0]).members.get(1).name);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
