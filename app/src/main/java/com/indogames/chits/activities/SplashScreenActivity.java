package com.indogames.chits.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.indogames.chits.R;
import com.indogames.chits.activities.MainActivity;

public class SplashScreenActivity extends AppCompatActivity {

    // constant - delay time before moving to MainActivity
    private static int SPLASH_DELAY = 3000;
    TextView companyName;
    Animation splashAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        splashAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.splash_anim);
        companyName = findViewById(R.id.company);
//        companyName.setAnimation(splashAnimation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_DELAY);
    }
}