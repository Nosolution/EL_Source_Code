package client.pc;

import java.util.HashMap;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

class AppWheel {
    //用户
    AppUser appUser = new AppUser();
    //服务器地址
    static String appHost = "47.100.169.211";
    //存ID和controller的Map
    HashMap<String, AppController> appControllers = new HashMap<>();
    //存ID和scene的Map
    HashMap<String, Scene> scenes = new HashMap<>();
    //存ID和课程时间的Map
    HashMap<String, Integer> time = new HashMap<>();
    //主窗口
    Stage appStage = new Stage();
    //RoomView窗口
    Stage roomStage = new Stage();
    //UserDefinedView窗口
    Stage userDefinedStage = new Stage();
    //WarningView窗口
    Stage warningStage = new Stage();
    //PauseView窗口
    Stage pauseStage = new Stage();
    //StopView窗口
    Stage stopStage = new Stage();

    //加载，完成两个Map
    void load(String name, String resources) {
        try {
            //加载fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource(resources));
            //获取scene
            Pane pane = loader.load();
            Scene scene = new Scene(pane);
            scenes.put(name, scene);
            //获取appController
            AppController appController = loader.getController();
            appController.setAppWheel(this);
            appControllers.put(name, appController);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //主窗口切换scene
    void setScene(String name) {
        appStage.close();
        appStage.setMaximized(false);
        appStage.setScene(scenes.get(name));
        appStage.setMaximized(true);
        appStage.show();
    }

    //group下进入计时界面
    void toTimingView() {
        setScene("TimingView");
        appUser.mode = false;
        ((TimingViewController) appControllers.get("TimingView")).selfStudy = false;
        ((TimingViewController) appControllers.get("TimingView")).leftTime = time.get(appUser.lesson);
        ((TimingViewController) appControllers.get("TimingView")).clickTime();
        ((PauseViewController) appControllers.get("PauseView")).leftTime = 3 * 60 * 1000;
    }
}