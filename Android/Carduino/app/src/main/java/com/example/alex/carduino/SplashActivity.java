package com.example.alex.carduino;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView logoView = (ImageView) findViewById(R.id.logoView);


        Animation animation = AnimationUtils.loadAnimation(this, R.anim.they_see_me_rollin);

        logoView.startAnimation(animation);

        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(3000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{
                    Intent intent = new Intent(SplashActivity.this,MenuActivity.class);
                    startActivity(intent);
                }
            }
        };
        timerThread.start();
    }
}
