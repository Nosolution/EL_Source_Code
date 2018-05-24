package Managers;

import android.os.Handler;
import android.os.Message;

//多线程进行正计时的模板
//handler处理交互信息，message的arg1属性为正计时的时间，必须要获取String.valueOf才可以用（本身是int型）
public class CountingUp implements Runnable {
    private long count = 0;
    private boolean ifStop = true;
    private Handler handler;
    private int MaxDelay;
    Message msg;

    private CountingUp(Handler handler, int minutes) {

        this.handler = handler;
        this.MaxDelay = minutes;
    }

    public static CountingUp getCountingUp(Handler handler, int minutes) {
        return new CountingUp(handler, minutes);
    }

    public int getMaxDelay() {
        return this.MaxDelay;
    }

    public void setInterval(int minutes) {
        this.MaxDelay = minutes;
    }

    @Override
    public void run() {
        count = 0;
        while (ifStop && count <= MaxDelay) {
            count++;
            msg = new Message();
            msg.arg1 = Math.toIntExact(count);
            handler.sendMessage(msg);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}

