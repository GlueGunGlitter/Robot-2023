package g3.utils;

public class TaskExecutor implements Runnable{

    private boolean stopped = false;

    private Timer timer = new Timer();
    private Double runIntervalSec = 0d;
    private Double lastRunTime = 0d;
    private Double currentTime;

    private Task task;

    public TaskExecutor(Task task, Double runIntervalSec) {
        this.task = task;

        this.runIntervalSec = runIntervalSec;
    }

    public TaskExecutor(Task task) {
        this.task = task;
    }

    public void run() {
        timer.reset();

        task.init();

        task.loop();
        while (!stopped && !task.loopIsFinished()) {
            currentTime = timer.getTime();

            if ( lastRunTime+runIntervalSec <= currentTime) {
                task.loop();

                lastRunTime = currentTime;
            }
        }

        task.end();
    }

    public void execute() {
        Thread thread = new Thread(this);
        thread.start();
    }

    public void cancel() {
        stopped = true;
    }
}
