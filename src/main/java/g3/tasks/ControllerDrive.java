package g3.tasks;

import g3.systems.Controller;
import g3.systems.Drive;
import g3.utils.Task;

public class ControllerDrive extends Task {

    Drive driveSystem;
    Controller controller;

    double[] stickLeft;
    double[] stickRight;

    public ControllerDrive(Drive driveSystem, Controller controller) {
        this.driveSystem = driveSystem;
        this.controller = controller;
    }

    @Override
    public void init() {}

    @Override
    public void execute() {
        stickLeft = controller.getStickLeft();
        stickRight = controller.getStickRight();

        driveSystem.driveTank(controller.stickControlFunc(stickLeft[1]), controller.stickControlFunc(stickRight[1]));
    }

    @Override
    public void end() {}

    @Override
    public boolean isFinished() {
        return false;
    }
}
