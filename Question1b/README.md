# Engine Time Calculator

The `EngineTimeCalculator` is a Java program that calculates the total time required to repair a set of engines based on their individual repair times and a split cost.

## Description

This program takes an array of engine repair times and a split cost as input. It then sorts the array of repair times in ascending order and simulates the repair process by splitting the repair workload between two workers, each taking the engine with the longest repair time from the remaining pool. The split cost represents the additional time it takes to perform the split operation.

## Example

``` java
int[] engineTimes = {1, 2, 3}; 
int splitCost = 1; 
int totalTime = EngineTimeCalculator.calculateTotalTime(engineTimes, splitCost);
System.out.println("Total time: " + totalTime);

// Output - Total time: 4