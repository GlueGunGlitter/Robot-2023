package g3.systems;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class Controller {
    private static final XboxController controller = new XboxController(0);
    private static final GenericHID stickLeft = new GenericHID(0);
    private static final GenericHID stickRight = new GenericHID(1);

    public boolean isPressed(int button) {
        return controller.getRawButton(button);
    }

    public double[] getStickLeft() {
        return new double[] {stickLeft.getRawAxis(0), stickLeft.getRawAxis(1)};
    }

    public double[] getStickRight() {
        return new double[] {stickRight.getRawAxis(0), stickRight.getRawAxis(1)};
    }

    public double stickControlFunc(double rawAxis) {
        return rawAxis;
    }
}
