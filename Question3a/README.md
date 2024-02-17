# ScoreTracker 

The ScoreTracker class is a Java implementation designed to facilitate the tracking and calculation of median scores from a stream of assignment scores. 

## Features:

 - **Initialization**: The ScoreTracker class allows users to create a new instance of the tracker using the `ScoreTracker()` constructor method.

- **Adding Scores**: Users can add individual assignment scores to the tracker using the `addScore(double score)` method.

- **Calculating Median**: The class provides functionality to calculate the median of all the assignment scores in the data stream using the `getMedianScore()` method. If the number of scores is even, the median is calculated as the average of the two middle scores.

## Example

``` java

        ScoreTracker scoreTracker = new ScoreTracker(); 
        // Creating an instance of ScoreTracker class

        scoreTracker.addScore(85.5); // Adding a score to the tracker
        scoreTracker.addScore(92.3); // Adding another score to the tracker
        scoreTracker.addScore(77.8); // Adding another score to the tracker
        scoreTracker.addScore(90.1); // Adding another score to the tracker

        double median1 = scoreTracker.getMedianScore(); 
        // Calculating the median score
        System.out.println("Median 1: " + median1); 
        // Printing the calculated median

        // Output: 87.8 

        scoreTracker.addScore(81.2); // Adding another score to the tracker
        //scoreTracker.addScore(88.7); // Adding another score to the tracker

        double median2 = scoreTracker.getMedianScore(); 
        // Recalculating the median score
        System.out.println("Median 2: " + median2); 
        // Printing the recalculated median
    
        // Output: 87.1
