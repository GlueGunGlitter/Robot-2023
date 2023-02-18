package g3.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import g3.utils.Controller;

public class Gripper extends SubsystemBase {
    private final int motorChannel = 10;

    private final Controller controller;
    private final  WPI_TalonFX motor = new  WPI_TalonFX(motorChannel);

    public Gripper(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void periodic() {
        if (controller.inst.getAButton()) {
            motor.set(0.2);
        }
        else if (controller.inst.getBButton()) {
            motor.set(-0.2);
        }
    }
    
}
