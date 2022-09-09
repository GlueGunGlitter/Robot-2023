package g3.utils;

import java.util.ArrayList;

public class Scheduler {

    /*private static Scheduler instance = new Scheduler();
    private static Timer timer;
    private static double currentTime = 0;

    private ArrayList<Task> schedule;

    public Scheduler() {
        schedule = new ArrayList<Task>();
    }

    public void startTimer() {
        timer = new Timer();
    }

    public void addTask(Task task) {
        schedule.add(task);
        task.init();
    }

    public static void executeAll() {
        try {
            currentTime = timer.getTime();
        }
        catch (NullPointerException e) {
            throw new NullPointerException("timer wasn't started. run Scheduler.startTimer() before Scheduler.executeAll");
        }

        for (Task task : schedule) {

            if ( task.lastRunTime+task.runIntervalSec <= currentTime || task.lastRunTime == 0d) {
                task.run();

                if (task.isFinished()) {
                    task.finish();
                    stop(task);
                }

                task.lastRunTime = currentTime;
            }
        }
    }

    public void stop(Task task) {
        task.finish();
        schedule.remove(task);
    }

    public void killAll() {
        schedule = new ArrayList<Task>();
    }

    public double getTime() {
        return currentTime;
    }

    public Scheduler getInstance() {
        return instance;
    }*/
}
