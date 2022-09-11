package g3.code;

import edu.wpi.first.wpilibj.TimedRobot;
import g3.utils.Task;

public class Game extends TimedRobot {

    Robot robot = new Robot();
    Task autonomous = robot.getAutonomousTask();

    @Override
    public void robotInit() {}

    @Override
    public void robotPeriodic() {
    }

    @Override
    public void autonomousInit() {
        autonomous.start();
    }

    @Override
    public void autonomousPeriodic() {}

    @Override
    public void teleopInit() {
        autonomous.cancel();
    }

    /** This function is called periodically during operator control. */
    @Override
    public void teleopPeriodic() {}

    @Override
    public void disabledInit() {}

    @Override
    public void disabledPeriodic() {}

    @Override
    public void testInit() {}

    @Override
    public void testPeriodic() {}
}
