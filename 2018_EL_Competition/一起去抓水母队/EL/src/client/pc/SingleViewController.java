package client.pc;

public class SingleViewController extends AppSchedule {
    //一坨事件
    public void clickBack() {
        appWheel.setScene("HomeView");
    }
    public void clickSelfStudy() {
        appWheel.setScene("TimingView");
        ((TimingViewController) appWheel.appControllers.get("TimingView")).selfStudy = true;
        ((TimingViewController) appWheel.appControllers.get("TimingView")).leftTime = 0;
        ((TimingViewController) appWheel.appControllers.get("TimingView")).clickTime();
        appWheel.appUser.mode = true;
    }
    public void clickUserDefined() {
        appWheel.userDefinedStage.show();
    }
    public void clickSubmit() {
        upAll();
        if (appWheel.appUser.lesson != null) {
            appWheel.setScene("TimingView");
            appWheel.appUser.mode = true;
            ((TimingViewController) appWheel.appControllers.get("TimingView")).selfStudy = false;
            ((TimingViewController) appWheel.appControllers.get("TimingView")).leftTime = appWheel.time.get(appWheel.appUser.lesson);
            ((TimingViewController) appWheel.appControllers.get("TimingView")).clickTime();
            ((PauseViewController) appWheel.appControllers.get("PauseView")).leftTime = 3 * 60 * 1000;
        }
    }
}
