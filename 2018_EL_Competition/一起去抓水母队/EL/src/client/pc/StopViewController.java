package client.pc;

import javafx.animation.AnimationTimer;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.util.Date;

public class StopViewController extends AppController {
    //一坨控件
    public Label information;
    public ImageView exit;
    public ImageView cancel;

    //一坨变量
    private long beginTime;
    private long leftTime;
    private AnimationTimer animationTimer = new AnimationTimer() {
        @Override
        public void handle(long now) {
            long goneTime = new Date().getTime() - beginTime;
            long leftSecond = (leftTime - goneTime) / 1000;
            information.setText(leftSecond + "s后退出");
            if(leftTime < goneTime) {
                stop();
                clickExit();
            }
        }
    };
    //一坨事件
    public void clickExit() {
        animationTimer.stop();
        appWheel.stopStage.close();
        appWheel.setScene("HomeView");
        if (((TimingViewController) appWheel.appControllers.get("TimingView")).selfStudy) {
            int time = (int)((TimingViewController) appWheel.appControllers.get("TimingView")).leftTime;
            if (time < 10 * 60 * 1000) {
                appWheel.appUser.time[12] = appWheel.appUser.time[12] + time / 60000;
                appWheel.appUser.time[0] = appWheel.appUser.time[0] + time / 60000;
                appWheel.appUser.times[12] = appWheel.appUser.times[12] + 1;
                appWheel.appUser.times[0] = appWheel.appUser.times[0] + 1;
            }
        }
        ((TimingViewController) appWheel.appControllers.get("TimingView")).status = false;
    }
    public void clickCancel() {
        animationTimer.stop();
        appWheel.stopStage.close();
        ((TimingViewController)appWheel.appControllers.get("TimingView")).clickTime();
    }

    //一坨函数
    void start() {
        beginTime = new Date().getTime();
        leftTime = 10 * 1000;
        animationTimer.start();
    }
}
