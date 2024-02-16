import java.util.ArrayList; // Import ArrayList class
import java.util.List; // Import List interface
import java.util.Random; // Import Random class

public class TSPUsingAntColony { // Define the AntColonyTSP class
    private double[][] pheromoneMatrix; // Declare pheromoneMatrix variable
    private int[][] distanceMatrix; // Declare distanceMatrix variable
    private int numberOfNodes; // Declare numberOfNodes variable
    private int numberOfAnts; // Declare numberOfAnts variable
    private double alpha; // Declare alpha variable
    private double beta; // Declare beta variable
    private double evaporationRate; // Declare evaporationRate variable
    private double initialPheromone; // Declare initialPheromone variable

    // Constructor to initialize parameters
    public TSPUsingAntColony(int numberOfNodes, int numberOfAnts, double alpha, double beta, double evaporationRate, double initialPheromone) {
        this.numberOfNodes = numberOfNodes; // Initialize numberOfNodes
        this.numberOfAnts = numberOfAnts; // Initialize numberOfAnts
        this.alpha = alpha; // Initialize alpha
        this.beta = beta; // Initialize beta
        this.evaporationRate = evaporationRate; // Initialize evaporationRate
        this.initialPheromone = initialPheromone; // Initialize initialPheromone
        pheromoneMatrix = new double[numberOfNodes][numberOfNodes]; // Create pheromoneMatrix array
        distanceMatrix = new int[numberOfNodes][numberOfNodes]; // Create distanceMatrix array
    }

    // Initialize pheromone matrix with initial values
    public void initializePheromoneMatrix() {
        for (int i = 0; i < numberOfNodes; i++) { // Iterate over rows of pheromoneMatrix
            for (int j = 0; j < numberOfNodes; j++) { // Iterate over columns of pheromoneMatrix
                pheromoneMatrix[i][j] = initialPheromone; // Set initialPheromone for each element
            }
        }
    }

    // Initialize distance matrix
    public void initializeDistanceMatrix(int[][] distanceMatrix) {
        this.distanceMatrix = distanceMatrix; // Assign provided distanceMatrix to the instance variable
    }

    // Find the shortest tour
    public List<Integer> findShortestTour() {
        Random random = new Random(); // Create a Random object
        List<Integer> bestTour = null; // Declare bestTour variable
        int bestTourLength = Integer.MAX_VALUE; // Initialize bestTourLength
        for (int iteration = 0; iteration < numberOfAnts; iteration++) { // Loop over number of ants
            List<Integer> antTour = constructAntTour(random); // Construct tour for current ant
            int antTourLength = calculateTourLength(antTour); // Calculate length of the tour
            if (antTourLength < bestTourLength) { // Check if current tour is the best so far
                bestTourLength = antTourLength; // Update bestTourLength
                bestTour = antTour; // Update bestTour
            }
            updatePheromoneTrail(antTour, antTourLength); // Update pheromone trail based on the tour
        }
        return bestTour; // Return the best tour found
    }

    // Construct a tour for a single ant
    private List<Integer> constructAntTour(Random random) {
        List<Integer> antTour = new ArrayList<>(); // Create an ArrayList to store the tour
        boolean[] visitedNodes = new boolean[numberOfNodes]; // Create an array to track visited nodes
        int currentNode = random.nextInt(numberOfNodes); // Start from a random node
        antTour.add(currentNode); // Add starting node to the tour
        visitedNodes[currentNode] = true; // Mark starting node as visited
        while (antTour.size() < numberOfNodes) { // Loop until the tour is complete
            int nextNode = selectNextNode(currentNode, visitedNodes, random); // Select next node
            antTour.add(nextNode); // Add next node to the tour
            visitedNodes[nextNode] = true; // Mark next node as visited
            currentNode = nextNode; // Update current node
        }
        antTour.add(antTour.get(0)); // Return to the starting node to complete the tour
        return antTour; // Return the constructed tour
    }

    // Select the next node based on pheromone levels and distances
    private int selectNextNode(int currentNode, boolean[] visitedNodes, Random random) {
        double[] probabilities = new double[numberOfNodes]; // Create an array to store probabilities
        double probabilitiesSum = 0.0; // Initialize sum of probabilities
        for (int node = 0; node < numberOfNodes; node++) { // Loop over all nodes
            if (!visitedNodes[node]) { // Check if the node is not visited
                double pheromoneLevel = Math.pow(pheromoneMatrix[currentNode][node], alpha); // Calculate pheromone level
                double distance = 1.0 / Math.pow(distanceMatrix[currentNode][node], beta); // Calculate distance
                probabilities[node] = pheromoneLevel * distance; // Calculate probability for the node
                probabilitiesSum += probabilities[node]; // Update sum of probabilities
            }
        }
        double randomValue = random.nextDouble(); // Generate a random value
        double cumulativeProbability = 0.0; // Initialize cumulative probability
        for (int node = 0; node < numberOfNodes; node++) { // Loop over all nodes
            if (!visitedNodes[node]) { // Check if the node is not visited
                double probability = probabilities[node] / probabilitiesSum; // Calculate probability for the node
                cumulativeProbability += probability; // Update cumulative probability
                if (randomValue <= cumulativeProbability) { // Check if the node is selected
                    return node; // Return the selected node
                }
            }
        }
        return -1; // Unreachable code, should never happen
    }

    // Calculate the total length of the tour
    private int calculateTourLength(List<Integer> tour) {
        int tourLength = 0; // Initialize tour length
        for (int i = 0; i < tour.size() - 1; i++) { // Loop over all nodes in the tour
            int currentNode = tour.get(i); // Get current node
            int nextNode = tour.get(i + 1); // Get next node
            tourLength += distanceMatrix[currentNode][nextNode]; // Add distance between current and next nodes to tour length
        }
        return tourLength; // Return the total length of the tour
    }

    // Update the pheromone trail based on the tour length
    private void updatePheromoneTrail(List<Integer> tour, int tourLength) {
        double pheromoneDeposit = 1.0 / tourLength; // Calculate pheromone deposit
        for (int i = 0; i < tour.size() - 1; i++) { // Loop over all edges in the tour
            int currentNode = tour.get(i); // Get current node
            int nextNode = tour.get(i + 1); // Get next node
            pheromoneMatrix[currentNode][nextNode] = (1 - evaporationRate) * pheromoneMatrix[currentNode][nextNode] + evaporationRate * pheromoneDeposit; // Update pheromone level
        }
    }

    // Main method for testing
    public static void main(String[] args) {
        
        // Example usage
        int[][] distanceMatrix = {
                {0, 2, 3, 4},
                {2, 0, 6, 1},
                {3, 6, 0, 2},
                {4, 1, 2, 0}
        };
        
        int numberOfNodes = 4; // Define number of nodes
        int numberOfAnts = 10; // Define number of ants
        double alpha = 1.0; // Define alpha
        // A higher alpha value biases ants towards selecting paths with higher pheromone levels, prioritizing exploitation of known good paths.
        double beta = 2.0; // Define beta
        // A higher beta value biases ants towards selecting shorter paths based on heuristic information, prioritizing exploration of shorter paths.
        double evaporationRate = 0.5; // Define evaporation rate
        // A higher evaporation rate means that pheromone evaporates more quickly, leading to a faster decay of pheromone trails.
        double initialPheromone = 0.1; // Define initial pheromone
        // A higher initial pheromone level biases ants towards exploring paths with higher initial pheromone levels, 
        // potentially leading to premature convergence on suboptimal solutions.

        TSPUsingAntColony antColony = new TSPUsingAntColony(numberOfNodes, numberOfAnts, alpha, beta, evaporationRate, initialPheromone); // Create AntColonyTSP object
        antColony.initializePheromoneMatrix(); // Initialize pheromone matrix
        antColony.initializeDistanceMatrix(distanceMatrix); // Initialize distance matrix
        List<Integer> shortestTour = antColony.findShortestTour(); // Find the shortest tour
        System.out.println("Shortest tour: " + shortestTour); // Print the shortest tour

        /* 
        Output - Shortest tour: [0, 1, 3, 2, 0]

        Explanation -  

        Node 0: Start at node 0.
        {0, 2, 3, 4} - smallest number is 2, which means the shortest path is to node 1
        Node 1: Move from node 0 to node 1.
        {2, 0, 6, 1} - smallest number is 1, which means the shortest path is to node 3
        Node 3: Move from node 1 to node 3.
        {3, 6, 0, 2} - smallest number is 0, which means the shortest path is to node 2
        Node 2: Move from node 3 to node 2.
        {4, 1, 2, 0} - all the nodes are visited, so return to the initial node 0
        Node 0: Return to node 0 to complete the tour.
        */

        /* 
        int[][] distanceMatrix = {
            {0, 3, 4, 2},
            {3, 0, 5, 1},
            {4, 5, 0, 6},
            {2, 1, 6, 0}
        };

        Output - Shortest tour: [0, 3, 1, 2, 0]
        
        Explanation -

        Start at node 0.
        Move from node 0 to node 3.
        Move from node 3 to node 1.
        Move from node 1 to node 2.
        Return to node 0 to complete the tour. 
        */
    }
}
