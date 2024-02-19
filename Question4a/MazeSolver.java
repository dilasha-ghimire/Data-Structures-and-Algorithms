package Question4a;

import java.util.*;

class MazeSolver {
    // Define possible movement directions: right, down, left, up
    private static final int[][] DIRECTIONS = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

    // Define characters representing different elements of the maze
    private static final char START_POINT = 'S'; // Starting point
    private static final char WALL = 'W'; // Wall

    // Method to find the minimum moves to solve the maze
    public static int findMinMoves(char[][] grid) {
        // Get the dimensions of the grid
        int m = grid.length;
        int n = grid[0].length;

        // Create a visited array to keep track of visited cells
        boolean[][] visited = new boolean[m][n];

        // Create a queue to perform BFS
        Queue<Position> queue = new LinkedList<>();

        // Start from the starting point
        Position startPosition = null;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == START_POINT) {
                    startPosition = new Position(i, j);
                    break;
                }
            }
        }
        // Return -1 if starting point not found
        if (startPosition == null) {
            return -1;
        }

        // Mark the starting point as visited
        visited[startPosition.row][startPosition.col] = true;

        // Initialize the queue with the starting position
        queue.offer(startPosition);

        // Count the total number of keys in the maze
        int totalKeys = 0;
        for (char[] row : grid) {
            for (char cell : row) {
                if (Character.isLowerCase(cell)) {
                    totalKeys++;
                }
            }
        }

        // Initialize variables to track keys collected and moves made
        int keysCollected = 0;
        int moves = 0;

        // BFS traversal
        while (!queue.isEmpty()) {
            // Process cells level by level
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                // Get the current position from the queue
                Position currentPosition = queue.poll();

                // Check if all keys are collected
                if (keysCollected == totalKeys) {
                    return moves;
                }

                // Check for adjacent cells
                for (int[] direction : DIRECTIONS) {
                    int newRow = currentPosition.row + direction[0];
                    int newCol = currentPosition.col + direction[1];

                    // Check if the new cell is within the grid and not visited
                    if (isInBounds(m, n, newRow, newCol) && !visited[newRow][newCol] && grid[newRow][newCol] != WALL) {
                        // Mark the new cell as visited and add it to the queue
                        visited[newRow][newCol] = true;
                        if (Character.isLowerCase(grid[newRow][newCol])) {
                            keysCollected++;
                        }
                        queue.offer(new Position(newRow, newCol));
                    }
                }
            }

            // Increment moves after processing all cells at this level
            moves++;
        }

        // Return -1 if goal cannot be reached
        return -1;
    }

    // Check if a position is within the bounds of the grid
    private static boolean isInBounds(int m, int n, int row, int col) {
        return row >= 0 && row < m && col >= 0 && col < n;
    }

    // Define a Position class to represent a cell in the grid
    private static class Position {
        int row;
        int col;

        Position(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

    // Main method to test the MazeSolver
    public static void main(String[] args) {
        // Define sample grids
        char[][] grid = {
            {'S', 'P', 'q', 'P', 'P'},
            {'W', 'W', 'W', 'P', 'W'},
            {'r', 'P', 'Q', 'P', 'R'}
        };

        // Find minimum moves for the first grid
        int minMoves = findMinMoves(grid);
        // Print result
        if (minMoves != -1) {
            System.out.println("The minimum number of moves required to collect all keys is: " + minMoves);
        } 
        else {
            System.out.println("It is impossible to collect all the keys and reach the exit.");
        }

        // Define another sample grid
        char[][] grid1 = {
            {'S', 'P', 'q', 'P', 'P'},
            {'W', 'W', 'W', 'W', 'W'},
            {'r', 'P', 'Q', 'P', 'R'}
        };

        // Find minimum moves for the second grid
        minMoves = findMinMoves(grid1);
        // Print result
        if (minMoves != -1) {
            System.out.println("The minimum number of moves required to collect all keys is: " + minMoves);
        } 
        else {
            System.out.println("It is impossible to collect all the keys and reach the exit.");
        }
    }
}
