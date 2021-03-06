package com.example.alex.carduino;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Button classicBtn = (Button) findViewById(R.id.classicBtn);
        Button gyroBtn = (Button) findViewById(R.id.gyroBtn);

        classicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent(MenuActivity.this,MainActivity.class);
            startActivity(intent);
            }
        });

        gyroBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            AlertDialog alertDialog = new AlertDialog.Builder(MenuActivity.this).create();
            alertDialog.setTitle("Gyro");
            alertDialog.setMessage("Handy drehen");
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(MenuActivity.this,MotionSensorActivity.class);
                    startActivity(intent);
                }
            });
            alertDialog.show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        this.finishAffinity();
    }
}
