# Maze Solver

The Maze Solver is a Java program designed to find the minimum number of moves required to collect all keys in a maze and reach the exit. It implements a breadth-first search (BFS) algorithm to explore the maze efficiently.

## Introduction

The Maze Solver program takes a 2D grid representing a maze as input. The maze contains various elements such as walls, keys, and the starting point. The objective is to navigate through the maze, collecting all keys, and reaching the exit while minimizing the number of moves.

The program employs a breadth-first search (BFS) algorithm to explore the maze. It starts from the designated starting point and iteratively explores adjacent cells, marking them as visited and enqueueing them for further exploration. The BFS continues until all keys are collected or until it's impossible to reach the exit.

### Example

Consider the following maze:

``` java

['S', 'P', 'q', 'P', 'P']
['W', 'W', 'W', 'P', 'W']
['r', 'P', 'Q', 'P', 'R']

``` 

- 'S' represents the starting point.
- 'W' represents walls that cannot be crossed.
- 'P', 'Q', 'R', etc., represent different paths.
- 'q', 'r', etc., represent keys.

The Maze Solver would find the minimum number of moves required to collect all keys and reach the exit in this maze.

### Output

The program outputs the minimum number of moves required to collect all keys and reach the exit. If it's impossible to collect all keys and reach the exit, it outputs a message indicating the impossibility.

## Examples

Consider the following maze grids:

``` java

Grid 1:

['S', 'P', 'q', 'P', 'P']
['W', 'W', 'W', 'P', 'W']
['r', 'P', 'Q', 'P', 'R']

Grid 2:

['S', 'P', 'q', 'P', 'P']
['W', 'W', 'W', 'W', 'W']
['r', 'P', 'Q', 'P', 'R']

// The output of the Maze Solver for these grids would be:

// The minimum number of moves required to collect all keys is: 8
// It is impossible to collect all the keys and reach the exit.

```
