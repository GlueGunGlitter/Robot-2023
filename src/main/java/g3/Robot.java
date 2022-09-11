package g3.code;

import g3.code.tasks.Autonomous;
import g3.utils.Task;

public class Robot {

    private Task autoTask = new Autonomous();

    public Robot() {
        configureButtonBindings();
    }

    private void configureButtonBindings() {}

    public Task getAutonomousTask() {
        // An ExampleCommand will run in autonomous
        return autoTask;
    }
}
