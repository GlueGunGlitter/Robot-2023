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
import edu.wpi.first.wpilibj.Encoder;
import g3.utils.Controller;

public class Parallelogram extends SubsystemBase {
    private final int motorChannel = 30;

    private final Controller controller;
    private final NetworkTable sd;
    private final WPI_TalonFX motor = new WPI_TalonFX(motorChannel);

    private int direction = 0;
    private double timeInMotion = 0;
    private boolean ltFlag = false;
    private boolean rtFlag = false;
    private boolean open = false;
    private int openPos = -71171;
    private int closedPos = 22655;
    private int midPos = -44545;
    private double currGoal = 0;
    private DoublePublisher encRaw;
    private DoublePublisher boreEnc;
    private Encoder throughBore;
    private int ticks;
    private int rawTicks;

    private boolean pressedRight = false;
    private boolean pressedLeft = false;

    private final PIDController pid = new PIDController(0.00002, 0.0, 0);

    public Parallelogram(Controller controller, NetworkTable smartdashboard) {
        this.sd = smartdashboard;
        this.controller = controller;
        encRaw = sd.getDoubleTopic("Parallelogram encRaw").publish();
        boreEnc = sd.getDoubleTopic("Parallelogram Bore Encoder").publish();
        this.throughBore = new Encoder(0, 1);

    }

    @Override
    public void periodic() {
        encRaw.set(motor.getSelectedSensorPosition());
        boreEnc.set(throughBore.getDistance());
        System.out.println("Rate" + throughBore.getRate() + ", Distance: " + throughBore.getDistance() + " Raw: "
                + throughBore.getRaw());

        if (controller.inst.getLeftTriggerAxis() > 0.9 && !ltFlag) {
            open = false;
            direction = 1;

            ltFlag = true;
        } else if (controller.inst.getRightTriggerAxis() > 0.9 && !rtFlag) {
            direction = -1;

            rtFlag = true;
        } else {
            ltFlag = false;
            rtFlag = false;
        }

        if (direction != 0) {
            if (direction == 1) { // closing
                if (throughBore.getDistance() < midPos) {
                    motor.set(0);

                } else {
                    motor.set(0.3);
                }
            } else { // OPening
                if (throughBore.getDistance() > -230) {
                    motor.set(-0.5);
                } else {
                    motor.set(0);
                }
            }
            // motor.set((direction == 1) ? 0.16 : -0.35); // 0.16 is close, direciton 1 is
            // close

        }
        timeInMotion += 1f / 20f;

        if ((timeInMotion > 1 && Math.abs(motor.getSelectedSensorVelocity()) < 1800)) {
            direction = 0;
            timeInMotion = 0;
            motor.set(0);

            if (direction == -1) {
                open = true;
            }
        }
    }

    {
        motor.set(0);
    }

    // System.out.println("time: " + timeInMotion + ", speed: " +
    // motor.getSelectedSensorVelocity() + ", dir: " +direction);

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
        throughBore.reset();
    }

    public void pid_control() {
        if (controller.inst.getLeftTriggerAxis() > 0.9 && controller.inst.getRightTriggerAxis() > 0.9) {
            currGoal = throughBore.getDistance();
        } else if (controller.inst.getRightTriggerAxis() > 0.9 && pressedRight == false) {
            pressedLeft = false;
            pressedRight = true;
            if (throughBore.getDistance() < midPos) {
                currGoal = openPos;
            } else {
                currGoal = midPos;
            }

        } else if (controller.inst.getLeftTriggerAxis() > 0.9 && pressedLeft == false) {
            pressedRight = false;
            pressedLeft = true;
            if (throughBore.getDistance() > midPos) {
                currGoal = closedPos;
            } else {
                currGoal = midPos;
            }

        } else {
            currGoal = throughBore.getDistance();
        }
        if (throughBore.getDistance() <= -500 && pressedRight == true) {
            motor.set(0);
            if (throughBore.getDistance() >= -30 && pressedLeft == true) {
                motor.set(0);
            }
        } else {
            motor.set(pid.calculate(throughBore.getDistance(), currGoal));

        }
    }

}