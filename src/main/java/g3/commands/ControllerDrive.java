package g3.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import g3.utils.Controller;
import g3.subsystems.Drive;

public class ControllerDrive extends CommandBase {

    private Controller controller;
    private Drive drive;
    private double[] velLeft;
    private double[] velRight;
    private int direction = -1;
    public ControllerDrive(Controller controller, Drive drive) {
        this.controller = controller;
        this.drive = drive;
    }

    @Override
    public void execute() {
        velLeft = controller.getStickLeft();
        velRight = controller.getStickRight();


        double velocityMul = (controller.inst.getRightBumper()  && controller.inst.getLeftBumper()) ? 0.12:1;
        
        drive.tankDrive(
            (velLeft[1]<0.1 && velLeft[1]>-0.1) ? 0:Math.pow((velLeft[1]),4)*((velLeft[1]<0) ? -1:1)*velocityMul*direction+(0.07*direction*((velLeft[1]<0) ? -1:1)), 
            (velRight[1]<0.1 && velRight[1]>-0.1) ? 0:Math.pow((velRight[1]),4)*((velRight[1]<0) ? -1:1)*velocityMul*direction+(0.07*direction*((velRight[1]<0) ? -1:1)));
            
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
