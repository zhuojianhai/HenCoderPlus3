package com.hencoder.a12_bitmap_drawable;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hencoder.a12_bitmap_drawable.adaper.MyRecyclerViewAdaper;
import com.hencoder.a12_bitmap_drawable.decoreview.MyItemViewSpaceDecore;
import com.hencoder.a12_bitmap_drawable.decoreview.MyRecyclerViewDividerDecore;
import com.hencoder.a12_bitmap_drawable.decoreview.MyRecyclerViewStickyHeaderDecoration;

public class RecyclerViewActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    MyRecyclerViewAdaper adaper;
    LinearLayoutManager linearLayoutManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview_activity);
        recyclerView = findViewById(R.id.recyclerview);

        adaper = new MyRecyclerViewAdaper();
        linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        //itemDecoration 效果是叠加的
        recyclerView.addItemDecoration(new MyRecyclerViewDividerDecore());
        recyclerView.addItemDecoration(new MyItemViewSpaceDecore(20));
        recyclerView.addItemDecoration(new MyRecyclerViewStickyHeaderDecoration());
        recyclerView.setAdapter(adaper);


    }
}
