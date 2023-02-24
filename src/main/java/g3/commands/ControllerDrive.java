package g3.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import g3.utils.Controller;
import g3.subsystems.Drive;

public class ControllerDrive extends CommandBase {

    private Controller controller;
    private Drive drive;
    private double[] stickLeft;
    private double[] stickRight;
    //private int direction = -1;
    private boolean povFlag = false;
    private double speedVal;
    private int minusOrPlus;
    public ControllerDrive(Controller controller, Drive drive) {
        this.controller = controller;
        this.drive = drive;
    }

    @Override
    public void execute() {
        stickLeft = controller.getStickLeft();
        stickRight = controller.getStickRight();
        double velocityMul = (controller.inst.getRightTriggerAxis() < 0.9 && controller.inst.getRightTriggerAxis() < 0.9) ? 1:0.3;
        speedVal = stickLeft[1]*velocityMul;
        
        //drive.tankDrive(controller.stickControlFunc(Math.pow((stickLeft[1]*velocityMul),1.5)+0.3), 
       //          controller.stickControlFunc(Math.pow((stickRight[1]*velocityMul),1.5)+0.3)); // added function multiplier
        if ((controller.getStickLeft()[1]>0.1 || controller.getStickLeft()[1]<0.1) && (controller.getStickRight()[5]>0.1 || controller.getStickRight()[5]<0.1)){
        drive.tankDrive(stickLeft[1],(stickRight[1])); // added function multiplier
        }
        else if  ((controller.getStickLeft()[1]>0.1 || controller.getStickLeft()[1]<0.1)){
                drive.tankDrive(stickLeft[1],0); // added function multiplier
                }
        
        else if  ((controller.getStickRight()[5]>0.1 || controller.getStickRight()[5]<0.1)){
            drive.tankDrive(0,stickRight[1]); // added function multiplier
            }
        else {
            drive.tankDrive(0,0);
        }
        
    }


            
    

        
    

    @Override
    public boolean isFinished() {
        return false;
    }
}
