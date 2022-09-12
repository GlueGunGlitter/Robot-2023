package g3;

import g3.tasks.Autonomous;
import g3.utils.Task;

public class Robot {

    private Task autoTask = new Autonomous();

    public Task getAutonomousTask() {
        // An ExampleCommand will run in autonomous
        return autoTask;
    }
}
