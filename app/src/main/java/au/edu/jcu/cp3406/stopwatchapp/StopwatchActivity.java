package au.edu.jcu.cp3406.stopwatchapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;

import java.util.ArrayList;

public class StopwatchActivity extends AppCompatActivity {

    private Stopwatch time;
    private Handler handler;
    private boolean isRunning = false;
    private TextView display;
    private Button start_button;
    private String start_text = "start";
    private String stop_text = "stop";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        display = findViewById(R.id.time);
        start_button = findViewById(R.id.start);

        if (savedInstanceState == null) {
            time = new Stopwatch();
        } else {
            int hours = savedInstanceState.getInt("hours");
            int minutes = savedInstanceState.getInt("minutes");
            int seconds = savedInstanceState.getInt("seconds");
            time = new Stopwatch(hours, minutes, seconds);
            boolean running = savedInstanceState.getBoolean("running");
            display.setText(time.toString());
            if (running) {
                enableStopwatch();
                start_button.setText(stop_text);
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("hours", time.getHours());
        outState.putInt("minutes", time.getMinutes());
        outState.putInt("seconds", time.getSeconds());
        outState.putBoolean("running", isRunning);
    }

    private void enableStopwatch() {

        isRunning = true;
        handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (isRunning) {
                    time.tick();
                    display.setText(time.toString());
                    handler.postDelayed(this, 1000);
                }
            }
        });
    }

    private void disableStopwatch() {
        isRunning = false;
    }

    public void buttonClicked(View view) {

        if (!isRunning) {
            enableStopwatch();
            start_button.setText(stop_text);
        } else {
            disableStopwatch();
            start_button.setText(start_text);
        }
    }

    public void settingClicked(View view) {

    }
}