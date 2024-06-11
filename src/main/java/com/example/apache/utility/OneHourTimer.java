package com.example.apache.utility;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Timer;
import java.util.TimerTask;

@Component
public class OneHourTimer {

    private static final long ONE_HOUR_IN_MILLIS = 60 * 60 * 1000;

    private boolean flag;

    public OneHourTimer() {
        this.flag = true; // Set the flag to true initially
    }

    @PostConstruct
    public void startTimer() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                flag = true;
                timer.cancel(); // Stops the timer
            }
        };

        // Schedule the task to run after one hour
        timer.schedule(task, ONE_HOUR_IN_MILLIS);
    }

    public boolean isFlag() {
        return flag;
    }

    public void resetFlag() {
        this.flag = false;
        startTimer(); // Restart the timer
    }
}
