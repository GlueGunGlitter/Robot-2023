package g3.subsystems;

import org.photonvision.PhotonCamera;

public class Vision {

    PhotonCamera camera = new PhotonCamera("photonvision");

    private final double OBJECT_SIZE_MM = 0d;
    private final double FOCAL_LENGTH = 0d;

    public double calculateDist(double objectPxSize) {
        return OBJECT_SIZE_MM * FOCAL_LENGTH / objectPxSize;
    }
}
