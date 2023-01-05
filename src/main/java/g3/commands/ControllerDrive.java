package g3.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import g3.utils.Controller;
import g3.subsystems.Drive;

public class ControllerDrive extends CommandBase {

    private Controller controller;
    private Drive drive;
    private double[] stickLeft;
    private double[] stickRight;

    public ControllerDrive(Controller controller, Drive drive) {
        this.controller = controller;
        this.drive = drive;
    }

    @Override
    public void execute() {
        stickLeft = controller.getStickLeft();
        stickRight = controller.getStickRight();

        drive.tankDrive(controller.stickControlFunc(stickLeft[1]-stickLeft[0]*0.2), controller.stickControlFunc(stickLeft[1]+stickLeft[0]*0.2));
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
