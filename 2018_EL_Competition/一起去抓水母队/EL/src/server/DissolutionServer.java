package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class DissolutionServer extends AppServer implements Runnable {
    @Override
    public void run() {
        while (true) try {
            socket = serverSocket.accept();
            dataInputStream = new DataInputStream(socket.getInputStream());
            strings = dataInputStream.readUTF().split(" ");
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            //
            for (AppUser appUser : appWheel.appRooms.get(strings[0]).members)
                appUser.kickMember();
            appWheel.appRooms.remove(strings[0]);
            dataOutputStream.writeUTF("success");
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
