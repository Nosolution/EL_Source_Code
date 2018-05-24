package client.pc;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;


public class SigninViewController extends AppController {
    //一坨控件
    public Label information;
    public TextField name;
    public PasswordField password;
    public Label signin;
    public Label back;
    public RadioButton woman;
    public RadioButton man;

    //一坨事件
    public void clickWoman() {
        if (woman.isSelected() && man.isSelected())
            man.setSelected(false);
    }
    public void clickMan() {
        if (woman.isSelected() && man.isSelected())
            woman.setSelected(false);
    }
    public void clickSignin() throws IOException {
        DataInputStream dataInputStream;
        DataOutputStream dataOutputStream;
        Socket socket;
        String[] strings;
        if (name.getCharacters().toString().length() < 3)
            information.setText("用户名过短");
        else if (name.getCharacters().toString().length() > 15)
            information.setText("用户名过长");
        else if (!name.getCharacters().toString().matches("^[a-z0-9A-Z\u4e00-\u9fa5]+$"))
            information.setText("换个用户名吧");
        else if (password.getCharacters().toString().length() < 6)
            information.setText("密码过短");
        else if (password.getCharacters().toString().length() > 20)
            information.setText("密码过长");
        else if (!password.getCharacters().toString().matches("^[\u0021-\u007f]+$"))
            information.setText("换个密码吧");
        else if (!woman.isSelected() && !man.isSelected())
            information.setText("未选择性别");
        else {
            socket = new Socket(AppWheel.appHost, 23330);
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataInputStream = new DataInputStream(socket.getInputStream());
            //
            dataOutputStream.writeUTF(name.getCharacters().toString() + " " + password.getCharacters().toString());
            strings = dataInputStream.readUTF().split(" ");
            if (strings[0].equals("fail"))
                information.setText("用户名已存在");
            if (strings[0].equals("success")) {
                appWheel.setScene("SchoolView");
                appWheel.appUser.name = name.getCharacters().toString();
                appWheel.appUser.password = password.getCharacters().toString();
                name.setText("");
                password.setText("");
                man.setSelected(false);
                woman.setSelected(false);
            }
            socket.close();
        }
    }
    public void clickBack() {
        information.setText("");
        appWheel.setScene("LoadingView");
    }
}
