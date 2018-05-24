package com.fantasticfour.elcontestproject.WNoise;

import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.fantasticfour.elcontestproject.Instance.Ins_WNoise.WNoisePreset;
import com.fantasticfour.elcontestproject.Instance.Instance;
import com.fantasticfour.elcontestproject.R;

import java.util.List;

public class WNoiseSceneActivity extends AppCompatActivity {
    public static final int s_LoadPresetResultCode = 233;
    public static final int s_LoadPresetCancelCode = 666;
    private int mResultCode;
    private WNoiseScenePresetAdapter mAdapter;
    private WNoisePreset currentPreset;
    private Button cancel;
    private Button done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wnoise_activity_scene);
        ModifyDisplay();
        mResultCode = s_LoadPresetCancelCode;
        setResult(mResultCode);
        List<WNoisePreset> PresetList = Instance.s_WNoiseController.GetPresetList();
        RecyclerView rv = findViewById(R.id.wnoise_rv_preset);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        TextView title = findViewById(R.id.title_middletext);
        title.setText("更改场景");

        currentPreset = Instance.s_WNoiseController.GetCurrentPreset();

        rv.setLayoutManager(layoutManager);
        mAdapter = new WNoiseScenePresetAdapter(PresetList, currentPreset);
        rv.setAdapter(mAdapter);

        cancel = findViewById(R.id.CancelLoadScene);
        done = findViewById(R.id.LoadScene);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Instance.s_WNoiseController.LoadPreset(currentPreset.m_ID);
                mResultCode = s_LoadPresetCancelCode;
                setResult(mResultCode);
                finish();
            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Instance.s_WNoiseController.LoadPreset(mAdapter.getChosenPreset().m_ID);
                mResultCode = s_LoadPresetResultCode;
                setResult(mResultCode);
                finish();
            }
        });


    }

    private void ModifyDisplay(){
        Display display = getWindowManager().getDefaultDisplay();
        WindowManager.LayoutParams params = getWindow().getAttributes();
        Point size = new Point();display.getSize(size);
        params.width = (int) (size.x*0.9);
        params.height = (int) (size.y*0.5);
        getWindow().setAttributes(params);
    }
}
