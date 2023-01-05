package g3;

import g3.commands.ControllerDrive;
import g3.commands.autonomousCommand;
import g3.subsystems.Controller;
import g3.subsystems.Drive;

final class RobotContainer {

    private final g3.subsystems.Drive drive = new Drive();
    private final g3.subsystems.Controller controller = new Controller();
    private final ControllerDrive controllerDriveCommand = new ControllerDrive(controller, drive);
    private final autonomousCommand  autoCommand = new autonomousCommand();

    public autonomousCommand getAutonomousCommand() {
        // An ExampleCommand will run in autonomous
        return autoCommand;
    }

    public g3.subsystems.Drive getDrive() {
        return drive;
    }

    public g3.subsystems.Controller getController() {
        return controller;
    }

    public ControllerDrive getControllerDriveCommand() {
        return controllerDriveCommand;
    }
}
