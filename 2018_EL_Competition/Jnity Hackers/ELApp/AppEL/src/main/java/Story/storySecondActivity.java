package Story;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.lenovo.elapp.R;

/**
 * Do task(time Countdown)
 * button(Next!)-->The second shot
 * */
public class storySecondActivity extends AppCompatActivity {

//    private TextView countdownText;
//    private Button countdownButton;
//
//    private CountDownTimer counteDownTimer;
//    private long timeLeftInMillisecond = 600000;//10 mins
//    private boolean timeRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.story_activity_second);

//        countdownButton = findViewById(R.id.countdown_button);
//        countdownText = findViewById(R.id.countdown_text);
    }


    /**Called when the user clicks the Send button*/
    public void sendMessage(View view){
        Intent intent =  new Intent(this,storyThirdActivity.class);
        startActivity(intent);
    }


}
