package g3.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import g3.subsystems.Drive;

public class Rotate extends CommandBase {

    private final Drive drive;
    private double startPos;
    private double vel;
    private double angle;
    private double secondsOnTarget;
    private PIDController pidController = new PIDController(0.01, 0, 0);

    public Rotate(Drive drive, double angle) {
        this.drive = drive;
        this.angle = angle;
    }

    @Override
    public void initialize() {
        startPos = drive.getHeading();
    }

    @Override
    public void execute() {
        vel = pidController.calculate(drive.getHeading() - startPos, angle);
        drive.tankDrive(vel, -vel);

        if (drive.getHeading() == angle) {
            secondsOnTarget += 1f / 20;
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
