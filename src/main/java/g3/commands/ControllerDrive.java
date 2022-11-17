package g3.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import g3.models.Controller;
import g3.subsystems.Drive;

public class ControllerDrive extends CommandBase {

    private Controller controller;
    private Drive drive;
    private double[] stickLeft;
    private double[] stickRight;

    @Override
    public void execute() {
        stickLeft = controller.getStickLeft();
        stickRight = controller.getStickRight();

        drive.driveTank(controller.stickControlFunc(stickLeft[1]), controller.stickControlFunc(stickRight[1]));
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
