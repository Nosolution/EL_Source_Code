package client.pc;

class AppController {
    //轮子，轮子！
    AppWheel appWheel;
    //每个控制器接口，用以绑定轮子
    void setAppWheel(AppWheel appWheel) {
        this.appWheel = appWheel;
    }
}
