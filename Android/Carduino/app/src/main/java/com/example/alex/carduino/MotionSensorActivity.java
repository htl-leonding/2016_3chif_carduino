package com.example.alex.carduino;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.hardware.SensorEventListener;
import android.os.UserManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MotionSensorActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor sensor;

    private TextView xAxis;
    private TextView yAxis;
    private TextView zAxis;

    public static final char UP = 'w';
    public static final char DOWN = 's';
    public static final char DIRECTION = 'd';
    String actProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        xAxis = (TextView) findViewById(R.id.xOut);
        yAxis = (TextView) findViewById(R.id.yOut);
        zAxis = (TextView) findViewById(R.id.zOut);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        xAxis.setText(String.valueOf(event.values[0]));
        yAxis.setText(String.valueOf(event.values[1]));
        zAxis.setText(String.valueOf(event.values[2]));

        if (event.values[2] > 5.50) {
            actProgress = UP + String.valueOf((int)((event.values[2] - 5) * 204.8));
        }
        else if (event.values[2] < 4.50) {
            actProgress = DOWN + String.valueOf((int)((5 - event.values[2]) * 204.8));
        }
        new Thread(new ClientSocket(actProgress)).start();

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
