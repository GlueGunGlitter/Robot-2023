package g3.subsystems;

import java.util.Date;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
//import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.SPI;
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
    private final boolean right1Inverted = true;
    private final boolean right2Inverted = true;

    private final boolean safetyEnabled = true;
    private final double safetyExpiration = 0.1;
    private final double driveMaxOutput = 1;

    public final double kEncoderMetersPerRotation = 1/1;

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

    ADXRS450_Gyro gyro = new ADXRS450_Gyro();//TODO - private AHRS gyro = new AHRS(SPI.Port.kMXP);
    private DifferentialDriveOdometry odometry = new DifferentialDriveOdometry(gyro.getRotation2d(), driveMaxOutput, driveMaxOutput);

    private double leftEncoderResetPos = 0;
    private double rightEncoderResetPos = 0;

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
        resetEncoders();
    }


    public void test() {
        System.out.println("gyro: " +getHeading());
        
    }

    public void tankDrive(double left, double right) {
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
        return new DifferentialDriveWheelSpeeds(
                left1Motor.getSelectedSensorVelocity(), 
                right1Motor.getSelectedSensorVelocity()
            );
    }

    public void resetOdometry(Pose2d pose) {
        resetEncoders();
        odometry.resetPosition(gyro.getRotation2d(), leftEncoderResetPos, rightEncoderResetPos, pose);
    }

    public void resetEncoders() {
        leftEncoderResetPos = left1Motor.getSelectedSensorPosition();
        rightEncoderResetPos = right1Motor.getSelectedSensorPosition();
    }

    public double getAverageEncoderDistance() {
        return ((left1Motor.getSelectedSensorPosition()-leftEncoderResetPos) + 
                (right1Motor.getSelectedSensorPosition()-rightEncoderResetPos)) / 2.0;
    }

    public void zeroHeading() {
        gyro.reset();
    }

    public double getHeading() {
        return gyro.getAngle();
    }

    public double getTurnRate() {
        return gyro.getRate();
    }
}



