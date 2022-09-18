package g3;

import g3.commands.Autonomous;
import g3.subsystems.Controller;
import g3.subsystems.Drive;

public final class Robot {

    public static final Drive Drive = new Drive();
    public static final Controller Controller = new Controller();

    private static final Autonomous autoCommand = new Autonomous();

    private Robot() {}

    public static Autonomous getAutonomousCommand() {
        // An ExampleCommand will run in autonomous
        return autoCommand;
    }
}
