package client.pc;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class SchoolViewController extends AppController {
    //一坨控件
    public GridPane pane;
    public ImageView logoG;
    public ImageView logoS;
    public ImageView logoR;
    public ImageView logoH;
    public ImageView backgroundG;
    public ImageView backgroundS;
    public ImageView backgroundR;
    public ImageView backgroundH;

    //一坨事件
    public void enterLogoG() {
        logoG.setScaleX(1.1);
        logoG.setScaleX(1.1);
        backgroundG.setVisible(true);
        pane.setStyle("-fx-background-color: #54171e");
    }
    public void exitLogoG() {
        logoG.setScaleX(1);
        logoG.setScaleX(1);
        backgroundG.setVisible(false);
    }
    public void enterLogoS() {
        logoS.setScaleX(1.1);
        logoS.setScaleX(1.1);
        backgroundS.setVisible(true);
        pane.setStyle("-fx-background-color: #0a442c");
    }
    public void exitLogoS() {
        logoS.setScaleX(1);
        logoS.setScaleX(1);
        backgroundS.setVisible(false);
    }
    public void enterLogoR() {
        logoR.setScaleX(1.1);
        logoR.setScaleX(1.1);
        backgroundR.setVisible(true);
        pane.setStyle("-fx-background-color: #0d1f33");
    }
    public void exitLogoR() {
        logoR.setScaleX(1);
        logoR.setScaleX(1);
        backgroundR.setVisible(false);
    }
    public void enterLogoH() {
        logoH.setScaleX(1.1);
        logoH.setScaleX(1.1);
        backgroundH.setVisible(true);
        pane.setStyle("-fx-background-color: #fdae02");
    }
    public void exitLogoH() {
        logoH.setScaleX(1);
        logoH.setScaleX(1);
        backgroundH.setVisible(false);
    }
    public void clickLogo(MouseEvent mouseEvent) throws IOException {
        DataInputStream dataInputStream;
        DataOutputStream dataOutputStream;
        Socket socket;
        String[] strings;
        ImageView school = (ImageView) mouseEvent.getSource();
        appWheel.appUser.school = school.getId().substring(school.getId().length() - 1);
        socket = new Socket(AppWheel.appHost, 23331);
        dataOutputStream = new DataOutputStream(socket.getOutputStream());
        dataInputStream = new DataInputStream(socket.getInputStream());
        //
        dataOutputStream.writeUTF(appWheel.appUser.name + " " + appWheel.appUser.school);
        strings = dataInputStream.readUTF().split(" ");
        if (strings[0].equals("success"))
            appWheel.setScene("EnrollmentView");
        socket.close();
    }
}
