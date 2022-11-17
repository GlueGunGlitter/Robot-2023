package g3;

import g3.commands.autonomousCommand;
import g3.models.Network;
import g3.models.Controller;
import g3.subsystems.Drive;

final class RobotContainer {

    private final Drive drive = new Drive();
    private final Controller controller = new Controller();
    private final autonomousCommand autoCommand = new autonomousCommand();
    private final Network network = new Network(Contsants.TEAM_NUM);

    public Network getNetwork() {
        return network;
    }

    public autonomousCommand getAutonomousCommand() {
        return autoCommand;
    }

    public Drive getDrive() {
        return drive;
    }

    public Controller getController() {
        return controller;
    }
}
