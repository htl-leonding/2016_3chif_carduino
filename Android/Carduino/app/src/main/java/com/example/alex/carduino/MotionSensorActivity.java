package com.example.alex.carduino;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.hardware.SensorEventListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.util.Log;

public class MotionSensorActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor sensor;

    ProgressBar verticalProgressBar;
    ProgressBar horizontalProgressBar;
    Switch stbySwitch;

    public static final char UP = 'w';
    public static final char DOWN = 's';
    public static final char DIRECTION = 'd';
    String actProgressVertical;
    String actProgressHorizontal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        verticalProgressBar = (ProgressBar) findViewById(R.id.verticalProgressBar);
        horizontalProgressBar = (ProgressBar) findViewById(R.id.horizontalProgressBar);
        stbySwitch = (Switch) findViewById(R.id.stbySwitch);
        stbySwitch.setChecked(false);
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
            actProgressVertical = UP + String.valueOf((int) (val));
        } else if (event.values[2] < 4.50 && event.values[0] > 9) {
            double val = (5 - event.values[2]) * 186.2;
            if (val > 1024) {
                val = 1024;
            }
            actProgressVertical = DOWN + String.valueOf((int) (val));
        }
        double val = ((event.values[1] + 7.5)*68.266666666666666666666666666667);
        actProgressHorizontal = DIRECTION + String.valueOf((int)val);

        Log.d(String.valueOf(val), "oasch");
        if (stbySwitch.isChecked()) {
            new Thread(new ClientSocket(UP + "0")).start();
        } else {
            new Thread(new ClientSocket(actProgressVertical)).start();
            new Thread(new ClientSocket(actProgressHorizontal)).start();
        }

    }



    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onBackPressed() {
        new Thread(new ClientSocket(UP + "0")).start();
        sensorManager.unregisterListener(this);
        super.onBackPressed();
    }
}
