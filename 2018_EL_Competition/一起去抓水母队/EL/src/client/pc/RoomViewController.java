package client.pc;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class RoomViewController extends AppController {
    //一坨控件
    public ImageView establishment;
    public ImageView participation;
    public ImageView roomIDLabel;
    public TextField roomID;
    public Label information;
    public ImageView submit;
    public ImageView back;

    //一坨事件
    public void clickEstablishment() throws IOException {
        appWheel.roomStage.close();
        appWheel.setScene("LeaderView");
        appWheel.appUser.leader = true;
        DataInputStream dataInputStream;
        DataOutputStream dataOutputStream;
        Socket socket;
        String[] strings;
        socket = new Socket(AppWheel.appHost, 23333);
        dataOutputStream = new DataOutputStream(socket.getOutputStream());
        dataInputStream = new DataInputStream(socket.getInputStream());
        //
        dataOutputStream.writeUTF(appWheel.appUser.name);
        strings = dataInputStream.readUTF().split(" ");
        appWheel.appUser.roonID = strings[0];
        ((LeaderViewController) appWheel.appControllers.get("LeaderView")).mode.setText("Room ID：" + strings[0]);
        ((LeaderViewController) appWheel.appControllers.get("LeaderView")).member0.setText(appWheel.appUser.name);
        appWheel.appUser.setIOStream(socket, dataInputStream);
        new Thread(new AppThread(appWheel)).start();
    }
    public void clickParticipation() {
        establishment.setVisible(false);
        participation.setVisible(false);
        roomIDLabel.setVisible(true);
        roomID.setVisible(true);
        information.setVisible(true);
        submit.setVisible(true);
        back.setVisible(true);
    }
    public void clickSubmit() throws IOException {
        DataInputStream dataInputStream;
        DataOutputStream dataOutputStream;
        Socket socket;
        String[] strings;
        if (!roomID.getCharacters().toString().matches("^[0-9]{4}$"))
            information.setText("房间不存在");
        else {
            socket = new Socket("127.0.0.1", 23337);
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataInputStream = new DataInputStream(socket.getInputStream());
            //
            dataOutputStream.writeUTF(roomID.getCharacters().toString() + " " + appWheel.appUser.name);
            strings = dataInputStream.readUTF().split(" ");
            if (strings[0].equals("fail"))
                information.setText("房间不存在");
            if (strings[0].equals("full"))
                information.setText("房间已满");
            if (strings[0].equals("success")) {
                information.setText("");
                appWheel.roomStage.close();
                appWheel.setScene("MemberView");
                appWheel.appUser.leader = false;
                ((MemberViewController) appWheel.appControllers.get("MemberView")).mode.setText("Room ID：" + roomID.getCharacters().toString());
                appWheel.appUser.roonID = roomID.getCharacters().toString();
                appWheel.appUser.setIOStream(socket, dataInputStream);
                new Thread(new AppThread(appWheel)).start();
            }
        }
    }
    public void clickBack() {
        establishment.setVisible(true);
        participation.setVisible(true);
        roomIDLabel.setVisible(false);
        roomID.setVisible(false);
        information.setVisible(false);
        submit.setVisible(false);
        back.setVisible(false);
    }
}
