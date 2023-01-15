package g3;

import g3.commands.ControllerDrive;
import g3.commands.autonomousCommand;
import g3.subsystems.Drive;
import g3.utils.Controller;
import g3.subsystems.Vision;

final class RobotContainer {

    private final Drive drive = new Drive();
    private final Controller controller = new Controller();
    private final Vision vision = new Vision();
    private final ControllerDrive controllerDriveCommand = new ControllerDrive(controller, drive);
    private final autonomousCommand  autoCommand = new autonomousCommand();

    public autonomousCommand getAutonomousCommand() {
        return autoCommand;
    }

    public Drive getDrive() {
        return drive;
    }

    public Controller getController() {
        return controller;
    }

    public Vision getVision() {
        return vision;
    }

    public ControllerDrive getControllerDriveCommand() {
        return controllerDriveCommand;
    }
}
