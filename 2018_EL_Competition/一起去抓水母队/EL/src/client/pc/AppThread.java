package client.pc;

import javafx.application.Platform;

import java.io.IOException;

public class AppThread implements Runnable {
    private AppUser appUser;
    private AppWheel appWheel;
    AppThread(AppWheel appWheel) {
        this.appUser = appWheel.appUser;
        this.appWheel = appWheel;
    }
    //持续监听
    @Override
    public void run() {
        try {
            while (true){
                String[] strings = appUser.dataInputStream.readUTF().split(" ");
                if (strings[0].equals("lesson")) {
                    appUser.lesson = strings[1];
                    Platform.runLater(() -> appWheel.toTimingView());
                }
                if (strings[0].equals("members")) {
                    Platform.runLater(() -> ((LeaderViewController) appWheel.appControllers.get("LeaderView")).member0.setText(strings[1]));
                    Platform.runLater(() -> ((MemberViewController) appWheel.appControllers.get("MemberView")).member0.setText(strings[1]));
                    if (strings.length > 2) {
                        Platform.runLater(() -> ((LeaderViewController) appWheel.appControllers.get("LeaderView")).member1.setText(strings[2]));
                        Platform.runLater(() -> ((MemberViewController) appWheel.appControllers.get("MemberView")).member1.setText(strings[2]));
                    }
                    else {
                        Platform.runLater(() -> ((LeaderViewController) appWheel.appControllers.get("LeaderView")).member1.setText("empty"));
                        Platform.runLater(() -> ((MemberViewController) appWheel.appControllers.get("MemberView")).member1.setText("empty"));
                    }
                    if (strings.length > 3) {
                        Platform.runLater(() -> ((LeaderViewController) appWheel.appControllers.get("LeaderView")).member2.setText(strings[3]));
                        Platform.runLater(() -> ((MemberViewController) appWheel.appControllers.get("MemberView")).member2.setText(strings[3]));
                    }
                    else {
                        Platform.runLater(() -> ((LeaderViewController) appWheel.appControllers.get("LeaderView")).member2.setText("empty"));
                        Platform.runLater(() -> ((MemberViewController) appWheel.appControllers.get("MemberView")).member2.setText("empty"));
                    }
                    if (strings.length > 4) {
                        Platform.runLater(() -> ((LeaderViewController) appWheel.appControllers.get("LeaderView")).member3.setText(strings[4]));
                        Platform.runLater(() -> ((MemberViewController) appWheel.appControllers.get("MemberView")).member3.setText(strings[4]));
                    }
                    else {
                        Platform.runLater(() -> ((LeaderViewController) appWheel.appControllers.get("LeaderView")).member3.setText("empty"));
                        Platform.runLater(() -> ((MemberViewController) appWheel.appControllers.get("MemberView")).member3.setText("empty"));
                    }
                }
                if (strings[0].equals("kick")) {
                    Platform.runLater(() -> appWheel.setScene("HomeView"));
                    TimingViewController timingViewController = (TimingViewController) appWheel.appControllers.get("TimingView");
                    timingViewController.animationTimer.stop();
                    if (timingViewController.audioClip != null)
                        timingViewController.audioClip.stop();
                    timingViewController.leftTime = timingViewController.leftTime - timingViewController.goneTime;
                    timingViewController.goneTime = 0;
                    timingViewController.status = false;
                    appWheel.appUser.socket.close();
                    appWheel.appUser.roonID = "";
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
