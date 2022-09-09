package g3.utils;


public interface Task {

    default void init() {}

    void loop();

    default void end() {}

    default boolean loopIsFinished() {return false;}
}
