package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

//验证登录并从服务器端同步到客户端
public class LoginServer extends AppServer implements Runnable {
    @Override
    public void run() {
        while (true) try {
            socket = serverSocket.accept();
            dataInputStream = new DataInputStream(socket.getInputStream());
            strings = dataInputStream.readUTF().split(" ");
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            //
            String name = strings[0];
            if (appWheel.appUsers.get(name) == null)
                dataOutputStream.writeUTF("user");
            else if (!appWheel.appUsers.get(name).password.equals(strings[1]))
                dataOutputStream.writeUTF("password");
            else {
                StringBuilder stringBuilder = new StringBuilder("success");
                stringBuilder.append(" ").append(appWheel.appUsers.get(name).score);
                stringBuilder.append(" ").append(appWheel.appUsers.get(name).school);
                for (int i : appWheel.appUsers.get(name).time)
                    stringBuilder.append(" ").append(String.valueOf(i));
                for (int i : appWheel.appUsers.get(name).times)
                    stringBuilder.append(" ").append(String.valueOf(i));
                dataOutputStream.writeUTF(stringBuilder.toString());
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
