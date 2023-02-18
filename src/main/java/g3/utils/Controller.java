package g3.utils;

import edu.wpi.first.wpilibj.XboxController;

public class Controller {
    public final XboxController inst = new XboxController(0);

    public boolean isPressed(int button) {
        return inst.getRawButton(button);
    }

    public double[] getStickLeft() {
        return new double[] {inst.getRawAxis(0), inst.getRawAxis(1)};
    }

    public double[] getStickRight() {
        return new double[] {inst.getRawAxis(4), inst.getRawAxis(5)};
    }

    public double stickControlFunc(double rawAxis) {
        return Math.pow(rawAxis, 5);
    }
}
