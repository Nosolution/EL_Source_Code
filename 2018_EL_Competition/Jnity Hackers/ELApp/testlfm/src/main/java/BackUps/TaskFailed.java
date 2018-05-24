package BackUps;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

//public class TaskFailed extends AppCompatActivity {
//    private volatile boolean IsFailed = false;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        Toast.makeText(this, "asassa", Toast.LENGTH_SHORT).show();
//    }
//}
public class TaskFailed extends BroadcastReceiver {
    public boolean IsFailed = false;

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "任务失败\n请重新设置任务", Toast.LENGTH_SHORT).show();
        IsFailed = true;
    }
}