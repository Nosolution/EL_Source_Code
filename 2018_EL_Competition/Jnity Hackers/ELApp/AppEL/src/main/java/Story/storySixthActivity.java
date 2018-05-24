package Story;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.lenovo.elapp.R;

/**
 * Do task(time Countdown && Input own task && music)
 * button(Next!)-->Done
 * */
public class storySixthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.story_activity_sixth);
    }

    /**Called when the user clicks the Send button*/
    public void sendMessage(View view){
        Intent intent =  new Intent(this,storySeventhActivity.class);
        startActivity(intent);
    }
}
