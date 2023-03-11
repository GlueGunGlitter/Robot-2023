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
    private final DoubleSolenoid piston;

    private boolean isOpen = false;
    private NetworkTable sd;

    public climbArm(Controller controller, NetworkTable sd) {
        piston = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 0, 1);
        this.controller = controller;
        this.sd = sd;
        piston.set(Value.kOff);
    }
    
    @Override
    public void periodic() {
        if (controller.inst.getAButtonPressed()) {
            System.out.println("pressed a");
            if (isOpen == true) {
                System.out.println("going forward");
                forwardPiston();

                
                isOpen = false;
            }
            else if (isOpen == false) {
                System.out.println("going reverse");
                backwardPiston();
                isOpen = true;
            }
        }
    }

    public void forwardPiston(){
        piston.set(Value.kForward);
    }

    public void backwardPiston(){
        piston.set(Value.kForward);
    }

}
