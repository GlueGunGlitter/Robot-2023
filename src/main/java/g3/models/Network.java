package g3.models;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Network {

    private NetworkTableInstance nt;
    
    public Network(int teamNum) {
        nt = NetworkTableInstance.getDefault();
        nt.startClientTeam(teamNum);  // where TEAM=190, 294, etc, or use inst.startClient("hostname") or similar
        nt.startDSClient();
    }
}
