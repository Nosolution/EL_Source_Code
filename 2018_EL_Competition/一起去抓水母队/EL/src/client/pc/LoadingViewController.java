package client.pc;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

public class LoadingViewController extends AppController {
    //一坨控件
    public GridPane pane;
    public ImageView signin;
    public ImageView login;

    //一坨变量
    private boolean status = false;

    //一坨事件
    public void showThings() {
        if (!status) {
            status = true;
            showSentence();
            showImageView();
        }
    }
    public void clickSignin() {
        appWheel.setScene("SigninView");
    }
    public void clickLogin() {
        appWheel.setScene("LoginView");
    }

    //一坨函数
    private void showSentence() {
        //准备第一句话
        Label sentence1 = new Label("- We are now at the King's Cross Station.\n  You can, fo example, get on a train.");
        sentence1.setWrapText(true);
        pane.add(sentence1, 1, 1);
        FadeTransition fadeTransition1 = new FadeTransition(Duration.millis(2000));
        fadeTransition1.setFromValue(0);
        fadeTransition1.setToValue(1.0);
        fadeTransition1.setNode(sentence1);
        //准备第二句话
        Label sentence2 = new Label("- Where would it take me to?");
        sentence2.setWrapText(true);
        pane.add(sentence2, 1, 2);
        FadeTransition fadeTransition2 = new FadeTransition(Duration.millis(2000));
        fadeTransition2.setFromValue(0);
        fadeTransition2.setToValue(1.0);
        fadeTransition2.setNode(sentence2);
        //准备第三句话
        Label sentence3 = new Label("- Forward.");
        sentence3.setWrapText(true);
        pane.add(sentence3, 1, 3);
        FadeTransition fadeTransition3 = new FadeTransition(Duration.millis(2000));
        fadeTransition3.setFromValue(0);
        fadeTransition3.setToValue(1.0);
        fadeTransition3.setNode(sentence3);
        //开始动画
        SequentialTransition sequentialTransition = new SequentialTransition(fadeTransition1, fadeTransition2, fadeTransition3);
        sequentialTransition.play();
    }
    private void showImageView() {
        //准备sigin
        FadeTransition fadeTransition4 = new FadeTransition(Duration.millis(4000));
        fadeTransition4.setFromValue(0);
        fadeTransition4.setToValue(1.0);
        fadeTransition4.setNode(signin);
        //准备login
        FadeTransition fadeTransition5 = new FadeTransition(Duration.millis(4000));
        fadeTransition5.setFromValue(0);
        fadeTransition5.setToValue(1.0);
        fadeTransition5.setNode(login);
        //开始动画
        signin.setVisible(true);
        login.setVisible(true);
        ParallelTransition parallelTransition = new ParallelTransition(fadeTransition4, fadeTransition5);
        parallelTransition.play();
    }
}
