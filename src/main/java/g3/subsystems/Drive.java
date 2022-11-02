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

    private final int left1Channel = 0;
    private final int left2Channel = 1;
    private final int right1Channel = 2;
    private final int right2Channel = 3;

    private final boolean left1Inverted = false;
    private final boolean left2Inverted = false;
    private final boolean right1Inverted = false;
    private final boolean right2Inverted = false;

    private final int[] leftEncoderPorts = new int[] {11, 12};
    private final boolean leftEncoderReversed = false;

    private final int[] rightEncoderPorts = new int[] {13, 14};
    private final boolean rightEncoderReversed = false;

    private final boolean safetyEnabled = true;
    private final double safetyExpiration = 0.1;
    private final double driveMaxOutput = 1;

    public final double kEncoderDistancePerPulse = 1/1;

    public final double ksVolts = 0;
    public final double kvVoltSecondsPerMeter = 0;
    public final double kaVoltSecondsSquaredPerMeter = 0;
    public final double kMaxSpeedMetersPerSecond = 0;
    public final double kMaxAccelerationMetersPerSecondSquared = 0;

    public final double kPDriveVel = 0;

    public final double kTrackwidthMeters = 0.69;
    public final DifferentialDriveKinematics kDriveKinematics = new DifferentialDriveKinematics(kTrackwidthMeters);

    private Talon left1 = new Talon(left1Channel);
    private Victor left2 = new Victor(left2Channel);
    private MotorControllerGroup leftMotors = new MotorControllerGroup(left1, left2);;

    private Victor right1 = new Victor(right1Channel);
    private Victor right2 = new Victor(right2Channel);
    private MotorControllerGroup rightMotors = new MotorControllerGroup(right1, right2);

    private DifferentialDrive drive = new DifferentialDrive(leftMotors, rightMotors);

    private Encoder leftEncoder =
            new Encoder(
                    leftEncoderPorts[0],
                    leftEncoderPorts[1],
                    leftEncoderReversed);
    private Encoder rightEncoder =
            new Encoder(
                    rightEncoderPorts[0],
                    rightEncoderPorts[1],
                    rightEncoderReversed);

    private Gyro gyro = new ADXRS450_Gyro();

    private DifferentialDriveOdometry odometry = new DifferentialDriveOdometry(gyro.getRotation2d());

    public Drive(){
        configureMotors();
        configureSensors();
    }

    public void configureMotors() {
        left1.setInverted(left1Inverted);
        left2.setInverted(left2Inverted);

        right1.setInverted(right1Inverted);
        right2.setInverted(right2Inverted);

        drive.setSafetyEnabled(safetyEnabled);
        drive.setExpiration(safetyExpiration);
        drive.setMaxOutput(driveMaxOutput);       
    }

    public void configureSensors() {
        leftEncoder.setDistancePerPulse(kEncoderDistancePerPulse);
        rightEncoder.setDistancePerPulse(kEncoderDistancePerPulse);
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
