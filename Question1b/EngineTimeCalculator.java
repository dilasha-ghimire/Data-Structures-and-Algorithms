import java.util.Arrays; // Importing Arrays utility to sort the array
import java.util.LinkedList; // Importing LinkedList for using it as a queue
import java.util.Queue; // Importing Queue interface for queue operations

public class EngineTimeCalculator {

    public static int calculateTotalTime(int[] engineTimes, int splitCost) {
        // Sort the array of engine times
        Arrays.sort(engineTimes);

        // Create a queue and add all sorted engine times
        Queue<Integer> queue = new LinkedList<>(); // Creating a queue using LinkedList
        for (int engineTime : engineTimes) { // Looping through each engine time
            queue.add(engineTime); // Adding each engine time to the queue
        }

        int totalTime = 0; // Variable to store the total time

        // Handle the first two engines separately
        int engine1Time = queue.remove(); // Remove and retrieve the first engine time
        int engine2Time = queue.remove(); // Remove and retrieve the second engine time
        totalTime = splitCost + Math.max(engine1Time, engine2Time); // Calculating the total time

        // Iterate over the remaining engines
        while (!queue.isEmpty()) { // Loop until the queue is empty
            int engineTime = queue.remove(); // Remove and retrieve the next engine time
            totalTime = splitCost + Math.max(totalTime, engineTime); // Calculating the total time considering split cost
        }

        return totalTime; // Returning the total time
    }

    public static void main(String[] args) {
        int[] engineTimes = {1, 2, 3}; // Example array of engine times
        int splitCost = 1; // Example split cost
        int totalTime = calculateTotalTime(engineTimes, splitCost); // Calling the method to calculate total time
        System.out.println("Total time: " + totalTime); // Printing the total time
    }
}
