package g3.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class AutonomousCommand extends CommandBase {
    @Override
    public void execute() {
        System.out.println('a');
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
