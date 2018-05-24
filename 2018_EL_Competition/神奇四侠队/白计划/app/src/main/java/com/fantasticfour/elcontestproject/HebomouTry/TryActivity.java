package com.fantasticfour.elcontestproject.HebomouTry;

import android.content.ClipData;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewDebug;
import android.widget.Button;

import com.fantasticfour.elcontestproject.R;

public class TryActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hebomou_activity_try);



        RecyclerView rv = findViewById(R.id.hebomou_rv_try);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        final RVTryAdapter rVAdapter = new RVTryAdapter();
        rv.setLayoutManager(llm);
        rv.setAdapter(rVAdapter);


        RVTryItemTouchHelperCallback rVTryItemTouchHelperCallback = new RVTryItemTouchHelperCallback(rVAdapter);
        ItemTouchHelper ith = new ItemTouchHelper(rVTryItemTouchHelperCallback);
        ith.attachToRecyclerView(rv);
        rVAdapter.SetItemTouchHelperCallback(rVTryItemTouchHelperCallback);



        Button btn_Try = findViewById(R.id.hebomou_btn_try);
        btn_Try.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rVAdapter.Reset();
            }
        });
    }
}
