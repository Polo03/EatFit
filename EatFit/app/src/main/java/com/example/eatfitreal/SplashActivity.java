package com.example.eatfitreal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.motion.widget.TransitionAdapter;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.transition.TransitionManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 1000; // tiempo de duración de la pantalla de inicio en milisegundos

    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final MotionLayout motionLayout = findViewById(R.id.motion_layout);
        final ImageView imageView = findViewById(R.id.icono);

        motionLayout.setTransitionListener(new TransitionAdapter() {
            @Override
            public void onTransitionCompleted(MotionLayout motionLayout, int currentId) {
                Intent intent = new Intent(SplashActivity.this, Login.class);
                startActivity(intent);
                finish();
            }
        });
        // Método que tarda unos segundos en realizarse la animación.
        motionLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                motionLayout.transitionToEnd(); // Se realiza la animación del objeto
            }
        }, SPLASH_TIME_OUT);
    }

}