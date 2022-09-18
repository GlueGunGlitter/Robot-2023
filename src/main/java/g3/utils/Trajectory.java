package g3.utils;

import com.pathplanner.lib.PathPlanner;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import g3.Robot;
import g3.subsystems.Drive;

public class Trajectory {

    private static edu.wpi.first.math.trajectory.Trajectory trajectory;

    public static final double kRamseteB = 2;
    public static final double kRamseteZeta = 0.7;

    public static SequentialCommandGroup getTrajectoryCommand(String fileName) {
        trajectory = getTrajectoryFromFile(fileName);
        return getCommand(trajectory);
    }


    public static edu.wpi.first.math.trajectory.Trajectory getTrajectoryFromFile(String fileName) {
        return PathPlanner.loadPath(fileName, 1/ Drive.kvVoltSecondsPerMeter, Drive.kMaxAccelerationMetersPerSecondSquared);
    }

    public static SequentialCommandGroup getCommand(edu.wpi.first.math.trajectory.Trajectory trajectory) {
        RamseteCommand ramseteCommand =
                new RamseteCommand(
                        trajectory,
                        Robot.Drive::getPose,
                        new RamseteController(kRamseteB, kRamseteZeta),
                        new SimpleMotorFeedforward(
                                Drive.ksVolts,
                                Drive.kvVoltSecondsPerMeter,
                                Drive.kaVoltSecondsSquaredPerMeter),
                        Drive.kDriveKinematics,
                        Robot.Drive::getWheelSpeeds,
                        new PIDController(Drive.kPDriveVel, 0, 0),
                        new PIDController(Drive.kPDriveVel, 0, 0),
                        // RamseteCommand passes volts to the callback
                        Robot.Drive::tankDriveVolts,
                        Robot.Drive);

        // Reset odometry to the starting pose of the trajectory.
        Robot.Drive.resetOdometry(trajectory.getInitialPose());

        // Run path following command, then stop at the end.
        return ramseteCommand.andThen(() -> Robot.Drive.tankDriveVolts(0, 0));
    }
}
