package Story;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.lenovo.elapp.R;

/**
 * Do task(time Countdown && Input own task)
 * button(Next!)-->The last shot
 * */
public class storyFourthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.story_activity_fourth);
    }

    /**Called when the user clicks the Send button*/
    public void sendMessage(View view){
        Intent intent =  new Intent(this,storyFifthActivity.class);
        startActivity(intent);
    }
}
