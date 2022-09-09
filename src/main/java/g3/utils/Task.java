package g3.utils;

public abstract class Task implements Runnable{

    private boolean stopped = false;

    private Timer timer = new Timer();
    private Double runIntervalSec = 0d;
    private Double lastRunTime = 0d;
    private Double currentTime;

    public Task(Double runIntervalSec) {
        this.runIntervalSec = runIntervalSec;
    }

    public Task() {}

    public void start() {
        Thread thread = new Thread(this);
        thread.start();
    }

    public void run() {
        timer.reset();

        init();

        execute();
        while (!stopped && !isFinished()) {
            currentTime = timer.getTime();

            if ( lastRunTime+runIntervalSec <= currentTime) {
                execute();

                lastRunTime = currentTime;
            }
        }

        end();
    }

    public abstract void init();

    public abstract void execute();

    public abstract void end();

    public abstract boolean isFinished();

    public void cancel() {
        stopped = true;
    }
}
