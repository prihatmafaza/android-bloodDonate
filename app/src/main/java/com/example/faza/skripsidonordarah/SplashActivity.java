package com.example.faza.skripsidonordarah;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {
    private ImageView iv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        iv = (ImageView) findViewById(R.id.splash);
        Animation anim = AnimationUtils.loadAnimation(this,R.anim.trans);
        iv.startAnimation(anim);
        final Intent intn = new Intent(this, halamanLogin.class);
        Thread timer = new Thread(){
            public void run(){
                try {
                    sleep(5000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                finally {
                    startActivity(intn);
                    finish();
                }
            }
        };
        timer.start();
    }
}
