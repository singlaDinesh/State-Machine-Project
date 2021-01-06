package com.setup.statemachine;
import java.util.Date;
import java.util.concurrent.ScheduledFuture;

import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.Trigger;


public class DefaultTaskScheduler implements TaskScheduler {

    @Override
    public ScheduledFuture<?> schedule(Runnable arg0, Trigger arg1) {
        return null;
    }

    @Override
    public ScheduledFuture<?> schedule(Runnable arg0, Date arg1) {
        return null;
    }

    @Override
    public ScheduledFuture<?> scheduleAtFixedRate(Runnable arg0, long arg1) {
        return null;
    }

    @Override
    public ScheduledFuture<?> scheduleAtFixedRate(Runnable arg0, Date arg1, long arg2) {
        return null;
    }

    @Override
    public ScheduledFuture<?> scheduleWithFixedDelay(Runnable arg0, long arg1) {
        return null;
    }

    @Override
    public ScheduledFuture<?> scheduleWithFixedDelay(Runnable arg0, Date arg1, long arg2) {
        return null;
    }

}
