package g3.subsystems;

import java.util.Date;

import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.kauailabs.navx.frc.AHRS;

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

    private final int left1Channel = 10;
    private final int left2Channel = 11;
    private final int left3Channel = 12;
    private final int left4Channel = 13;

    private final int right1Channel = 20;
    private final int right2Channel = 21;
    private final int right3Channel = 22;
    private final int right4Channel = 23;

    private final boolean left1Inverted = false;
    private final boolean left2Inverted = false;
    private final boolean left3Inverted = false;
    private final boolean left4Inverted = false;
    private final boolean right1Inverted = true;
    private final boolean right2Inverted = true;
    private final boolean right3Inverted = true;
    private final boolean right4Inverted = true;

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
    private final  WPI_VictorSPX left2Motor = new  WPI_VictorSPX(left2Channel);
    private final  WPI_VictorSPX left3Motor = new  WPI_VictorSPX(left3Channel);
    private final  WPI_VictorSPX left4Motor = new  WPI_VictorSPX(left4Channel);
    private final MotorControllerGroup leftMotors = new MotorControllerGroup(left1Motor, left2Motor, left3Motor, left4Motor);

    private final WPI_TalonSRX right1Motor = new WPI_TalonSRX(right1Channel);
    private final WPI_VictorSPX right2Motor = new WPI_VictorSPX(right2Channel);
    private final WPI_VictorSPX right3Motor = new WPI_VictorSPX(right3Channel);
    private final WPI_VictorSPX right4Motor = new WPI_VictorSPX(right4Channel);
    private final MotorControllerGroup rightMotors = new MotorControllerGroup(right1Motor, right2Motor, right3Motor, right4Motor);

    private DifferentialDrive drive = new DifferentialDrive(leftMotors, rightMotors);

    private AHRS gyro = new AHRS(SPI.Port.kMXP);
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
        left3Motor.setInverted(left3Inverted);
        left4Motor.setInverted(left4Inverted);

        right1Motor.setInverted(right1Inverted);
        right2Motor.setInverted(right2Inverted);
        right3Motor.setInverted(right3Inverted);
        right4Motor.setInverted(right4Inverted);

        left2Motor.follow(left1Motor);
        left3Motor.follow(left1Motor);
        left4Motor.follow(left1Motor);

        right2Motor.follow(right1Motor);
        right3Motor.follow(right1Motor);
        right4Motor.follow(right1Motor);

        drive.setSafetyEnabled(safetyEnabled);
        drive.setExpiration(safetyExpiration);
        drive.setMaxOutput(driveMaxOutput);       

        left1Motor.enableCurrentLimit(safetyEnabled);
        right1Motor.enableCurrentLimit(safetyEnabled);


    }

    private void configureSensors() {
        resetEncoders();
    }


    public void test() {
        tankDrive(0.5, 0.5);
        
    }

    public void tankDrive(double left, double right) {
        left1Motor.set(left);
        right1Motor.set(right);
        drive.feed();
    }

    public void tankDriveVolts(double leftVolts, double rightVolts) {
        left1Motor.setVoltage(leftVolts);
        right1Motor.setVoltage(rightVolts);
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



