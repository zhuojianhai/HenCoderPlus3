package com.hencoder.a20_drag_nestedscroll;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * author zjh
 * date 2025/3/12 14:59
 * desc
 */
public class DragHelperActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drag_helper_grid_view);
    }
}
