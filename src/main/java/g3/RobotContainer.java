package g3;

import g3.commands.autonomousCommand;
import g3.subsystems.Controller;
import g3.subsystems.Drive;

final class RobotContainer {

    private final g3.subsystems.Drive Drive = new Drive();
    private final g3.subsystems.Controller Controller = new Controller();
    private final autonomousCommand autoCommand = new autonomousCommand();

    public autonomousCommand getAutonomousCommand() {
        // An ExampleCommand will run in autonomous
        return autoCommand;
    }

    public g3.subsystems.Drive getDrive() {
        return Drive;
    }

    public g3.subsystems.Controller getController() {
        return Controller;
    }
}
