package g3.subsystems;

import java.util.function.DoublePredicate;

// import org.apache.commons.collections4.functors.TruePredicate;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import g3.utils.Controller;

public class Parallelogram extends SubsystemBase {
    private final int motorChannel = 30;

    private final Controller controller;
    private final NetworkTable sd;
    private final  WPI_TalonFX motor = new  WPI_TalonFX(motorChannel);

    private int direction = 0;
    private double timeInMotion = 0;
    private boolean ltFlag = false;
    private boolean rtFlag = false;
    private boolean open = false;

    public Parallelogram(Controller controller, NetworkTable smartdashboard) {
        this.sd = smartdashboard;
        this.controller = controller;
    }
    
    
    @Override
    public void periodic() {

        if (controller.inst.getLeftTriggerAxis()>0.9 && !ltFlag) {
            open = false;
            direction = 1;

            ltFlag = true;
        }
        else if (controller.inst.getRightTriggerAxis()>0.9 && !rtFlag) {
            direction = -1;

            rtFlag = true;
        }
        else {
            ltFlag = false;
            rtFlag = false;
        }

        if (direction != 0) {
            motor.set((direction == 1) ? 0.16:-0.35);
            timeInMotion += 1f/20f;

            if ((timeInMotion > 1 && Math.abs(motor.getSelectedSensorVelocity()) < 1800)) {
                direction = 0;
                timeInMotion = 0;
                motor.set(0);

                if (direction == -1) {
                    open = true;
                }
            }
        }
        else {
            motor.set(0);
        }

        //System.out.println("time: " + timeInMotion + ", speed: " + motor.getSelectedSensorVelocity() + ", dir: " +direction);
    }

    public void open() {
        direction = -1;
    }

    public void close() {
        direction = 1;
    }

    public boolean isOpen() {
        return open;
    }

    public void resetEncoder() {
        motor.setSelectedSensorPosition(0);
    }  
}
