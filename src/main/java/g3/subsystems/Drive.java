package g3.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drive extends SubsystemBase {

    private final int left1Channel = 1;
    private final int left2Channel = 2;
    private final int right1Channel = 3;
    private final int right2Channel = 4;

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

    private final WPI_TalonSRX left1Motor = new WPI_TalonSRX(left1Channel);
    private final WPI_VictorSPX left2Motor = new WPI_VictorSPX(left2Channel);
    private final MotorControllerGroup leftMotors = new MotorControllerGroup(left1Motor, left2Motor);;

    private final WPI_TalonSRX right1Motor = new WPI_TalonSRX(right1Channel);
    private final WPI_VictorSPX right2Motor = new WPI_VictorSPX(right2Channel);
    private final MotorControllerGroup rightMotors = new MotorControllerGroup(right1Motor, right2Motor);

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

    private void configureMotors() {
        left1Motor.setInverted(left1Inverted);
        left2Motor.setInverted(left2Inverted);

        right1Motor.setInverted(right1Inverted);
        right2Motor.setInverted(right2Inverted);

        drive.setSafetyEnabled(safetyEnabled);
        drive.setExpiration(safetyExpiration);
        drive.setMaxOutput(driveMaxOutput);       
    }

    private void configureSensors() {
        leftEncoder.setDistancePerPulse(kEncoderDistancePerPulse);
        rightEncoder.setDistancePerPulse(kEncoderDistancePerPulse);
        resetEncoders();
    }

    @Override
    public void periodic() {}

    public void driveTank(double left, double right) {
        leftMotors.set(left);
        rightMotors.set(right);
        drive.feed();
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
