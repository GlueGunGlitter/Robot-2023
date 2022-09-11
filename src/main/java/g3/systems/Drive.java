package g3.systems;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.motorcontrol.Talon;
import edu.wpi.first.wpilibj.motorcontrol.Victor;

public class Drive {

    private final int left1Chanel = 0;
    private final int left2Chanel = 1;
    private final int right1Chanel = 2;
    private final int right2Chanel = 3;

    private final boolean left1Inverted = false;
    private final boolean left2Inverted = false;
    private final boolean right1Inverted = false;
    private final boolean right2Inverted = false;

    private final boolean safetyEnabled = true;
    private final double safetyExpiration = 0.1;
    private final double driveMaxOutput = 1;

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
        left1 = new Talon(left1Chanel);
        left1.setInverted(left1Inverted);
        left2 = new Victor(left2Chanel);
        left2.setInverted(left2Inverted);

        leftMotors = new MotorControllerGroup(left1, left2);

        right1 = new Victor(right1Chanel);
        right1.setInverted(right1Inverted);
        right2 = new Victor(right2Chanel);
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
