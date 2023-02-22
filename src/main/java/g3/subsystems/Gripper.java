package g3.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import g3.utils.Controller;

public class Gripper extends SubsystemBase {
    private final int motorChannel = 10;

    private final Controller controller;
    private final  WPI_TalonFX motor = new  WPI_TalonFX(motorChannel);
    private final NetworkTable sd;

    private DoublePublisher vel;

    public Gripper(Controller controller, NetworkTable sd) {
        this.controller = controller;
        this.sd = sd;
        sd.getDoubleTopic("gripperVelocity").publish().set(0.4);
    }

    @Override
    public void periodic() {
        if (controller.inst.getAButton()) {
            motor.set(sd.getValue("gripperVelocity").getDouble());
        }
        else if (controller.inst.getBButton()) {
            motor.set(-sd.getValue("gripperVelocity").getDouble());
        }
        else {
            motor.set(0);
        }
    }
    
}
