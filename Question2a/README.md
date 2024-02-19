# Dress Equalizer

This Java program is designed to equalize the number of dresses among sewing machines. It takes an array representing the number of dresses in each machine and calculates the minimum number of moves required to ensure that each machine has the same number of dresses.

## Description

The Sewing Machine Equalizer program operates by taking an array representing the number of dresses in each machine as input. It then calculates the minimum number of moves required to ensure that each machine has the same number of dresses. This optimization is achieved through a simple algorithm that redistributes dresses, moving from left to right and then from right to left until all machines are equalized.

## Output 

Upon running the program, you will see the minimum number of moves required to equalize the dresses among the machines for several predefined input arrays. Each output line corresponds to a specific input array, and it indicates the minimum moves required. If equalization is not possible due to the total number of dresses not being divisible among the machines, the program will return -1 to indicate this limitation.

``` java

    int[] machines1 = {1, 0, 5};
    int[] machines2 = {5, 0, 1};
    int[] machines3 = {1, 3, 5};
    int[] machines4 = {5, 3, 1};
    int[] machines5 = {1, 6, 5};
    int[] machines6 = {1, 0, 5, 6};
    int[] machines7 = {1, 6, 5};
    int[] machines8 = {1, 1, 5};

    SewingMachine equalizer = new SewingMachine(); 

    System.out.println("Minimum moves required: " + equalizer.minMovesToEqualize(machines1)); 
    System.out.println();

    System.out.println("Minimum moves required: " + equalizer.minMovesToEqualize(machines2)); 
    System.out.println();

    System.out.println("Minimum moves required: " + equalizer.minMovesToEqualize(machines3)); 
    System.out.println();

    System.out.println("Minimum moves required: " + equalizer.minMovesToEqualize(machines4)); 
    System.out.println();

    System.out.println("Minimum moves required: " + equalizer.minMovesToEqualize(machines5)); 
    System.out.println();

    System.out.println("Minimum moves required: " + equalizer.minMovesToEqualize(machines6)); 
    System.out.println();

    System.out.println("Minimum moves required: " + equalizer.minMovesToEqualize(machines7)); 
    System.out.println();

    System.out.println("Minimum moves required: " + equalizer.minMovesToEqualize(machines8)); 

```

``` java

    // Outputs
    
    Initial Array: [1, 0, 5]
    Array after move 1: [1, 1, 4]
    Array after move 2: [1, 2, 3]
    Array after move 3: [1, 3, 2]
    Array after move 4: [2, 2, 2]
    Minimum moves required: 4

    Initial Array: [5, 0, 1]
    Array after move 1: [4, 1, 1]
    Array after move 2: [3, 2, 1]
    Array after move 3: [2, 3, 1]
    Array after move 4: [2, 2, 2]
    Minimum moves required: 4

    Initial Array: [1, 3, 5]
    Array after move 1: [1, 4, 4]
    Array after move 2: [2, 3, 4]
    Array after move 3: [2, 4, 3]
    Array after move 4: [3, 3, 3]
    Minimum moves required: 4

    Initial Array: [5, 3, 1]
    Array after move 1: [4, 4, 1]
    Array after move 2: [4, 3, 2]
    Array after move 3: [3, 4, 2]
    Array after move 4: [3, 3, 3]
    Minimum moves required: 4

    Initial Array: [1, 6, 5]
    Array after move 1: [1, 5, 6]
    Array after move 2: [1, 6, 5]
    Array after move 3: [2, 5, 5]
    Array after move 4: [2, 4, 6]
    Array after move 5: [2, 5, 5]
    Array after move 6: [3, 4, 5]
    Array after move 7: [3, 5, 4]
    Array after move 8: [4, 4, 4]
    Minimum moves required: 8

    Initial Array: [1, 0, 5, 6]
    Array after move 1: [1, 0, 4, 7]
    Array after move 2: [1, 0, 5, 6]
    Array after move 3: [1, 1, 4, 6]
    Array after move 4: [1, 1, 3, 7]
    Array after move 5: [1, 1, 4, 6]
    Array after move 6: [1, 2, 3, 6]
    Array after move 7: [1, 2, 4, 5]
    Array after move 8: [1, 3, 3, 5]
    Array after move 9: [1, 3, 4, 4]
    Array after move 10: [1, 4, 3, 4]
    Array after move 11: [2, 3, 3, 4]
    Array after move 12: [2, 3, 4, 3]
    Array after move 13: [2, 4, 3, 3]
    Array after move 14: [3, 3, 3, 3]
    Minimum moves required: 14

    Initial Array: [1, 6, 5]
    Array after move 1: [1, 5, 6]
    Array after move 2: [1, 6, 5]
    Array after move 3: [2, 5, 5]
    Array after move 4: [2, 4, 6]
    Array after move 5: [2, 5, 5]
    Array after move 6: [3, 4, 5]
    Array after move 7: [3, 5, 4]
    Array after move 8: [4, 4, 4]
    Minimum moves required: 8

    Minimum moves required: -1

```