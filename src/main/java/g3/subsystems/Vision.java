package g3.subsystems;

import java.util.ArrayList;
import java.util.Optional;

import org.photonvision.PhotonCamera;
import org.photonvision.PhotonUtils;
import org.photonvision.RobotPoseEstimator;
import org.photonvision.RobotPoseEstimator.PoseStrategy;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.apriltag.AprilTag;
import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.math.Pair;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.Timer;
import g3.Contsants.FieldConstants;

public class Vision {

    static final double CAMERA_HEIGHT_METERS = 0.93;
    static final double TARGET_HEIGHT_METERS = 1.37;
    static final double CAMERA_PITCH_RADIANS = 0;

    public static final int aprilTagsPipeline = 1;
    public static final int reflectiveTapePipeline = 2;

    static final Transform3d robotToCam =
    new Transform3d(
        new Translation3d(0.5, 0.0, 0.5),
        new Rotation3d(
                0, 0,
                0)); // Cam mounted facing forward, half a meter forward of center, half a meter up
    // from center.
    static final String cameraName = "OV5647";

    private PhotonCamera photonCamera;
    private RobotPoseEstimator robotPoseEstimator;

    private int selectedPipeline = 1;

    public Vision() {
        // Forward Camera
        photonCamera = new PhotonCamera(cameraName); // Change the name of your camera here to whatever it is in the
        // PhotonVision UI.

        // ... Add other cameras here

        // Assemble the list of cameras & mount locations
        var camList = new ArrayList<Pair<PhotonCamera, Transform3d>>();
        camList.add(new Pair<PhotonCamera, Transform3d>(photonCamera, robotToCam));

        initApprilTags();
    }

    private void initApprilTags() {
        // Set up a test arena of two apriltags at the center of each driver station set
        final AprilTag tag18 =
                new AprilTag(
                        18,
                        new Pose3d(
                                new Pose2d(
                                        FieldConstants.length,
                                        FieldConstants.width / 2.0,
                                        Rotation2d.fromDegrees(180))));
        final AprilTag tag01 =
                new AprilTag(
                        01,
                        new Pose3d(new Pose2d(0.0, FieldConstants.width / 2.0, Rotation2d.fromDegrees(0.0))));
        ArrayList<AprilTag> atList = new ArrayList<AprilTag>();
        atList.add(tag01);

        // TODO - once 2023 happens, replace this with just loading the 2023 field arrangement
        AprilTagFieldLayout atfl =
                new AprilTagFieldLayout(atList, FieldConstants.length, FieldConstants.width);

        var camList = new ArrayList<Pair<PhotonCamera, Transform3d>>();
        camList.add(new Pair<PhotonCamera, Transform3d>(photonCamera, robotToCam));
                
        robotPoseEstimator =
                new RobotPoseEstimator(atfl, PoseStrategy.CLOSEST_TO_REFERENCE_POSE, camList);

    }

    public PhotonPipelineResult getResults() {
        return photonCamera.getLatestResult();
    }

    public void changePipeline(int pipeline) {
        photonCamera.setPipelineIndex(pipeline);
        selectedPipeline = pipeline;
    }

    public double distFromTarget(PhotonTrackedTarget target) {
        System.out.println("pitch: "+target.getPitch());

        double dist = PhotonUtils.calculateDistanceToTargetMeters(
                                CAMERA_HEIGHT_METERS,
                                TARGET_HEIGHT_METERS,
                                CAMERA_PITCH_RADIANS,
                                Units.degreesToRadians(target.getPitch()));

        return dist;
    }    
    
    public double angleTarget(PhotonTrackedTarget target) {
        return target.getYaw();
    }

    /**
     * @param estimatedRobotPose The current best guess at robot pose
     * @return A pair of the fused camera observations to a single Pose2d on the field, and the time
     *     of the observation. Assumes a planar field and the robot is always firmly on the ground
     */
    public Pair<Pose2d, Double> getEstimatedPose(Pose2d prevEstimatedRobotPose) {
        if (selectedPipeline == aprilTagsPipeline) {
                robotPoseEstimator.setReferencePose(prevEstimatedRobotPose);

            double currentTime = Timer.getFPGATimestamp();
            Optional<Pair<Pose3d, Double>> result = robotPoseEstimator.update();
            if (result.isPresent()) {
                return new Pair<Pose2d, Double>(
                        result.get().getFirst().toPose2d(), currentTime - result.get().getSecond());
            } else {
                return new Pair<Pose2d, Double>(null, 0.0);
            }
        } else {
            return new Pair<Pose2d, Double>(null, 0.0);
        }
    }
}