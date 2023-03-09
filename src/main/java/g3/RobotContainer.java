package g3;

import org.apache.commons.lang3.ObjectUtils.Null;

import com.fasterxml.jackson.annotation.JsonTypeInfo.None;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.StringPublisher;
import edu.wpi.first.networktables.StringSubscriber;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import g3.commands.ControllerDrive;
import g3.commands.AutonomousCommandBalance;
import g3.commands.AutonomousCommandNobalance;
import g3.subsystems.Drive;
import g3.subsystems.Gripper;
import g3.subsystems.Parallelogram;
import g3.subsystems.climbArm;
import g3.utils.Controller;

final class RobotContainer {

    private final NetworkTableInstance nt = NetworkTableInstance.getDefault();
    private final NetworkTable sd = nt.getTable("SmartDashboard");

    private final g3.subsystems.Drive drive = new Drive();
    private final g3.utils.Controller controller = new Controller();
    private final Parallelogram parallelogram = new Parallelogram(controller, sd);
    private final Gripper gripper = new Gripper(controller, sd);
    private final climbArm climbArm = new climbArm(controller, sd);

    private final ControllerDrive controllerDriveCommand = new ControllerDrive(controller, drive);
    private final AutonomousCommandNobalance  autoCommandNoBalance = new AutonomousCommandNobalance(parallelogram, gripper, drive);
    private final AutonomousCommandBalance  autoCommandBalance = new AutonomousCommandBalance(parallelogram, gripper, drive);


    private POVButton d_UpPOV = new POVButton(controller.inst, 0);
    private final GenericEntry autoCommandChoice;

    public RobotContainer() {
        autoCommandChoice = Shuffleboard.getTab("Controlls").add("chooseAutoCommand", "No Balalnce").getEntry();
        configureButtonBindings();

    }


    public CommandBase getAutonomousCommand() {
        return (autoCommandChoice.get().getString() == "Balance") ? autoCommandBalance : autoCommandNoBalance;
    }



    public g3.subsystems.Drive getDrive() {
        return drive;
    }

    public g3.subsystems.Parallelogram getParallelogram() {
        return parallelogram;
    }

    

    public Gripper getGripper(){
        return gripper;
    }

    public ControllerDrive getControllerDriveCommand() {
        return controllerDriveCommand;
    }

    public Command resetAllEncoders(){ // needs to be moved, not supposed to be working
        getParallelogram().resetEncoder();
        getGripper().resetEncoder();
        return null;
    }
    private void configureButtonBindings(){
        d_UpPOV.toggleOnTrue(resetAllEncoders());
    }
}
