package g3.systems;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.math.trajectory.constraint.DifferentialDriveVoltageConstraint;

import java.util.List;

public class Trajectory {

    private TrajectoryConfig config;
    private edu.wpi.first.math.trajectory.Trajectory trajectory;

    public void config() {

        var autoVoltageConstraint =
                new DifferentialDriveVoltageConstraint(
                        new SimpleMotorFeedforward(
                                Drive.ksVolts,
                                Drive.kvVoltSecondsPerMeter,
                                Drive.kaVoltSecondsSquaredPerMeter),
                        Drive.kDriveKinematics,
                        10);

        // Create config for trajectory
        config = new TrajectoryConfig(
                    Drive.kMaxSpeedMetersPerSecond,
                    Drive.kMaxAccelerationMetersPerSecondSquared)
                    // Add kinematics to ensure max speed is actually obeyed
                    .setKinematics(Drive.kDriveKinematics)
                    // Apply the voltage constraint
                    .addConstraint(autoVoltageConstraint);
    }


    public void generate( Pose2d start,
                          List<Translation2d> interiorWaypoints,
                          Pose2d end){
         trajectory =
                TrajectoryGenerator.generateTrajectory(
                        start,
                        interiorWaypoints,
                        end,
                        config);
    }

    RamseteCommand ramseteCommand =
            new RamseteCommand(
                    exampleTrajectory,
                    m_robotDrive::getPose,
                    new RamseteController(AutoConstants.kRamseteB, AutoConstants.kRamseteZeta),
                    new SimpleMotorFeedforward(
                            DriveConstants.ksVolts,
                            DriveConstants.kvVoltSecondsPerMeter,
                            DriveConstants.kaVoltSecondsSquaredPerMeter),
                    DriveConstants.kDriveKinematics,
                    m_robotDrive::getWheelSpeeds,
                    new PIDController(DriveConstants.kPDriveVel, 0, 0),
                    new PIDController(DriveConstants.kPDriveVel, 0, 0),
                    // RamseteCommand passes volts to the callback
                    m_robotDrive::tankDriveVolts,
                    m_robotDrive);

    // Reset odometry to the starting pose of the trajectory.
    m_robotDrive.resetOdometry(exampleTrajectory.getInitialPose());

    // Run path following command, then stop at the end.
    return ramseteCommand.andThen(() -> m_robotDrive.tankDriveVolts(0, 0));
}
