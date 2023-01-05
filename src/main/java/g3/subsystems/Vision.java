package g3.subsystems;

import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonPipelineResult;

public class Vision {

    PhotonCamera camera = new PhotonCamera("photonvision");

    private final double OBJECT_SIZE_MM = 0d;
    private final double FOCAL_LENGTH = 0d;

    private double calculateDist(double objectPxSize) {
        return OBJECT_SIZE_MM * FOCAL_LENGTH / objectPxSize;
    }

    private PhotonPipelineResult getPhotonResults() {
        return camera.getLatestResult();
    }
}