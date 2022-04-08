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
The settingActivity gets user input as the speed for the timer.
### SettingActivity
#### SETTING_REQUEST
In the SettingActivity, a unique integer is required as a symbolic constant. In this case, the symbolic is named SETTING_REQUEST.
```
public class SettingsActivity extends AppCompatActivity {

    public static int SETTINGS_REQUEST = 1234;
    private EditText input_speed;
    ...
}
```
#### doneClick
In doneClick, a intent is created to hold information to return as a result from SettingActivity.  
setResult() is used to set the result status(RESULT_OK) amd the result itself.
finish() will programmatically terminates an activity.
```
public void doneClicked(View view) {
        String text = input_speed.getText().toString();
        int speed = Integer.parseInt(text);

        Intent data = new Intent();
        data.putExtra("speed", speed);
        setResult(RESULT_OK, data);
        finish();
    }
```
#### connect settings activity to main activity
Back in StopwatchActivity(main activity), the following methods are added to link the settings activity with the main activity.
##### settingClicked
In the settingClicked the startActivityForResult() will starts the SettingsActivity and gets the result back.
```
public void settingClicked(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivityForResult(intent, SettingsActivity.SETTINGS_REQUEST);
    }
```
##### onActivityResult
The onActivityResult will grabs the content choosen by checking the request code(SETTING_REQUEST) and the result code(RESULT_OK).
```
protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SettingsActivity.SETTINGS_REQUEST){
            if (resultCode == RESULT_OK){
                if (data != null){
                    speed = data.getIntExtra("speed", 1000);
                }
            }
        }
    }
```
** Noted that startActivityForResult and onActivityResult has been both deprecated after androidx.activity:activity:1.2.0-alpha02.
