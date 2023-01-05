package g3;

import g3.commands.ControllerDrive;
import g3.commands.autonomousCommand;
import g3.subsystems.Drive;
import g3.utils.Controller;

final class RobotContainer {

    private final g3.subsystems.Drive drive = new Drive();
    private final g3.utils.Controller controller = new Controller();
    private final ControllerDrive controllerDriveCommand = new ControllerDrive(controller, drive);
    private final autonomousCommand  autoCommand = new autonomousCommand();

    public autonomousCommand getAutonomousCommand() {
        return autoCommand;
    }

    public g3.subsystems.Drive getDrive() {
        return drive;
    }

    public g3.utils.Controller getController() {
        return controller;
    }

    public ControllerDrive getControllerDriveCommand() {
        return controllerDriveCommand;
    }
}
