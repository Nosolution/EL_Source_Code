package client.pc;

import java.util.Date;
import javafx.animation.AnimationTimer;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

public class PauseViewController extends AppController {
    //一坨控件
    public Label time;
    public Text back;
    //一坨变量
    private long beginTime;
    private long goneTime;
    long leftTime;
    private AnimationTimer animationTimer = new AnimationTimer() {
        @Override
        public void handle(long now) {
            goneTime = new Date().getTime() - beginTime;
            long leftMinute = (leftTime - goneTime) / (60 * 1000);
            long leftSecond = ((leftTime - goneTime) % (60 * 1000)) / 1000;
            if(leftSecond < 10)
                time.setText(leftMinute + ":0" + leftSecond);
            else
                time.setText(leftMinute + ":" + leftSecond);
            if(leftTime < goneTime)
                stop();
        }
    };
    //一坨事件
    public void clickBack() {
        animationTimer.stop();
        leftTime = leftTime - goneTime;
        goneTime = 0;
        appWheel.pauseStage.close();
        ((TimingViewController)appWheel.appControllers.get("TimingView")).clickTime();
    }
    void start() {
        beginTime = new Date().getTime();
        animationTimer.start();
    }
}
