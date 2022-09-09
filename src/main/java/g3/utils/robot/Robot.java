package g3.utils.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import g3.utils.TaskExecutor;

public class Robot extends TimedRobot {

    private TaskExecutor autonomous;
    private TaskExecutor teleop;
    private TaskExecutor periodic;

    @Override
    public void robotInit() {
        autonomous = new TaskExecutor(new Autonomous());
        teleop = new TaskExecutor(new Teleop());
        periodic = new TaskExecutor(new Periodic());
    }

    @Override
    public void robotPeriodic() {
    }

    @Override
    public void disabledInit() {}

    @Override
    public void disabledPeriodic() {}

    @Override
    public void autonomousInit() {
        autonomous.execute();
    }

    @Override
    public void autonomousPeriodic() {}

    @Override
    public void teleopInit() {
        autonomous.cancel();
        teleop.execute();
    }

    /** This function is called periodically during operator control. */
    @Override
    public void teleopPeriodic() {}

    @Override
    public void testInit() {}

    @Override
    public void testPeriodic() {}
}
