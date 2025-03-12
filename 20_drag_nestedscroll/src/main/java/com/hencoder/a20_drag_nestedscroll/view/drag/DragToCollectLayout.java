package com.hencoder.a20_drag_nestedscroll.view.drag;

import android.content.ClipData;
import android.content.Context;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.view.ViewCompat;
import androidx.core.widget.ImageViewCompat;

import com.hencoder.a20_drag_nestedscroll.R;

public class DragToCollectLayout extends RelativeLayout {
    ImageView avatarView;
    ImageView logoView;
    ImageView appIcon;
    LinearLayout collectorLayout;

    OnLongClickListener dragStarter = new OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            ClipData imageData = ClipData.newPlainText("name", v.getContentDescription());
            return ViewCompat.startDragAndDrop(v, imageData, new DragShadowBuilder(v), null, 0);
        }
    };
    OnDragListener dragListener = new CollectListener();

    public DragToCollectLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        avatarView = findViewById(R.id.avatarView);
        logoView = findViewById(R.id.logoView);
        appIcon = findViewById(R.id.appIcon);
        collectorLayout = findViewById(R.id.collectorLayout);


        avatarView.setOnLongClickListener(dragStarter);
        logoView.setOnLongClickListener(dragStarter);
        appIcon.setOnLongClickListener(dragStarter);
        collectorLayout.setOnDragListener(dragListener);
    }

    class CollectListener implements OnDragListener {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            switch (event.getAction()) {
                case DragEvent.ACTION_DROP:
                    // 当其他view 被拖拽时候，所有注册了CollectListener的view都会接收回调，
//                    此时，LinearLayout 注册这个事件，就是为了其他view拖拽松手时候，拿到传递数据，然后放入LinearLayout 中
                    if (v instanceof LinearLayout) {
                        LinearLayout layout = (LinearLayout) v;
                        TextView textView = new TextView(getContext());
                        textView.setTextSize(16);
                        textView.setText(event.getClipData().getItemAt(0).getText());
                        layout.addView(textView);
                    }
                    break;
            }
            return true;
        }
    }

}
