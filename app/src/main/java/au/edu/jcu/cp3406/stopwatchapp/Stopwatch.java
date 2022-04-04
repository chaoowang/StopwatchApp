package au.edu.jcu.cp3406.stopwatchapp;

import androidx.annotation.NonNull;

import java.util.Locale;

public class Stopwatch {
    private int hours, minutes, seconds;

    Stopwatch() {
        hours = minutes = seconds = 0;
    }

    void tick() {
        //TODO
    }

    @NonNull
    @Override
    public String toString() {
        return String.format(Locale.getDefault(),"%02d:%02d:%02d", hours,minutes,seconds);
    }
}
