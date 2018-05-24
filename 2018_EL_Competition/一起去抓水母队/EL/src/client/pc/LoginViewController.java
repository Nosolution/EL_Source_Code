package client.pc;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LoginViewController extends AppController {
    //一坨控件
    public Label information;
    public TextField name;
    public TextField password;
    public Label back;
    public Label login;

    //一坨事件
    public void clickLogin() throws IOException {
        DataInputStream dataInputStream;
        DataOutputStream dataOutputStream;
        Socket socket;
        String[] strings;
        if (!name.getCharacters().toString().matches("^[a-z0-9A-Z\u4e00-\u9fa5]{3,15}$"))
            information.setText("用户名不存在");
        else if (!password.getCharacters().toString().matches("^[\u0021-\u007f]{6,20}$"))
            information.setText("用户名不存在");
        else {
            socket = new Socket(AppWheel.appHost, 23332);
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataInputStream = new DataInputStream(socket.getInputStream());
            //
            dataOutputStream.writeUTF(name.getCharacters().toString() + " " + password.getCharacters().toString());
            strings = dataInputStream.readUTF().split(" ");
            if (strings[0].equals("user"))
                information.setText("用户名不存在");
            if (strings[0].equals("password"))
                information.setText("密码错误");
            if (strings[0].equals("success")) {
                appWheel.setScene("HomeView");
                appWheel.appUser.name = name.getCharacters().toString();
                appWheel.appUser.password = password.getCharacters().toString();
                appWheel.appUser.score = Integer.parseInt(strings[1]);
                appWheel.appUser.school = strings[2];
                for (int i = 0; i < 14; i++) {
                    appWheel.appUser.time[i] = Integer.parseInt(strings[i + 3]);
                    appWheel.appUser.times[i] = Integer.parseInt(strings[i + 17]);
                }
                password.setText("");
            }
            socket.close();
        }
    }
    public void clickBack() {
        information.setText("");
        appWheel.setScene("LoadingView");
    }
}
