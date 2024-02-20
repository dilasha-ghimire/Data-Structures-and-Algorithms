package Question5b;

import java.util.ArrayList;
import java.util.List;

// Class representing an ISP application for managing a network of devices
public class IspApplication {
    // Number of devices in the network
    int v;
    // Adjacent matrix representing connections between devices
    int network[][];

    // Constructor initializing the number of devices and the network matrix
    IspApplication(int v) {
        this.v = v;
        network = new int[v][v];
    }

    // Function to add an edge between connected devices in the network (undirected graph)
    public void addEdge(int source, int destination){
        network[source][destination]=1;
        network[destination][source] = 1;
    }

    // Function to print the network matrix
    public void printNetwork() {
        for(int i = 0; i< network.length; i++) {
            for(int j = 0; j<network[i].length; j++) {
                System.out.print(network[i][j]);
            }
            System.out.println();
        }
    }

    // Function to find the devices directly connected to the target or source device
    List<Integer> getConnectedDevices(int root){
        List<Integer> connectedDevices=new ArrayList<>();
        for(int j=0; j<v;j++){
            if(network[root][j]!=0 || network[j][root]!=0){
                connectedDevices.add(j);
            }
        }
        return connectedDevices;
    }

    // Function to find the impacted devices when a target device loses power
    public List<Integer> findImpactedDevices(int targetDevice, int source) {
        List<Integer> connectedDevicesToTarget = getConnectedDevices(targetDevice);
        List<Integer> connectedDevicesToSource = getConnectedDevices(source);
        List<Integer> impactedDevices = new ArrayList<>();
        boolean visited[] = new boolean[v];

        // Mark the source and its direct connections as visited to exclude them from impacted devices
        visited[source] = true;
        for (int device : connectedDevicesToSource) {
            visited[device] = true;
        }

        // Traverse the connected devices excluding source and its direct connections
        for (int device : connectedDevicesToTarget) {
            if (!visited[device]) {
                dfsCheckSource(device, visited, impactedDevices, source, targetDevice);
            }
        }

        return impactedDevices;
    }

    // Depth-first search to check if a device is indirectly connected to the source
    private void dfsCheckSource(int node, boolean visited[], List<Integer> impactedDevices, int source, int targetDevice) {
        visited[node] = true;

        // If the node is the targetDevice, return
        if (node == targetDevice) {
            return;
        }

        // To check if the node is connected to the source
        boolean connectedToSource = false;

        for (int i = 0; i < v; i++) {
            if ((network[node][i] != 0 || network[i][node] != 0) && !visited[i]) {
                dfsCheckSource(i, visited, impactedDevices, source, targetDevice);
                // If node i is connected to the source, set connectedToSource to true
                if (isConnectedToSource(i, source, visited)) {
                    connectedToSource = true;
                }
            }
        }

        // If the node is not connected to the source, add it to the impactedDevices list
        if (!connectedToSource) {
            impactedDevices.add(node);
        }
    }

    // Helper method to check if a node is indirectly connected to the source
    private boolean isConnectedToSource(int node, int source, boolean visited[]) {
        visited[node] = true;

        // If the node is the source or directly connected to the source, return true
        if (node == source) {
            return true;
        }

        // Check if the node is indirectly connected to the source through another node
        for (int i = 0; i < v; i++) {
            if ((network[node][i] != 0 || network[i][node] != 0) && !visited[i]) {
                if (isConnectedToSource(i, source, visited)) {
                    return true;
                }
            }
        }

        return false;
    }

    // Main method to test the ISP application
    public static void main(String[] args) {
        // Define target device and power source
        int target = 4;
        int powerSource = 0;

        // Create ISP application instance
        IspApplication isp = new IspApplication(8);

        // Add edges representing connections between devices
        isp.addEdge(0, 1);
        isp.addEdge(0, 2);
        isp.addEdge(1, 3);
        isp.addEdge(1, 6);
        isp.addEdge(2, 4);
        isp.addEdge(4, 6);
        isp.addEdge(4, 5);
        isp.addEdge(5, 7);

        // Print the network matrix
        isp.printNetwork();

        // Find impacted devices when power is cut off at the target device
        List<Integer> impDevice = isp.findImpactedDevices(target, powerSource);

        // Print the impacted device list
        System.out.println("Impacted Device List: " + impDevice);
    }
}
