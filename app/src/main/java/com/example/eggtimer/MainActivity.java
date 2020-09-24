package com.example.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    final static int MAX_TIME = 3600;
    TextView timerText;
    SeekBar seekbar;
    Button startButton;
    boolean isRunning = false;
    CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        seekbar = (SeekBar) findViewById(R.id.seekBar);
        timerText = (TextView) findViewById(R.id.timerTextVeiw);
        startButton = (Button) findViewById(R.id.startButton);
        seekbar.setMax(MAX_TIME);

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                updateTimeText(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
    public void updateTimeText (int seconds){
        String time = makeTimeString(seconds);
        timerText.setText(time);
    }
    public String makeTimeString(int seconds){

        return String.format("%02d" +":" +"%02d" , seconds/60,seconds%60);
    }
    public void startClick(View view){
        if (!isRunning) {
            seekbar.setEnabled(false);
            timer = new CountDownTimer(seekbar.getProgress() * 1000, 1000) {
                @Override
                public void onTick(long l) {
                    seekbar.setProgress(seekbar.getProgress() - 1);
                    updateTimeText(seekbar.getProgress());

                }

                @Override
                public void onFinish() {
                    seekbar.setEnabled(true);
                    isRunning=false;
                    startButton.setText("Start");
                    MediaPlayer ring = MediaPlayer.create(MainActivity.this, R.raw.air);
                    ring.start();
                }
            };
            timer.start();
            startButton.setText("Stop!");
            isRunning=true;
        }else if (isRunning) {
            seekbar.setEnabled(true);
            isRunning=false;
            startButton.setText("Start");
            timer.cancel();
        }
    }
}
