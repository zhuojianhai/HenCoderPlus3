package com.hencoder.a12_bitmap_drawable;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hencoder.a12_bitmap_drawable.adaper.MyRecyclerViewAdaper;

public class RecyclerViewActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    MyRecyclerViewAdaper adaper;
    LinearLayoutManager linearLayoutManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recyclerView = findViewById(R.id.recyclerview);

        adaper = new MyRecyclerViewAdaper();
        linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adaper);


    }
}
