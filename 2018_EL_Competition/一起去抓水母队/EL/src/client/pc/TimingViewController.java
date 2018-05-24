package client.pc;

import javafx.animation.AnimationTimer;
import javafx.animation.PathTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

import java.util.Date;

public class TimingViewController extends AppController {
    //一坨控件
    public Label time;
    public ImageView music;
    public ImageView music1;
    public ImageView music2;
    public ImageView music3;
    public ImageView music4;
    public ImageView music5;
    public ImageView music6;
    public Label musicTip;
    public ImageView pause;
    public ImageView stop;
    public ImageView volume;
    public Slider slider;

    //一坨变量
    boolean status = false;
    boolean selfStudy;
    private long beginTime;
    long goneTime;
    long leftTime;
    AnimationTimer animationTimer = new AnimationTimer() {
        @Override
        public void handle(long now) {
            if (selfStudy)
                goneTime = beginTime - new Date().getTime();
            else
                goneTime = new Date().getTime() - beginTime;
            long leftMinute = (leftTime - goneTime) / (60 * 1000);
            long leftSecond = ((leftTime - goneTime) % (60 * 1000)) / 1000;
            if (leftSecond < 10)
                time.setText(leftMinute + ":0" + leftSecond);
            else
                time.setText(leftMinute + ":" + leftSecond);
            if (leftTime < goneTime) {
                stop();
                int time = appWheel.time.get(appWheel.appUser.lesson);
                if (appWheel.appUser.mode) {
                    appWheel.setScene("SingleView");
                    SingleViewController singleViewController = (SingleViewController) appWheel.appControllers.get("SingleView");
                    singleViewController.show("empty", "1");
                    singleViewController.upAll();
                } else if (appWheel.appUser.leader) {
                    appWheel.setScene("LeaderView");
                    LeaderViewController leaderViewController = (LeaderViewController) appWheel.appControllers.get("LeaderView");
                    leaderViewController.show("empty", "1");
                    leaderViewController.upAll();
                } else
                    appWheel.setScene("MemberView");
                appWheel.appUser.time[time % 100] = appWheel.appUser.time[time % 100] + time / 60000;
                appWheel.appUser.time[0] = appWheel.appUser.time[0] + time / 60000;
                appWheel.appUser.times[time % 100] = appWheel.appUser.times[time % 100] + 1;
                appWheel.appUser.times[0] = appWheel.appUser.times[0] + 1;
                if (audioClip != null)
                    audioClip.stop();
                status = false;
            }
        }
    };
    private AudioClip audioClip2 = new AudioClip(getClass().getResource("music/SlytherinCommonRoom.mp3").toString());
    private AudioClip audioClip3 = new AudioClip(getClass().getResource("music/RavenclawCommonRoom.mp3").toString());
    private AudioClip audioClip4 = new AudioClip(getClass().getResource("music/HogwartsLibrary.mp3").toString());
    private AudioClip audioClip5 = new AudioClip(getClass().getResource("music/HufflepuffCommonRoom.mp3").toString());
    private AudioClip audioClip6 = new AudioClip(getClass().getResource("music/GryffindorCommonRoom.mp3").toString());
    AudioClip audioClip = audioClip4;

    //一坨事件
    //时间
    public void clickTime() {
        if (!status) {
            beginTime = new Date().getTime();
            animationTimer.start();
            if (audioClip != null)
                audioClip.play(slider.getValue() / 100);
            musicTip.setText("");
            status = true;
        }
    }
    //音乐相关
    public void clickMusic() {
        move(music1, 32, 150);
        move(music2, 32, -86);
        move(music3, -70, -27);
        move(music4, 134, -27);
        move(music5, -70, 91);
        move(music6, 134, 91);
    }
    public void enterMusic1() {
        musicTip.setText("Learn in silence");
    }
    public void enterMusic2() {
        musicTip.setText("Learn in Slytherin common room");
    }
    public void enterMusic3() {
        musicTip.setText("Learn in Ravenclaw common room");
    }
    public void enterMusic4() {
        musicTip.setText("Learn in Hogwarts library");
    }
    public void enterMusic5() {
        musicTip.setText("Learn in Hufflepuff common room");
    }
    public void enterMusic6() {
        musicTip.setText("Learn in Gryffindor common room");
    }
    public void clickMusic1() {
        chooseMusic("music1");
    }
    public void clickMusic2() {
        chooseMusic("music2");
    }
    public void clickMusic3() {
        chooseMusic("music3");
    }
    public void clickMusic4() {
        chooseMusic("music4");
    }
    public void clickMusic5() {
        chooseMusic("music5");
    }
    public void clickMusic6() {
        chooseMusic("music6");
    }
    public void exitMusic(){
        musicTip.setText("");
    }
    //暂停
    public void clickPause() {
        if (!appWheel.appUser.mode)
            musicTip.setText("房间中无法停止");
        else if (selfStudy)
            musicTip.setText("自习状态不可暂停");
        else if (status) {
            appWheel.pauseStage.show();
            ((PauseViewController) appWheel.appControllers.get("PauseView")).start();
            animationTimer.stop();
            if (audioClip != null)
                audioClip.stop();
            leftTime = leftTime - goneTime;
            goneTime = 0;
            status = false;
        }
    }
    //停止
    public void clickStop() {
        if (appWheel.appUser.mode) {
            appWheel.stopStage.show();
            ((StopViewController) appWheel.appControllers.get("StopView")).start();
            animationTimer.stop();
            if (audioClip != null)
                audioClip.stop();
            leftTime = leftTime - goneTime;
            goneTime = 0;
            status = false;
        } else
            musicTip.setText("房间中无法停止");
    }
    //音量相关
    public void clickVolume() {
        if (slider.isVisible())
            slider.setVisible(false);
        else
            slider.setVisible(true);
    }
    public void releaseSlider() {
        if (audioClip != null && audioClip.isPlaying()) {
            audioClip.stop();
            audioClip.play(slider.getValue() / 100);
        }
    }

    //一坨函数
    private void move(ImageView imageView, int x, int y) {
        Path path = new Path();
        PathTransition pathTransition = new PathTransition();
        if (imageView.isVisible()) {
            path.getElements().add(new MoveTo(x, y));
            path.getElements().add(new LineTo(32, 32));
            pathTransition.setDuration(Duration.millis(500));
            pathTransition.setNode(imageView);
            EventHandler<ActionEvent> eventEventHandler = e -> imageView.setVisible(false);
            pathTransition.setOnFinished(eventEventHandler);
            pathTransition.setPath(path);
            pathTransition.play();
        } else {
            path.getElements().add(new MoveTo(32, 32));
            path.getElements().add(new LineTo(x, y));
            pathTransition.setDuration(Duration.millis(500));
            pathTransition.setNode(imageView);
            pathTransition.setPath(path);
            imageView.setVisible(true);
            pathTransition.play();
        }
    }
    private void chooseMusic(String music){
        clickMusic();
        if(status) {
            if (audioClip != null)
                audioClip.stop();
            changeMusic(music);
            if (audioClip != null)
                audioClip.play(slider.getValue() / 100);
        } else
            changeMusic(music);
    }
    private void changeMusic(String music) {
        if (music.equals("music1"))
            audioClip = null;
        if (music.equals("music2"))
            audioClip = audioClip2;
        if (music.equals("music3"))
            audioClip = audioClip3;
        if (music.equals("music4"))
            audioClip = audioClip4;
        if (music.equals("music5"))
            audioClip = audioClip5;
        if (music.equals("music6"))
            audioClip = audioClip6;
    }
}
