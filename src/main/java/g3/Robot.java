package g3;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import g3.commands.AutonomousCommand;

public class Robot extends TimedRobot {

    RobotContainer robotContainer = new RobotContainer();
    AutonomousCommand autonomous = robotContainer.getAutonomousCommand();
    

    @Override
    public void robotInit() {}

    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();
    }

    @Override
    public void autonomousInit() {
        autonomous.schedule();
    }

    @Override
    public void autonomousPeriodic() {}

    @Override
    public void teleopInit() {
        robotContainer.getParallelogram().resetEncoder();
        robotContainer.getGripper().resetEncoder();
        autonomous.cancel();
        robotContainer.getControllerDriveCommand().schedule();
    }

    /** This function is called periodically during operator control. */
    @Override
    public void teleopPeriodic() {
    }

    @Override
    public void disabledInit() {}

    @Override
    public void disabledPeriodic() {}

    @Override
    public void testInit() {
        
    }

    @Override
    public void testPeriodic() {

    }
}
