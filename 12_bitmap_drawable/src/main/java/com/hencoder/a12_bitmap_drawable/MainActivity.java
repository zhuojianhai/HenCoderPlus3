package com.hencoder.a12_bitmap_drawable;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.hencoder.a12_bitmap_drawable.view.DrawableViewCustom;
import com.hencoder.a12_bitmap_drawable.view.MaterialEditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    MaterialEditText materialEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        materialEditText = findViewById(R.id.editText);
//        materialEditText.setUseFloatingLabel(false);

        DrawableViewCustom viewById = findViewById(R.id.drawable);
        viewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,RecyclerViewActivity.class);
                startActivity(intent);
            }
        });
    }
}
