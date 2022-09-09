package g3.code.gametasks;

import g3.utils.Task;

public class Autonomous extends Task {

    public void loop() {
        System.out.println("runing auto");
    }

    public boolean loopIsFinished() {
        return false;
    }

    @Override
    public void init() {

    }

    @Override
    public void execute() {

    }

    @Override
    public void end() {

    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
