package g3.systems;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.motorcontrol.Talon;
import edu.wpi.first.wpilibj.motorcontrol.Victor;

public class Drive {

    private Talon left1;
    private Victor left2;
    private MotorControllerGroup leftMotors;

    private Victor right1;
    private Victor right2;
    private MotorControllerGroup rightMotors;

    private DifferentialDrive drive;

    public void ConfigureMotors() {
        left1 = new Talon(0);
        left1.setInverted(false);
        left2 = new Victor(1);
        left2.setInverted(false);

        leftMotors = new MotorControllerGroup(left1, left2);

        right1 = new Victor(2);
        right1.setInverted(false);
        right2 = new Victor(3);
        right2.setInverted(false);

        rightMotors = new MotorControllerGroup(right1, right2);

        drive = new DifferentialDrive(leftMotors, rightMotors);
        drive.setSafetyEnabled(true);
        drive.setExpiration(0.1);
        drive.setMaxOutput(1.0);
    }

    public void driveTank(double left, double right) {
        drive.tankDrive(left, right);
    }
}
