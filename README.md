# StopwatchApp
A timer which user can set the speed.  
  
  Note that all stopwatch functionality is located in the Stopwatch class. The challenge of this prac is to use lifecycle methods in StopwatchActivity to maintain the state of the stopwatch!

## Stopwatch (main funcitionally)
### methods
tick() enables the stopwatch to tick, this method doesn't involve with the actual time, it only adds 1 to the integer seconds.
```
void tick() {
        seconds += 1;
        if (seconds == 60) {
            seconds = 0;
            minutes += 1;
        }
        if (minutes == 60) {
            minutes = 0;
            hours += 1;
        }
    }
```
toString() turns the stopwatch in to the format "hh:mm:ss".
```
public String toString() {
        return String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds);
    }
```
## StopwatchActivity (lifecycle methods to maintain the app state)
### helper method 
helper methods are usually orivate methods in a class that represent self-contained and reuseable functionality.
#### enablestopwatch
enablestopwatch() creates a handler which post() a Runnable that tells stopwatch to tick.
```
private void enableStopwatch() {
        isRunning = true;
        handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (isRunning) {
                    time.tick();
                    display.setText(time.toString());
                    handler.postDelayed(this, speed);
                }
            }
        });
    }
```
### lifecycle methods
In Android studio, activity is recreated after each rotation by default. To prevent the app resets itself, onSaveInstanceState and onCreate has to be implement appropriately.
#### onSaveInstanceState
onSaveInstanceState() saves the instances before placing the activity in a background state.
```
public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("hours", time.getHours());
        outState.putInt("minutes", time.getMinutes());
        outState.putInt("seconds", time.getSeconds());
        outState.putBoolean("running", isRunning);
        outState.putInt("speed",speed);
    }
```
#### onCreate
The onCreate method was called after the rotation, and when the savedInstanceSatae is not null, the onCreate method will create the activity based on the instances saved in savedInstance.
```
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ...
        if (savedInstanceState == null) {
            time = new Stopwatch();
        } else {
            int hours = savedInstanceState.getInt("hours");
            int minutes = savedInstanceState.getInt("minutes");
            int seconds = savedInstanceState.getInt("seconds");
            speed = savedInstanceState.getInt("speed");
            time = new Stopwatch(hours, minutes, seconds);
            boolean running = savedInstanceState.getBoolean("running");
            display.setText(time.toString());
            if (running) {
                enableStopwatch();
                start_button.setText(stop_text);
            }
        }
    }
```
## SettingActivity
### to be update...
