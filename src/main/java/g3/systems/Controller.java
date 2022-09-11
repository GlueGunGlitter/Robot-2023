package g3.systems;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class Controller {
    private final XboxController controller = new XboxController(0);
    private final GenericHID stickLeft = new GenericHID(0);
    private final GenericHID stickRight = new GenericHID(1);

    public boolean isPressed(int button) {
        return controller.getRawButton(button);
    }

    public Double[] getStickLeft() {
        return new Double[] {stickLeft.getRawAxis(0), stickLeft.getRawAxis(1)};
    }

    public Double[] getStickRight() {
        return new Double[] {stickRight.getRawAxis(0), stickRight.getRawAxis(1)};
    }

    public double velocityControlFunc(double rawAxis) {

    }
}
