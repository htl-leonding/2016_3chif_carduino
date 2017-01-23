package com.example.alex.carduino;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static final char UP = 'w';
    public static final char DOWN = 's';
    public static final char DIRECTION = 'd';

    ClientSocket client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SeekBar verticalSeekBar = (SeekBar) findViewById(R.id.verticalSeekBar);
        SeekBar horizontalSeekBar = (SeekBar) findViewById(R.id.horizontalSeekBar);
        final Switch stbySwitch = (Switch) findViewById(R.id.stbySwitch);
        final TextView vertext = (TextView) findViewById(R.id.verText);
        final TextView horText = (TextView) findViewById(R.id.horText);

        //client = new ClientSocket(null);


        verticalSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            String actProgress;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(stbySwitch.isChecked()) {
                    if (progress > 1024) {
                        actProgress = UP + String.valueOf(progress - 1024);
                    } else if (progress < 1024) {
                        actProgress = DOWN + String.valueOf(1024 - progress);
                    }
                    vertext.setText(actProgress);
                    new Thread(new ClientSocket(actProgress)).start();
                }
              //  client.sendData(actProgressVertical);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //Unused
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //Unused
            }
        });

        horizontalSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            String actProgress;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                actProgress = DIRECTION + String.valueOf(progress);
                horText.setText(actProgress);
                new Thread(new ClientSocket(actProgress)).start();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //Unused
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //Unused
            }
        });

    }
    @Override
    public void onBackPressed() {
        new Thread(new ClientSocket(UP + "0")).start();
        super.onBackPressed();
    }
}
