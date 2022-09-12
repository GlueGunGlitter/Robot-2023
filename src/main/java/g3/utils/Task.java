package g3.utils;

public abstract class Task{

    public boolean initialized = false;
    public boolean stopped = false;
    public Double runIntervalSec = 0d;
    public Double lastRunTime = 0d;

    public Task(Double runIntervalSec) {
        this.runIntervalSec = runIntervalSec;
    }

    public Task() {
    }

    public abstract void init();

    public abstract void execute();

    public abstract void end();

    public abstract boolean isFinished();

    public void cancel() {
        stopped = true;
    }
}
