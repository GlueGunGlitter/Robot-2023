package g3.subsystems;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.motorcontrol.Talon;
import edu.wpi.first.wpilibj.motorcontrol.Victor;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drive extends SubsystemBase {

    private static final int left1Channel = 0;
    private static final int left2Channel = 1;
    private static final int right1Channel = 2;
    private static final int right2Channel = 3;

    private static final boolean left1Inverted = false;
    private static final boolean left2Inverted = false;
    private static final boolean right1Inverted = false;
    private static final boolean right2Inverted = false;

    private static final int[] leftEncoderPorts = new int[] {11, 12};
    private static final boolean leftEncoderReversed = false;

    private static final int[] rightEncoderPorts = new int[] {11, 12};
    private static final boolean rightEncoderReversed = false;

    private static final boolean safetyEnabled = true;
    private static final double safetyExpiration = 0.1;
    private static final double driveMaxOutput = 1;

    public static final double kEncoderDistancePerPulse = 1/1;

    public static final double ksVolts = 0;
    public static final double kvVoltSecondsPerMeter = 0;
    public static final double kaVoltSecondsSquaredPerMeter = 0;
    public static final double kMaxSpeedMetersPerSecond = 0;
    public static final double kMaxAccelerationMetersPerSecondSquared = 0;

    public static final double kPDriveVel = 0;

    public static final double kTrackwidthMeters = 0.69;
    public static final DifferentialDriveKinematics kDriveKinematics = new DifferentialDriveKinematics(kTrackwidthMeters);

    private static Talon left1 = new Talon(left1Channel);
    private static Victor left2 = new Victor(left2Channel);
    private static MotorControllerGroup leftMotors = new MotorControllerGroup(left1, left2);;

    private static Victor right1 = new Victor(right1Channel);
    private static Victor right2 = new Victor(right2Channel);
    private static MotorControllerGroup rightMotors = new MotorControllerGroup(right1, right2);

    private static final DifferentialDrive drive = new DifferentialDrive(leftMotors, rightMotors);

    private static final Encoder leftEncoder =
            new Encoder(
                    leftEncoderPorts[0],
                    leftEncoderPorts[1],
                    leftEncoderReversed);
    private static final Encoder rightEncoder =
            new Encoder(
                    rightEncoderPorts[0],
                    rightEncoderPorts[1],
                    rightEncoderReversed);

    private static final Gyro gyro = new ADXRS450_Gyro();

    private static final DifferentialDriveOdometry odometry

    public Drive(){
        odometry = new DifferentialDriveOdometry(gyro.getRotation2d());
        Configure();
    }

    public void Configure() {
        //motors
        left1.setInverted(left1Inverted);
        left2.setInverted(left2Inverted);

        right1.setInverted(right1Inverted);
        right2.setInverted(right2Inverted);

        drive.setSafetyEnabled(safetyEnabled);
        drive.setExpiration(safetyExpiration);
        drive.setMaxOutput(driveMaxOutput);

        //encoders
        leftEncoder.setDistancePerPulse(Drive.kEncoderDistancePerPulse);
        rightEncoder.setDistancePerPulse(Drive.kEncoderDistancePerPulse);
        resetEncoders();
    }

    @Override
    public void periodic() {
        // Update the odometry in the periodic block
        odometry.update(
                gyro.getRotation2d(), leftEncoder.getDistance(), rightEncoder.getDistance());
    }

    public void driveTank(double left, double right) {
        drive.tankDrive(left, right);
    }

    public void tankDriveVolts(double leftVolts, double rightVolts) {
        leftMotors.setVoltage(leftVolts);
        rightMotors.setVoltage(rightVolts);
        drive.feed();
    }

    public Pose2d getPose() {
        return odometry.getPoseMeters();
    }

    public DifferentialDriveWheelSpeeds getWheelSpeeds() {
        return new DifferentialDriveWheelSpeeds(leftEncoder.getRate(), rightEncoder.getRate());
    }

    public void resetOdometry(Pose2d pose) {
        resetEncoders();
        odometry.resetPosition(pose, gyro.getRotation2d());
    }

    public void resetEncoders() {
        leftEncoder.reset();
        rightEncoder.reset();
    }

    public double getAverageEncoderDistance() {
        return (leftEncoder.getDistance() + rightEncoder.getDistance()) / 2.0;
    }

    public Encoder getLeftEncoder() {
        return leftEncoder;
    }

    public Encoder getRightEncoder() {
        return rightEncoder;
    }

    public void zeroHeading() {
        gyro.reset();
    }

    public double getHeading() {
        return gyro.getRotation2d().getDegrees();
    }

    public double getTurnRate() {
        return gyro.getRate();
    }
}
