package g3.utils;

import com.pathplanner.lib.PathPlanner;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import g3.subsystems.Drive;

public class TrajectoryFollower {

    private Drive drive;
    private edu.wpi.first.math.trajectory.Trajectory trajectory;

    private final double kRamseteB = 2;
    private final double kRamseteZeta = 0.7;

    public TrajectoryFollower(Drive drive) {
        this.drive = drive;
    }

    public void loadTrajectoryFromFile(String fileName) {
        trajectory = PathPlanner.loadPath(fileName, 1/drive.kvVoltSecondsPerMeter, drive.kMaxAccelerationMetersPerSecondSquared);
    }

    public SequentialCommandGroup getFollowCommand() {
        if (trajectory == null) {
            throw new NullPointerException(
                "Trajectory is null. The trajectory is needed to be set before running getFollowCommand()"
            );
        }

        RamseteCommand ramseteCommand =
                new RamseteCommand(
                        trajectory,
                        drive::getPose,
                        new RamseteController(kRamseteB, kRamseteZeta),
                        new SimpleMotorFeedforward(
                                drive.ksVolts,
                                drive.kvVoltSecondsPerMeter,
                                drive.kaVoltSecondsSquaredPerMeter),
                        drive.kDriveKinematics,
                        drive::getWheelSpeeds,
                        new PIDController(drive.kPDriveVel, 0, 0),
                        new PIDController(drive.kPDriveVel, 0, 0),
                        // RamseteCommand passes volts to the callback
                        drive::tankDriveVolts,
                        drive);

        // Reset odometry to the starting pose of the trajectory.
        drive.resetOdometry(trajectory.getInitialPose());

        // Run path following command, then stop at the end.
        return ramseteCommand.andThen(() -> drive.tankDriveVolts(0, 0));
    }

    public void setTrajectory(edu.wpi.first.math.trajectory.Trajectory trajectory) {
        this.trajectory = trajectory;
    }
}
