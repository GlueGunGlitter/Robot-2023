package g3;

import g3.commands.ControllerDrive;
import g3.commands.autonomousCommand;
<<<<<<< HEAD
import g3.subsystems.Controller;
=======
import g3.models.Network;
import g3.models.Controller;
>>>>>>> d9df538070ee40eca7201ccf98b21004469f7888
import g3.subsystems.Drive;

final class RobotContainer {

<<<<<<< HEAD
    private final g3.subsystems.Drive drive = new Drive();
    private final g3.subsystems.Controller controller = new Controller();
    private final ControllerDrive controllerDriveCommand = new ControllerDrive(controller, drive);
    private final autonomousCommand  autoCommand = new autonomousCommand();
=======
    private final Drive drive = new Drive();
    private final Controller controller = new Controller();
    private final autonomousCommand autoCommand = new autonomousCommand();
    private final Network network = new Network(Contsants.TEAM_NUM);

    public Network getNetwork() {
        return network;
    }
>>>>>>> d9df538070ee40eca7201ccf98b21004469f7888

    public autonomousCommand getAutonomousCommand() {
        return autoCommand;
    }

<<<<<<< HEAD
    public g3.subsystems.Drive getDrive() {
        return drive;
    }

    public g3.subsystems.Controller getController() {
        return controller;
    }

    public ControllerDrive getControllerDriveCommand() {
        return controllerDriveCommand;
=======
    public Drive getDrive() {
        return drive;
    }

    public Controller getController() {
        return controller;
>>>>>>> d9df538070ee40eca7201ccf98b21004469f7888
    }
}
