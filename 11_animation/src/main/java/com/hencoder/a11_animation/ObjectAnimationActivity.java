package com.hencoder.a11_animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hencoder.a11_animation.view.CameraView;
import com.hencoder.a11_animation.view.CircleView;
import com.hencoder.a11_animation.view.ProvinceView;

public class ObjectAnimationActivity extends AppCompatActivity {
    CameraView cameraView;
    ProvinceView provinceView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object_animation);
        cameraView = findViewById(R.id.cameraView);

        provinceView = findViewById(R.id.province);

        /*ObjectAnimator bottomFlipAnimator = ObjectAnimator.ofFloat(cameraView,"bottomFlip",45);
        bottomFlipAnimator.setDuration(1000);

        ObjectAnimator topFlipAnimator = ObjectAnimator.ofFloat(cameraView,"topFlip",-30);
        topFlipAnimator.setDuration(1000);

        ObjectAnimator flipRotationAnimator = ObjectAnimator.ofFloat(cameraView, "flipRotation", 270);
        flipRotationAnimator.setStartDelay(200);
        flipRotationAnimator.setDuration(1000);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(bottomFlipAnimator,flipRotationAnimator,topFlipAnimator);
        animatorSet.setStartDelay(1000);
        animatorSet.start();*/


        PropertyValuesHolder bottomFlipHolder = PropertyValuesHolder.ofFloat("bottomFlip", 30);
        PropertyValuesHolder topFlipHolder = PropertyValuesHolder.ofFloat("topFlip", -30);
        PropertyValuesHolder flipRotationHolder = PropertyValuesHolder.ofFloat("flipRotation", 270);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(cameraView, bottomFlipHolder, topFlipHolder, flipRotationHolder);
        animator.setStartDelay(1000);
        animator.setDuration(1000);
        animator.start();


        ObjectAnimator provinceObjectAnimator = ObjectAnimator.ofObject(provinceView,"province",new ProvinceUtil.ProvinceEvaluator(),"澳门特别行政区");
        provinceObjectAnimator.setDuration(5000);
        provinceObjectAnimator.start();

    }
}
