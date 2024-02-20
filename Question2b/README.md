# Secret Knowledge Discovery System

This Java program is designed to discover individuals who possess secret knowledge within a group of people based on their meeting interactions.

## Overview

The program utilizes a graph-based approach with union-find data structure to determine the individuals who possess the secret knowledge. It processes meetings in increasing order of time and identifies individuals who are connected to the first person who knows the secret.

## Input

The program expects the following input parameters:

 - **Number of people (n)**: Total number of individuals in the group.

 - **Meetings (meetings)**: An array representing meetings between individuals. Each meeting is represented as an array of three integers: **[person1, person2, time]**, where **person1** and **person2** are the indices of individuals participating in the meeting, and **time** is the time when the meeting occurs.

 - **First person (firstPerson)**: Index of the first person who knows the secret.

## Output

The program outputs a list of indices representing individuals who know the secret based on the provided input.

## Example

``` java

int n = 5; // Number of people
int[][] meetings = {
    {0, 2, 1}, // Meeting 1: person 0 and person 2 meet at time 1
    {1, 3, 2}, // Meeting 2: person 1 and person 3 meet at time 2
    {2, 4, 3}  // Meeting 3: person 2 and person 4 meet at time 3
};
int firstPerson = 0; // Index of the first person who knows the secret


// Output - 
// People who know the secret: [0, 2, 4]


```