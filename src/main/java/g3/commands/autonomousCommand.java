package g3.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import g3.subsystems.Drive;
import g3.subsystems.Parallelogram;

public class AutonomousCommand extends CommandBase {

    private final Parallelogram parallelogram;
    private final Drive drive;

    public AutonomousCommand(Parallelogram parallelogram, Drive drive) {
        this.parallelogram = parallelogram;
        this.drive = drive;
    }

    @Override
    public void execute() {
        /*drive.resetEncoders();
        parallelogram.open();
        if (parallelogram.isOpen()) {
            drive.tankDrive(0.3, 0.3);
        }*/
    }

    @Override
    public boolean isFinished() {
        return drive.getEcoderDistanceInMeters() > 0.75;
    }
}
