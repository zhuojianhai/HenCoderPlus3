package com.hencoder.a09_drawing;


import android.app.Activity;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MainActivity extends Activity {

    private String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewParent parent = getWindow().getDecorView().getParent();
        Log.e(TAG, "onCreate: "+parent.toString() );
        TextView tv = findViewById(R.id.tv_title);
        boolean attachedToWindow = tv.isAttachedToWindow();
        tv.post(new Runnable() {
            @Override
            public void run() {

            }
        });

        final LinearLayout linearLayout =null;
        ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int width = linearLayout.getWidth();
            }
        };


        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                Button button = new Button(MainActivity.this);
                getWindow().getWindowManager().addView(button,null);
                Looper.loop();

            }
        }).start();
    }
}
