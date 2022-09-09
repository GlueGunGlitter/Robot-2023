package g3.main;

import edu.wpi.first.wpilibj.TimedRobot;
import g3.main.gametasks.Autonomous;
import g3.utils.Task;

public class Game extends TimedRobot {

    Task autonomous = new Autonomous();

    @Override
    public void robotInit() {}

    @Override
    public void robotPeriodic() {
    }

    @Override
    public void disabledInit() {}

    @Override
    public void disabledPeriodic() {}

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
    public void testInit() {}

    @Override
    public void testPeriodic() {}
}
