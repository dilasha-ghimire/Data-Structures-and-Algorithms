# Traveling Salesman Problem Solver using Ant Colony Optimization (ACO)


## Overview

This project provides a Java implementation of an Ant Colony Optimization (ACO) algorithm to solve the Traveling Salesman Problem (TSP). The TSP is a classic optimization problem where the goal is to find the shortest possible tour that visits every city exactly once and returns to the original city.

The ACO algorithm mimics the foraging behavior of ants to find good solutions to the TSP. It iteratively constructs tours using a combination of pheromone levels and distances between cities, updating pheromone trails to bias future ant behavior towards shorter paths.

1. **Setting Off**: We begin by assembling a group of ants.

2. **Ant Adventures**: Each ant embarks on its own journey. They start from random points and decide where to go next by weighing the distance and the scent left by other ants (pheromones).

3. **Leaving Traces**: While ants travel, they leave behind a scent trail. Paths with shorter distances get stronger scents.

4. **Repeat**: The ants keep exploring and marking paths for a while.

5. **Choosing the Best Route**: Finally, we examine all the paths the ants explored and select the best one. This path represents the solution to the Traveling Salesman Problem (TSP).

In essence, ants roam around, marking paths with their scent, and we choose the most promising path they found as our solution to the TSP.

## Customization
You can customize the parameters of the ACO algorithm and the TSP instance by modifying the main method in the AntColonyTSP.java file. Here are the parameters you can adjust:

 - **numberOfNodes**: The number of cities in the TSP instance.
 - **numberOfAnts**: The number of ants used in the algorithm.
 - **alpha**: Controls the influence of pheromone levels on ant behavior. Higher values bias ants towards exploiting known good paths.
 - **beta**: Controls the influence of distances on ant behavior. Higher values bias ants towards exploring shorter paths.
 - **evaporationRate**: Determines how quickly pheromone trails evaporate over time.
 - **initialPheromone**: Sets the initial pheromone level on edges. Higher values bias ants towards exploring paths with higher initial pheromone levels.

You can also define custom distance matrices to represent different TSP instances.

## Example

``` java

    int[][] distanceMatrix = {
                {0, 2, 3, 4},
                {2, 0, 6, 1},
                {3, 6, 0, 2},
                {4, 1, 2, 0}
        };

        Output - Shortest tour: [0, 1, 3, 2, 0]

        Explanation -  

        Node 0: Start at node 0.
        {0, 2, 3, 4} - smallest number is 2, 
        which means the shortest path is to node 1

        Node 1: Move from node 0 to node 1.
        {2, 0, 6, 1} - smallest number is 1, 
        which means the shortest path is to node 3

        Node 3: Move from node 1 to node 3.
        {3, 6, 0, 2} - smallest number is 0, 
        which means the shortest path is to node 2

        Node 2: Move from node 3 to node 2.
        {4, 1, 2, 0} - all the nodes are visited, 
        so return to the initial node 0

        Node 0: Return to node 0 to complete the tour.

```
``` java

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


        