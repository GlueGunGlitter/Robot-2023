package g3.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import g3.utils.Controller;

public class Parallelogram extends SubsystemBase {
    private final int motorChannel = 9;

    private final Controller controller;
    private final  WPI_TalonFX motor = new  WPI_TalonFX(motorChannel);

    private final TrapezoidProfile.Constraints profileConstraints =
      new TrapezoidProfile.Constraints(0.25, 0.3);
    private final ProfiledPIDController profileController =
        new ProfiledPIDController(1.3, 0.0, 0.7, profileConstraints);

    private boolean ltFlag = false;
    private boolean rtFlag = false;

    public Parallelogram(Controller controller) {
        this.controller = controller;
    }
    

    @Override
    public void periodic() {
        System.out.println("enc: "+(motor.getSelectedSensorPosition()/2048/360));

        if (controller.inst.getLeftTriggerAxis() > 0.9 && !ltFlag) {
            
            ltFlag = true;
        }
        else {
            ltFlag = false;

            if (controller.inst.getRightTriggerAxis() > 0.9 && !rtFlag) {
                
                rtFlag = true;
            }
            else {
                rtFlag = false;
            }
        }

        motor.set(profileController.calculate(((62.136*2.0*Math.PI)/360)*(motor.getSelectedSensorPosition()/2048/360)));//
    }

    public void resetEncoder() {
        motor.setSelectedSensorPosition(0);
    }
    
}
