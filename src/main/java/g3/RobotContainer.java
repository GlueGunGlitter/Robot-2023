package g3;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import g3.commands.ControllerDrive;
import g3.commands.autonomousCommand;
import g3.subsystems.Drive;
import g3.subsystems.Gripper;
import g3.subsystems.Parallelogram;
import g3.utils.Controller;

final class RobotContainer {

    private final g3.subsystems.Drive drive = new Drive();
    private final g3.utils.Controller controller = new Controller();
    private final Parallelogram parallelogram = new Parallelogram(controller);
    private final Gripper gripper = new Gripper(controller);

    private final ControllerDrive controllerDriveCommand = new ControllerDrive(controller, drive);
    private final autonomousCommand  autoCommand = new autonomousCommand();
    
    private final NetworkTableInstance nt = NetworkTableInstance.getDefault();
    private final NetworkTable sd = nt.getTable("SmartDashboard");
    
    public RobotContainer() {}

    public autonomousCommand getAutonomousCommand() {
        return autoCommand;
    }

    public g3.subsystems.Drive getDrive() {
        return drive;
    }

    public g3.subsystems.Parallelogram getParallelogram() {
        return parallelogram;
    }



    public ControllerDrive getControllerDriveCommand() {
        return controllerDriveCommand;
    }
}
