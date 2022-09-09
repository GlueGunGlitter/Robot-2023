package g3.utils;

public class Timer {

    private long start;

    public Timer(){
        reset();
    }

    public void reset(){
        start = System.currentTimeMillis();
    }

    //return time in seconds
    public double getTime() {
        return ( (double) (System.currentTimeMillis() - start) )/1000;
    }

}
