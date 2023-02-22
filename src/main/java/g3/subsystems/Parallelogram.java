package g3.subsystems;

import java.util.function.DoublePredicate;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import g3.utils.Controller;

public class Parallelogram extends SubsystemBase {
    private final int motorChannel = 9;

    private final Controller controller;
    private final NetworkTable sd;
    private final  WPI_TalonFX motor = new  WPI_TalonFX(motorChannel);

    private final TrapezoidProfile.Constraints profileConstraints =
      new TrapezoidProfile.Constraints(0.1, 0.03);
    private final ProfiledPIDController profileController =
        new ProfiledPIDController(0.0001, 0.0, 0, profileConstraints);

    private boolean ltFlag = false;
    private boolean rtFlag = false;

    private DoublePublisher encCalc;
    private DoublePublisher encRaw;

    public Parallelogram(Controller controller, NetworkTable smartdashboard) {
        this.sd = smartdashboard;
        this.controller = controller;

        encCalc = sd.getDoubleTopic("parallelogram encCalc").publish();
        encRaw = sd.getDoubleTopic("parallelogram encRaw").publish();
    }
    

    @Override
    public void periodic() {
        encCalc.set((motor.getSelectedSensorPosition()/2048/150)*360);
        encRaw.set(motor.getSelectedSensorPosition());
        profileController.setGoal(-110.712109375);


        if (controller.inst.getLeftTriggerAxis() > 0.9) {
            //motor.set(0.2);
            ltFlag = true;
        }
        else {
            ltFlag = false;

            if (controller.inst.getRightTriggerAxis() > 0.9) {
                //motor.set(-0.2);
                //profileController.setGoal(0);
                
                rtFlag = true;
            }
            else {
                rtFlag = false;
            }
        }

        if (controller.inst.getRightTriggerAxis() < 0.9 && controller.inst.getLeftTriggerAxis() < 0.9) {
            //motor.set(0);
        }

        motor.set(profileController.calculate(-(((62.136*2.0*Math.PI)/360)*(motor.getSelectedSensorPosition()/2048/150)*360)));//-121.712109375
    }

    public void resetEncoder() {
        motor.setSelectedSensorPosition(0);
    }
    
}
