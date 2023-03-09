package g3.subsystems;

import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.DoubleSolenoid;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import g3.utils.Controller;

public class climbArm extends SubsystemBase {
    private final Controller controller;
    private final DoubleSolenoid Wand = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 0, 1);

    private boolean isOpen = false;
    private NetworkTable sd;

    public climbArm(Controller controller, NetworkTable sd) {
        this.controller = controller;
        this.sd = sd;
    }

    public void DoMagic() {
        if (controller.inst.getAButtonPressed()) {
            if (isOpen == true) {
                Wand.set(Value.kForward);
                isOpen = false;
            }
            if (isOpen == false) {
                Wand.set(Value.kReverse);
                isOpen = true;
            }
        }
    }

    @Override
    public void periodic() {
        DoMagic();
    }

}
