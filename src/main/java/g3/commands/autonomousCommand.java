package g3.commands;

import java.sql.Time;
import java.time.Instant;
import java.time.LocalTime;
import java.util.Timer;

import edu.wpi.first.wpilibj2.command.CommandBase;
import g3.subsystems.Drive;
import g3.subsystems.Gripper;
import g3.subsystems.Parallelogram;

public class AutonomousCommand extends CommandBase {

    private final Parallelogram parallelogram;
    private final Drive drive;
    private final Gripper gripper;
    private double statTimer = 0;
    private boolean hasOpened = false;
    private boolean hasClosed = false;

    public AutonomousCommand(Parallelogram parallelogram, Gripper gripper, Drive drive) {
        this.parallelogram = parallelogram;
        this.drive = drive;
        this.gripper = gripper;
    }

    @Override
    public void initialize() {
        gripper.closeCone();
        parallelogram.open();
        statTimer = System.currentTimeMillis();
        
    }

    @Override
    public void execute() {
        if (System.currentTimeMillis()-statTimer>3000 && hasOpened==false) {
            hasOpened=true;
            gripper.open();
        }
        if (System.currentTimeMillis()-statTimer>4000 && System.currentTimeMillis()-statTimer<7500) {
            drive.tankDrive(-0.3, -0.3);

            if (!hasClosed) {
                parallelogram.close();
                hasClosed = true;
            }
        }
            
        
        
    }
    

    @Override
    public boolean isFinished() {
        return (System.currentTimeMillis()-statTimer) > 15000;
    }
}
