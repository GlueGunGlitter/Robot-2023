package g3;

import org.photonvision.targeting.PhotonPipelineResult;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import g3.commands.autonomousCommand;

public class Robot extends TimedRobot {

    RobotContainer robotContainer = new RobotContainer();
    autonomousCommand autonomous = robotContainer.getAutonomousCommand();

    @Override
    public void robotInit() {}

    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();
        PhotonPipelineResult r = robotContainer.getVision().getResults();
        if (r.hasTargets()) {
            System.out.println(robotContainer.getVision().distFromTarget(robotContainer.getVision().getResults().getBestTarget()));
        }
    }

    @Override
    public void autonomousInit() {
        autonomous.schedule();
    }

    @Override
    public void autonomousPeriodic() {}

    @Override
    public void teleopInit() {
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
