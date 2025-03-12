package com.hencoder.a20_drag_nestedscroll;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nested_scalable_image_view);

        View dragToCollect = findViewById(R.id.drag_to_collect);
        View dragHelper = findViewById(R.id.drag_helper);
        View dragUpAndDown = findViewById(R.id.drag_up_down);


        dragToCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(MainActivity.this,DragLinstenerActivity.class);
                startActivity(intent);
            }
        });
        dragHelper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(MainActivity.this,DragHelperActivity.class);
                startActivity(intent);
            }
        });
        dragUpAndDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(MainActivity.this,DragUpAndDownActivity.class);
                startActivity(intent);
            }
        });

    }
}