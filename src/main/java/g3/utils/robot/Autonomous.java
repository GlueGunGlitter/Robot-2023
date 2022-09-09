package g3.utils.robot;

import g3.utils.Task;

public class Autonomous implements Task {

    public void loop() {
        System.out.println("runing auto");
    }

    public boolean loopIsFinished() {
        return false;
    }
}
