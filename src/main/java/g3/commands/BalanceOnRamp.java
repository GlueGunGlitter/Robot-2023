package g3.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import g3.subsystems.Drive;

public class BalanceOnRamp extends CommandBase {
    private static final double kP = 0.01;
    private static final double kI = 0;
    private static final double kD = 0;
    private Drive drive;
    private PIDController pidController = new PIDController(kP, kI, kD);
    private int mlsBalaced = 0;

    public BalanceOnRamp(Drive drive) {
        this.drive = drive;
    }

    @Override
    public void initialize() {
        mlsBalaced = 0;
    }

    @Override
    public void execute() {
        double pitch = drive.getPitch();
        double vel = -pidController.calculate(drive.getPitch(), 0);
        
        System.out.println("pitch: " + pitch + ", mls: " + mlsBalaced);
        drive.tankDrive(vel, vel);

        if (pitch >= -0.3 && pitch <= 0.3) {
            mlsBalaced++;
        } else {
            mlsBalaced = 0;
        }
    }

    @Override
    public boolean isFinished() {
        return mlsBalaced == 500;
    }
}