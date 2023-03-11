package g3;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.cameraserver.CameraServer;
import g3.commands.AutonomousCommandNobalance;

public class Robot extends TimedRobot {

    RobotContainer robotContainer = new RobotContainer();
    CommandBase autonomous;

    @Override
    public void robotInit() {
        robotContainer.getParallelogram().resetEncoder();
        robotContainer.getGripper().resetEncoder();
        CameraServer.startAutomaticCapture();
        autonomous = robotContainer.getAutonomousCommand();
    }
    
    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();
    }

    @Override
    public void autonomousInit() {
        robotContainer.getParallelogram().resetEncoder();
        robotContainer.getGripper().resetEncoder();
        autonomous.schedule();
        
    }

    @Override
    public void autonomousPeriodic() {}

    @Override
    public void teleopInit() {
        robotContainer.getParallelogram().resetEncoder(); // to remove!!!!
        robotContainer.getGripper().resetEncoder(); // to remove!!!!!
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
