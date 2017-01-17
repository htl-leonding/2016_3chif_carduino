package com.example.alex.carduino;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.hardware.SensorEventListener;
import android.os.UserManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

public class MotionSensorActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor sensor;

    ProgressBar verticalProgressBar;
    ProgressBar horizontalProgressBar;
    Switch stbySwitch;

    public static final char UP = 'w';
    public static final char DOWN = 's';
    public static final char DIRECTION = 'd';
    String actProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        verticalProgressBar = (ProgressBar) findViewById(R.id.verticalProgressBar);
        horizontalProgressBar = (ProgressBar) findViewById(R.id.horizontalProgressBar);
        stbySwitch = (Switch) findViewById(R.id.stbySwitch);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.values[2] > 5.50 && event.values[0] < 9) {
            double val = (event.values[2] - 5) * 186.2;
            if (val > 1024) {
                val = 1024;
            }
            actProgress = UP + String.valueOf((int) (val));
        } else if (event.values[2] < 4.50 && event.values[0] > 9) {
            double val = (5 - event.values[2]) * 186.2;
            if (val > 1024) {
                val = 1024;
            }
            actProgress = DOWN + String.valueOf((int) (val));
        }
        stbySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    new Thread(new ClientSocket(UP+"0")).start();
                }
                else{
                    new Thread(new ClientSocket(actProgress)).start();
                }
            }
        });


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onBackPressed() {
        sensorManager.unregisterListener(this);
    }
}
