package g3.utils;

import java.util.ArrayList;
import java.util.List;

public class TasksExecutor {
    private List<Task> taskList = new ArrayList<Task>();

    private Double taskCurrentTime;

    public void addTask(Task task) {
        taskList.add(task);
    }

    public void executeAll() {
        for (Task task:taskList){
            if (!task.initialized) {
                task.timer.reset();
                task.init();

                task.initialized = true;
            }

            if (!task.stopped && !task.isFinished()) {
                taskCurrentTime = task.timer.getTime();

                if (task.lastRunTime + task.runIntervalSec <= taskCurrentTime) {
                    task.execute();

                    task.lastRunTime = taskCurrentTime;
                }
            }
            else {
                task.end();
                taskList.remove(task);
            }
        }
    }

    public void cancel(Task task) {
        taskList.remove(task);
    }

    public void cancelAll() {
        taskList.clear();
    }
}
