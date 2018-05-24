package client.pc;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

public class UserDefinedViewController extends AppController{
    //一坨控件
    public ComboBox time;
    public Label submit;
    public Label cancel;

    //一坨事件
    public void clickSubmit() {
        if (time.getValue() != null) {
            appWheel.setScene("TimingView");
            appWheel.appUser.mode = true;
            appWheel.time.replace("userDefined", Integer.parseInt(((String) time.getValue()).substring(0, 2)) * 60 * 1000 + 13);
            appWheel.userDefinedStage.close();
            appWheel.appUser.lesson = "userDefined";
            ((TimingViewController) appWheel.appControllers.get("TimingView")).selfStudy = false;
            ((TimingViewController) appWheel.appControllers.get("TimingView")).leftTime = appWheel.time.get(appWheel.appUser.lesson);
            ((TimingViewController) appWheel.appControllers.get("TimingView")).clickTime();
            ((PauseViewController) appWheel.appControllers.get("PauseView")).leftTime = 3 * 60 * 1000;
        }
    }
    public void clickCancel() {
        appWheel.userDefinedStage.close();
    }
}
