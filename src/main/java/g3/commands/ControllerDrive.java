package g3.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import g3.Robot;

public class ControllerDrive extends CommandBase {

    double[] stickLeft;
    double[] stickRight;

    @Override
    public void execute() {
        stickLeft = Robot.Controller.getStickLeft();
        stickRight = Robot.Controller.getStickRight();

        Robot.Drive.driveTank(Robot.Controller.stickControlFunc(stickLeft[1]), Robot.Controller.stickControlFunc(stickRight[1]));
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
