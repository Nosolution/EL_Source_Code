package client.pc;

import javafx.application.Application;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.Random;

public class App extends Application {
    @Override
    //启动轮子，加载所有scene，显示主窗口
    public void start(Stage primaryStage) throws IOException {
        Random random = new Random();
        String fileName = random.nextInt(11) + 1 + ".png";
        FileChannel inputChannel = new FileInputStream(new File("./src/client/pc/image/timing/background/" + fileName)).getChannel();
        FileChannel outputChannel = new FileOutputStream(new File("./src/client/pc/image/timing/background.png")).getChannel();
        outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
        inputChannel.close();
        outputChannel.close();
        AppWheel appWheel = new AppWheel();
        appWheel.load("LoadingView", "LoadingView.fxml");
        appWheel.load("LoginView", "LoginView.fxml");
        appWheel.load("SigninView", "SigninView.fxml");
        appWheel.load("SchoolView", "SchoolView.fxml");
        appWheel.load("EnrollmentView", "EnrollmentView.fxml");
        appWheel.load("HomeView", "HomeView.fxml");
        appWheel.load("RoomView", "RoomView.fxml");
        appWheel.load("DataView", "DataView.fxml");
        appWheel.load("LeaderView", "LeaderView.fxml");
        appWheel.load("MemberView", "MemberView.fxml");
        appWheel.load("SingleView", "SingleView.fxml");
        appWheel.load("UserDefinedView", "UserDefinedView.fxml");
        appWheel.load("WarningView", "WarningView.fxml");
        appWheel.load("TimingView", "TimingView.fxml");
        appWheel.load("PauseView", "PauseView.fxml");
        appWheel.load("StopView", "StopView.fxml");
        appWheel.time.put("total", 0);
        appWheel.time.put("astronomy", 40 * 60 * 1000 + 1);
        appWheel.time.put("careOfMagicalCreatures", 40 * 60 * 1000 + 2);
        appWheel.time.put("charms", 40 * 60 * 1000 + 3);
        appWheel.time.put("defenceAgainstTheDarkArts", 60 * 60 * 1000 + 4);
        appWheel.time.put("divination", 25 * 60 * 1000 + 5);
        appWheel.time.put("flyingLessons", 25 * 60 * 1000 + 6);
        appWheel.time.put("herbology", 25 * 60 * 1000 + 7);
        appWheel.time.put("historyOfMagic", 60 * 60 * 1000 + 8);
        appWheel.time.put("muggleStudies", 25 * 60 * 1000 + 9);
        appWheel.time.put("potions", 40 * 60 * 1000 + 10);
        appWheel.time.put("transfiguration", 60 * 60 * 1000 + 11);
        appWheel.time.put("selfStudy", 12);
        appWheel.time.put("userDefined", 13);
        //准备主窗口
        appWheel.setScene("LoadingView");
        //最大化窗口
        appWheel.appStage.setMaximized(true);
        //不可改变窗口大小
        appWheel.appStage.setResizable(false);
        //关闭窗口时事件
        appWheel.appStage.setOnCloseRequest(e -> appWheel.appUser.record());
        //显示主窗口
        appWheel.appStage.show();
        //准备RoomView窗口
        appWheel.roomStage.setScene(appWheel.scenes.get("RoomView"));
        //搁置其余窗口
        appWheel.roomStage.initModality(Modality.APPLICATION_MODAL);
        //准备RoomView窗口
        appWheel.userDefinedStage.setScene(appWheel.scenes.get("UserDefinedView"));
        //搁置其余窗口
        appWheel.userDefinedStage.initModality(Modality.APPLICATION_MODAL);
        //准备WarningView窗口
        appWheel.warningStage.setScene(appWheel.scenes.get("WarningView"));
        //搁置其余窗口
        appWheel.warningStage.initModality(Modality.APPLICATION_MODAL);
        //准备PauseView窗口
        appWheel.pauseStage.setScene(appWheel.scenes.get("PauseView"));
        //搁置其余窗口
        appWheel.pauseStage.initModality(Modality.APPLICATION_MODAL);
        //关闭窗口时事件
        appWheel.pauseStage.setOnCloseRequest(e -> ((PauseViewController)appWheel.appControllers.get("PauseView")).clickBack());
        //准备StopView窗口
        appWheel.stopStage.setScene(appWheel.scenes.get("StopView"));
        //搁置其余窗口
        appWheel.stopStage.initModality(Modality.APPLICATION_MODAL);
        //关闭窗口时事件
        appWheel.stopStage.setOnCloseRequest(e -> ((StopViewController)appWheel.appControllers.get("StopView")).clickCancel());
    }
    //main入口，万恶之源
    public static void main(String[] args) {
        launch(args);
    }

}
