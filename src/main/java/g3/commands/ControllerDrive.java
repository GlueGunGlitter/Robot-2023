package g3.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import g3.utils.Controller;
import g3.subsystems.Drive;

public class ControllerDrive extends CommandBase {

    private Controller controller;
    private Drive drive;
    private double[] stickLeft;
    private double[] stickRight;
    private int direction = -1;
    private boolean povFlag = false;

    public ControllerDrive(Controller controller, Drive drive) {
        this.controller = controller;
        this.drive = drive;
    }

    @Override
    public void execute() {
        stickLeft = controller.getStickLeft();
        stickRight = controller.getStickRight();

        double velocityMul = (controller.inst.getRightTriggerAxis() < 0.9 && controller.inst.getRightTriggerAxis() < 0.9) ? 1:0.3;

        drive.tankDrive(controller.stickControlFunc(stickLeft[1]*direction*velocityMul), 
                controller.stickControlFunc(stickRight[1]*direction*velocityMul));

        if (controller.inst.getPOV() == 180 && !povFlag) {
            direction *= -1;
            povFlag = true;
        }
        else {
            povFlag = false;
        }

        
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
