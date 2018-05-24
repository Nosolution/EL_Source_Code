package client.pc;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class WarningViewController extends AppController {
    //一坨控件
    public Label information;
    public ImageView exit;
    public ImageView cancel;

    //一坨事件
    public void clickExit() throws IOException {
        DataInputStream dataInputStream;
        DataOutputStream dataOutputStream;
        Socket socket;
        String[] strings;
        appWheel.warningStage.close();
        appWheel.setScene("HomeView");
        if (appWheel.appUser.leader)
            socket = new Socket(AppWheel.appHost, 23336);
        else
            socket = new Socket(AppWheel.appHost, 23338);
        dataOutputStream = new DataOutputStream(socket.getOutputStream());
        dataInputStream = new DataInputStream(socket.getInputStream());
        //
        dataOutputStream.writeUTF(appWheel.appUser.roonID + " " + appWheel.appUser.name);
        strings = dataInputStream.readUTF().split(" ");
        if (strings[0].equals("success"))
            socket.close();
        appWheel.appUser.roonID = "";
    }
    public void clickCancel() {
        appWheel.warningStage.close();
    }
}
