package g3;

import g3.commands.ControllerDrive;
import g3.commands.AutonomousCommand;
import g3.subsystems.Drive;
import g3.utils.Controller;
import g3.subsystems.Vision;
import g3.commands.BalanceOnRamp;

final class RobotContainer {

    private final Drive drive = new Drive();
    private final Controller controller = new Controller();
    private final Vision vision = new Vision();

    private final ControllerDrive controllerDriveCommand = new ControllerDrive(controller, drive);
    private final AutonomousCommand  autoCommand = new AutonomousCommand();
    private final BalanceOnRamp  balanceCommand = new BalanceOnRamp(drive);

    public AutonomousCommand getAutonomousCommand() {
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

    public BalanceOnRamp getBalanceCommand() {
        return balanceCommand;
    }
}
