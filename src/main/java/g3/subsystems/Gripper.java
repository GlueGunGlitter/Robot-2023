package g3.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import g3.utils.Controller;

public class Gripper extends SubsystemBase {
    private final int motorChannel = 40;

    private final Controller controller;
    private final  WPI_TalonFX motor = new  WPI_TalonFX(motorChannel);
    private final NetworkTable sd;



    private double goal = 0;
    private DoublePublisher encRaw;
    

    private final PIDController pid =
    new PIDController(0.00002, 0.0, 0);

    public Gripper(Controller controller, NetworkTable sd) {
        this.controller = controller;
        this.sd = sd;

        encRaw = sd.getDoubleTopic("Griper encRaw").publish();
    }

    @Override
    public void periodic() {
        encRaw.set(motor.getSelectedSensorPosition());
        

        if (controller.inst.getYButtonPressed()) {// open
            goal = -37186;
        }
        else if (controller.inst.getBButtonPressed()) { //cube
            goal = -5500;
        }
        else if (controller.inst.getXButtonPressed()) { //cone
            goal = 5000;
        }

        motor.set(pid.calculate(motor.getSelectedSensorPosition(), goal));
    }

    public void open() {
        goal = 30186;
    }

    public void closeCube() {
        goal = -100;
    }

    public void closeCone() {
        goal = -13000;
    }

    public void resetEncoder() {
        motor.setSelectedSensorPosition(0);
    }  
}

