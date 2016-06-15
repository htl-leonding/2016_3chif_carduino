package com.example.alex.carduino;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.hardware.SensorEventListener;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class MotionSensorActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor sensor;

    ProgressBar verticalProgressBar;
    ProgressBar horizontalProgressBar;

    public static final char UP = 'w';
    public static final char DOWN = 's';
    public static final char DIRECTION = 'd';
    String actProgressSpeed;
    String actProgressDirection;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        verticalProgressBar = (ProgressBar) findViewById(R.id.verticalProgressBar);
        verticalProgressBar.setProgress(0);
        horizontalProgressBar = (ProgressBar) findViewById(R.id.horizontalProgressBar);
        horizontalProgressBar.setProgress(0);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);

    }

       @Override
    public void onSensorChanged(SensorEvent event) {
           double val = 0;

        if (event.values[2] > 5.50 && event.values[0] < 9) {
             val = (event.values[2] - 5) * 186.2;
            if (val > 1024){
                val = 1024;
            }
            actProgressSpeed = UP + String.valueOf((int)(val));
            verticalProgressBar.setProgress((int)val);
        }
        else if (event.values[2] < 4.50 && event.values[0] > 9) {
            val = (5 - event.values[2]) * 186.2;
            if (val > 1024){
                val = 1024;
            }
            actProgressSpeed = DOWN + String.valueOf((int)(val));
            verticalProgressBar.setProgress((int)val);
        }
        new Thread(new ClientSocket(actProgressSpeed)).start();
           if (event.values[1] >= 5) {
               val = 1024 - (event.values[1] + 5) * 102.4;
               if (val < 0){
                   val = 0;
               }
               actProgressDirection = DIRECTION + String.valueOf((int)(val));
               horizontalProgressBar.setProgress((int) val);
           }
           else if (event.values[1] < 5) {
               val = 1024 - (event.values[1] + 5) * 102.4;
               if (val > 1024){
                   val = 1024;
               }
               actProgressDirection = DIRECTION + String.valueOf((int)(val));
               horizontalProgressBar.setProgress((int) val);
           }
           new Thread(new ClientSocket(actProgressDirection)).start();


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onStop() {
        sensorManager.unregisterListener(this);
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        sensorManager.unregisterListener(this);
        super.onBackPressed();
    }


}
