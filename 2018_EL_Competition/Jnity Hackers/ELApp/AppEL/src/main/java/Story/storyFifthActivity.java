package Story;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.lenovo.elapp.R;

/**
 * The last Shot
 *
 * Button to the task-->time countdown && input your own task */
public class storyFifthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.story_activity_fifth);
    }

    public void sendMessage(View view){
        Intent intent =  new Intent(this,storySixthActivity.class);
        startActivity(intent);
    }
}
