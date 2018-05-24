package client.pc;

import javafx.animation.PathTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

public class HomeViewController extends AppController {
    //一坨控件
    public ImageView setting;
    public ImageView music;
    public ImageView music1;
    public ImageView music2;
    public ImageView music3;
    public ImageView music4;
    public ImageView music5;
    public ImageView music6;
    public Label musicTip;
    public ImageView volume;
    public Slider slider;
    public ImageView statistics;
    public ImageView group;
    public ImageView single;

    //一坨事件
    public void clickSettings() {
        if (music.isVisible())
            if (music1.isVisible())
                clickMusic();
            else if (slider.isVisible())
                clickVolume();
            else {
                music.setVisible(false);
                volume.setVisible(false);
            }
        else {
            music.setVisible(true);
            volume.setVisible(true);
        }
    }
    public void clickMusic() {
        move(music1, 20, 70);
        move(music2, 20, -30);
        move(music3, -23, -5);
        move(music4, 63, -5);
        move(music5, -23, 45);
        move(music6, 63, 45);
    }
    public void clickMusic1() {
        ((TimingViewController) appWheel.appControllers.get("TimingView")).clickMusic();
        ((TimingViewController) appWheel.appControllers.get("TimingView")).clickMusic1();
        clickMusic();
    }
    public void clickMusic2() {
        ((TimingViewController) appWheel.appControllers.get("TimingView")).clickMusic();
        ((TimingViewController) appWheel.appControllers.get("TimingView")).clickMusic2();
        clickMusic();
    }
    public void clickMusic3() {
        ((TimingViewController) appWheel.appControllers.get("TimingView")).clickMusic();
        ((TimingViewController) appWheel.appControllers.get("TimingView")).clickMusic3();
        clickMusic();
    }
    public void clickMusic4() {
        ((TimingViewController) appWheel.appControllers.get("TimingView")).clickMusic();
        ((TimingViewController) appWheel.appControllers.get("TimingView")).clickMusic4();
        clickMusic();
    }
    public void clickMusic5() {
        ((TimingViewController) appWheel.appControllers.get("TimingView")).clickMusic();
        ((TimingViewController) appWheel.appControllers.get("TimingView")).clickMusic5();
        clickMusic();
    }
    public void clickMusic6() {
        ((TimingViewController) appWheel.appControllers.get("TimingView")).clickMusic();
        ((TimingViewController) appWheel.appControllers.get("TimingView")).clickMusic6();
        clickMusic();
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
    public void exitMusic() {
        musicTip.setText("");
    }
    public void clickVolume() {
        if (slider.isVisible())
            slider.setVisible(false);
        else
            slider.setVisible(true);
    }
    public void releaseSlider() {
        ((TimingViewController) appWheel.appControllers.get("TimingView")).slider.setValue(slider.getValue());
    }
    public void clickStatistics() {
        appWheel.setScene("DataView");
        ((DataViewController) appWheel.appControllers.get("DataView")).start();
    }
    public void clickGroup() {
        appWheel.roomStage.show();
    }
    public void clickSingle() {
        appWheel.setScene("SingleView");
        appWheel.appUser.leader = true;
    }

    //一坨函数
    private void move(ImageView imageView, int x, int y) {
        Path path = new Path();
        PathTransition pathTransition = new PathTransition();
        if (imageView.isVisible()) {
            path.getElements().add(new MoveTo(x, y));
            path.getElements().add(new LineTo(20, 20));
            pathTransition.setDuration(Duration.millis(500));
            pathTransition.setNode(imageView);
            EventHandler<ActionEvent> eventEventHandler = e -> imageView.setVisible(false);
            pathTransition.setOnFinished(eventEventHandler);
            pathTransition.setPath(path);
            pathTransition.play();
        } else {
            path.getElements().add(new MoveTo(20, 20));
            path.getElements().add(new LineTo(x, y));
            pathTransition.setDuration(Duration.millis(500));
            pathTransition.setNode(imageView);
            pathTransition.setPath(path);
            imageView.setVisible(true);
            pathTransition.play();
        }
    }
}
