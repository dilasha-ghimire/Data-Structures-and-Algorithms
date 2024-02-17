import java.util.ArrayList; // Importing ArrayList class from java.util package
import java.util.Collections; // Importing Collections class from java.util package
import java.util.List; // Importing List interface from java.util package

public class ScoreTracker { // Declaring a public class named ScoreTracker
    
    // Declaring a private instance variable 'scores' of type List that stores Double values
    private List<Double> scores; 

    public ScoreTracker() { // Constructor method for ScoreTracker class
        scores = new ArrayList<>(); // Initializing 'scores' as a new ArrayList object
    }

    public void addScore(double score) { // Method to add a score to the list
        scores.add(score); // Adding the provided score to the list
    }

    public double getMedianScore() { // Method to calculate the median score
        int size = scores.size(); // Getting the size of the list
        double result = 0; // Initializing a variable 'result' to store the median value

        Collections.sort(scores); // Sorting the list in ascending order

        if (size == 0) { // Checking if the list is empty
            throw new IllegalStateException("No scores added."); // Throwing an exception if no scores have been added
        } 
        
        else if (size == 1) { // Checking if there is only one score in the list
            result = scores.get(0); // Setting the result to the single score in the list
        }

        else if (size % 2 == 0) { // Checking if the size of the list is even
            int midSize = size / 2; // Calculating the middle index of the list
            double median = (scores.get(midSize - 1) + scores.get(midSize)) / 2.0; // Calculating the median for even-sized list
            result = median; // Setting the result to the calculated median value
        } 
        
        else { // If the size of the list is odd
            result = scores.get(size / 2); // Setting the result to the middle score in the sorted list
        }

        return result; // Returning the calculated median score
    }

    public static void main(String[] args) { // Main method
        ScoreTracker scoreTracker = new ScoreTracker(); // Creating an instance of ScoreTracker class
        scoreTracker.addScore(85.5); // Adding a score to the tracker
        scoreTracker.addScore(92.3); // Adding another score to the tracker
        scoreTracker.addScore(77.8); // Adding another score to the tracker
        scoreTracker.addScore(90.1); // Adding another score to the tracker
        double median1 = scoreTracker.getMedianScore(); // Calculating the median score
        System.out.println("Median 1: " + median1); // Printing the calculated median

        // Output: 87.8 

        scoreTracker.addScore(81.2); // Adding another score to the tracker
        scoreTracker.addScore(88.7); // Adding another score to the tracker
        double median2 = scoreTracker.getMedianScore(); // Recalculating the median score
        System.out.println("Median 2: " + median2); // Printing the recalculated median
    
        // Output: 87.1
    }
}
