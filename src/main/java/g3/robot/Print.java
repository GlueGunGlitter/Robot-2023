package g3.robot;

import g3.utils.Task;

public class Print implements Task {

    String string;

    public Print(String string, double runIntervalSec) {
        this.string = string;
    }

    @Override
    public void loop() {
        System.out.println(string);
    }

    @Override
    public boolean loopIsFinished() {
        return false;
    }

}