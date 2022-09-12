package g3.systems;

public class Vision {

    public final double OBJECT_SIZE_MM = 0d;
    public final double OBJECT_SIZE_PX = 0d;
    public final double FOCAL_LENGTH = 0d;

    public double calculateDist(double objectPxSize) {
        return OBJECT_SIZE_MM * FOCAL_LENGTH / objectPxSize;
    }
}
