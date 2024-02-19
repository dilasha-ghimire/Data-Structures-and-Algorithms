package Question2a; 

import java.util.Arrays; // Imports the Arrays class from the java.util package

public class SewingMachine { 
    
    public int minMovesToEqualize(int[] machines) { // Declares a method named minMovesToEqualize which takes an integer array machines as input and returns an integer
        int totalDresses = 0; // Declares an integer variable totalDresses and initializes it to 0
        for (int dresses : machines) { // Iterates over each element in the machines array
            totalDresses += dresses; // Adds the value of each element to totalDresses
        }
    
        int numOfMachines = machines.length; // Declares an integer variable numOfMachines and assigns it the length of the machines array
        if (totalDresses % numOfMachines != 0) { // Checks if the total number of dresses is evenly divisible by the number of machines
            return -1; // Returns -1 if the total number of dresses cannot be evenly distributed among machines
        }
    
        int targetDressesPerMachine = totalDresses / numOfMachines; // Calculates the target number of dresses per machine
    
        int moves = 0; // Declares an integer variable moves to keep track of the number of moves made to equalize dresses

        System.out.println("Initial Array: " + Arrays.toString(machines)); // Prints the initial array for debugging purposes

        boolean equalized = false; // Declares a boolean variable equalized and initializes it to false
        while (!equalized) { // Executes the loop until all machines have equal dresses
            equalized = true; // Assumes all machines have equal dresses at the beginning of each loop iteration
    
            // Move from left to right
            for (int i = 0; i < numOfMachines - 1; i++) { // Iterates over each machine except the last one
                if (machines[i] > targetDressesPerMachine) { // Checks if the current machine has more dresses than the target
                    machines[i]--; // Decreases the number of dresses in the current machine
                    machines[i + 1]++; // Increases the number of dresses in the next machine
                    moves++; // Increments the number of moves
                    equalized = false; // Sets equalized to false since a move was made
    
                    System.out.println("Array after move " + moves + ": " + Arrays.toString(machines)); // Prints the array after each move for debugging
                }
            }

            // Move from right to left
            for (int i = numOfMachines - 1; i > 0; i--) { // Iterates over each machine except the first one
                if (machines[i] > targetDressesPerMachine) { // Checks if the current machine has more dresses than the target
                    machines[i]--; // Decreases the number of dresses in the current machine
                    machines[i - 1]++; // Increases the number of dresses in the previous machine
                    moves++; // Increments the number of moves
                    equalized = false; // Sets equalized to false since a move was made
    
                    System.out.println("Array after move " + moves + ": " + Arrays.toString(machines)); // Prints the array after each move for debugging
                }
            }
        }
    
        return moves; // Returns the total number of moves required to equalize dresses
    }
    
    public static void main(String[] args) {
        int[] machines1 = {1, 0, 5}; // Declares and initializes an integer array named machines
        int[] machines2 = {5, 0, 1}; // Declares and initializes an integer array named machines
        int[] machines3 = {1, 3, 5}; // Declares and initializes an integer array named machines
        int[] machines4 = {5, 3, 1}; // Declares and initializes an integer array named machines
        int[] machines5 = {1, 6, 5}; // Declares and initializes an integer array named machines
        int[] machines6 = {1, 0, 5, 6}; // Declares and initializes an integer array named machines
        int[] machines7 = {1, 6, 5}; // Declares and initializes an integer array named machines
        int[] machines8 = {1, 1, 5}; // Declares and initializes an integer array named machines

        SewingMachine equalizer = new SewingMachine(); // Creates an instance of the SewingMachine class
        System.out.println("Minimum moves required: " + equalizer.minMovesToEqualize(machines1)); // Calls the minMovesToEqualize method and prints the minimum moves required
        System.out.println();
        System.out.println("Minimum moves required: " + equalizer.minMovesToEqualize(machines2)); // Calls the minMovesToEqualize method and prints the minimum moves required
        System.out.println();
        System.out.println("Minimum moves required: " + equalizer.minMovesToEqualize(machines3)); // Calls the minMovesToEqualize method and prints the minimum moves required
        System.out.println();
        System.out.println("Minimum moves required: " + equalizer.minMovesToEqualize(machines4)); // Calls the minMovesToEqualize method and prints the minimum moves required
        System.out.println();
        System.out.println("Minimum moves required: " + equalizer.minMovesToEqualize(machines5)); // Calls the minMovesToEqualize method and prints the minimum moves required
        System.out.println();
        System.out.println("Minimum moves required: " + equalizer.minMovesToEqualize(machines6)); // Calls the minMovesToEqualize method and prints the minimum moves required
        System.out.println();
        System.out.println("Minimum moves required: " + equalizer.minMovesToEqualize(machines7)); // Calls the minMovesToEqualize method and prints the minimum moves required
        System.out.println();
        System.out.println("Minimum moves required: " + equalizer.minMovesToEqualize(machines8)); // Calls the minMovesToEqualize method and prints the minimum moves required
    }
}
