package g3.systems;

import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.motorcontrol.Talon;
import edu.wpi.first.wpilibj.motorcontrol.Victor;

public class Drive {

    private static final int left1Channel = 0;
    private static final int left2Channel = 1;
    private static final int right1Channel = 2;
    private static final int right2Channel = 3;

    private static final boolean left1Inverted = false;
    private static final boolean left2Inverted = false;
    private static final boolean right1Inverted = false;
    private static final boolean right2Inverted = false;

    private static final boolean safetyEnabled = true;
    private static final double safetyExpiration = 0.1;
    private static final double driveMaxOutput = 1;

    public static final double ksVolts = 0;
    public static final double kvVoltSecondsPerMeter = 0;
    public static final double kaVoltSecondsSquaredPerMeter = 0;
    public static final double kMaxSpeedMetersPerSecond = 0;
    public static final double kMaxAccelerationMetersPerSecondSquared = 0;
    public static final DifferentialDriveKinematics kDriveKinematics = null;

    private Talon left1;
    private Victor left2;
    private MotorControllerGroup leftMotors;

    private Victor right1;
    private Victor right2;
    private MotorControllerGroup rightMotors;

    private DifferentialDrive drive;

    public Drive(){
        ConfigureMotors();
    }

    public void ConfigureMotors() {
        left1 = new Talon(left1Channel);
        left1.setInverted(left1Inverted);
        left2 = new Victor(left2Channel);
        left2.setInverted(left2Inverted);

        leftMotors = new MotorControllerGroup(left1, left2);

        right1 = new Victor(right1Channel);
        right1.setInverted(right1Inverted);
        right2 = new Victor(right2Channel);
        right2.setInverted(right2Inverted);

        rightMotors = new MotorControllerGroup(right1, right2);

        drive = new DifferentialDrive(leftMotors, rightMotors);
        drive.setSafetyEnabled(safetyEnabled);
        drive.setExpiration(safetyExpiration);
        drive.setMaxOutput(driveMaxOutput );
    }

    public void driveTank(double left, double right) {
        drive.tankDrive(left, right);
    }
}
