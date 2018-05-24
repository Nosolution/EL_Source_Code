package client.pc;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;



public class LeaderViewController extends AppSchedule {
    //一坨事件
    public void clickBack() {
        appWheel.warningStage.show();
        ((WarningViewController) appWheel.appControllers.get("WarningView")).information.setText("确认要解散房间吗？");
    }
    public void clickKick(MouseEvent mouseEvent) throws IOException{
        DataInputStream dataInputStream;
        DataOutputStream dataOutputStream;
        Socket socket;
        String[] strings;
        Label member;
        switch (((ImageView) mouseEvent.getSource()).getId()) {
            case "kick1":
                member = member1;
                break;
            case "kick2":
                member = member2;
                break;
            case "kick3":
                member = member3;
                break;
            default:
                member = member0;
        }
        if (!member.getText().equals("empty")) {
            socket = new Socket(AppWheel.appHost, 23335);
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataInputStream = new DataInputStream(socket.getInputStream());
            //
            dataOutputStream.writeUTF(appWheel.appUser.roonID + " " + member.getText());
            strings = dataInputStream.readUTF().split(" ");
            if (strings[0].equals("success"))
                socket.close();
        }
    }
    public void clickSubmit() throws IOException {
        upAll();
        if (appWheel.appUser.lesson != null) {
            DataInputStream dataInputStream;
            DataOutputStream dataOutputStream;
            Socket socket;
            String[] strings;
            socket = new Socket(AppWheel.appHost, 23334);
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataInputStream = new DataInputStream(socket.getInputStream());
            //
            dataOutputStream.writeUTF(appWheel.appUser.roonID + " " + appWheel.appUser.lesson);
            strings = dataInputStream.readUTF().split(" ");
            if (strings[0].equals("success"))
                socket.close();
            }
    }
}