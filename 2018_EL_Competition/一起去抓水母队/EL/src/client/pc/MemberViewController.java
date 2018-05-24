package client.pc;

public class MemberViewController extends AppSchedule {
    //一坨事件
    public void clickBack() {
        appWheel.warningStage.show();
        ((WarningViewController) appWheel.appControllers.get("WarningView")).information.setText("确认要退出房间吗？");
    }
}
