package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Random;

//创建房间
public class EstablishmentServer extends AppServer implements Runnable {
    @Override
    public void run() {
        while (true) try {
            socket = serverSocket.accept();
            dataInputStream = new DataInputStream(socket.getInputStream());
            strings = dataInputStream.readUTF().split(" ");
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            //
            Random random = new Random();
            String roomID = String.valueOf(random.nextInt(10000));
            while (roomID.length() != 4)
                roomID = String.valueOf(random.nextInt(10000));
            appWheel.appRooms.put(roomID, new AppRoom(appWheel.appUsers.get(strings[0])));
            dataOutputStream.writeUTF(roomID);
            appWheel.appUsers.get(strings[0]).setIOStream(socket, dataInputStream, dataOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
