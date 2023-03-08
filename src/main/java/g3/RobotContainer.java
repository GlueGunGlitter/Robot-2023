package g3;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.StringPublisher;
import edu.wpi.first.networktables.StringSubscriber;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import g3.commands.ControllerDrive;
import g3.commands.AutonomousCommandBalance;
import g3.commands.AutonomousCommandNobalance;
import g3.subsystems.Drive;
import g3.subsystems.Gripper;
import g3.subsystems.Parallelogram;
import g3.utils.Controller;

final class RobotContainer {

    private final NetworkTableInstance nt = NetworkTableInstance.getDefault();
    private final NetworkTable sd = nt.getTable("SmartDashboard");

    private final g3.subsystems.Drive drive = new Drive();
    private final g3.utils.Controller controller = new Controller();
    private final Parallelogram parallelogram = new Parallelogram(controller, sd);
    private final Gripper gripper = new Gripper(controller, sd);

    private final ControllerDrive controllerDriveCommand = new ControllerDrive(controller, drive);
    private final AutonomousCommandNobalance  autoCommandNoBalance = new AutonomousCommandNobalance(parallelogram, gripper, drive);
    private final AutonomousCommandBalance  autoCommandBalance = new AutonomousCommandBalance(parallelogram, gripper, drive);

    private final GenericEntry autoCommandChoice;

    public RobotContainer() {
        autoCommandChoice = Shuffleboard.getTab("Controlls").add("chooseAutoCommand", "No Balalnce").getEntry();
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
}
