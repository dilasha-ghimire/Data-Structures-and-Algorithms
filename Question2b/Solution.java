package Question2b; // Define the package name where the Solution class belongs

import java.util.*; // Import all classes from the java.util package

public class Solution { // Define the Solution class
    public static void main(String[] args) { // Start of the main method, entry point of the Java program
        Solution solution = new Solution(); // Create an instance of the Solution class

        int n = 5; // number of people
        int[][] meetings = { // Define an array of meetings
            {0, 2, 1}, // Meeting 1: person 0 and person 2 meet at time 1
            {1, 3, 2}, // Meeting 2: person 1 and person 3 meet at time 2
            {2, 4, 3}  // Meeting 3: person 2 and person 4 meet at time 3
        };
        int firstPerson = 0; // Define the first person who knows the secret

        List<Integer> result = solution.findAllPeople(n, meetings, firstPerson); // Call the method to find all people who know the secret
        System.out.println("People who know the secret: " + result); // Print the result
    }

    public List<Integer> findAllPeople(int n, int[][] meetings, int firstPerson) { // Method to find all people who know the secret
        // Sort meetings in increasing order of time
        Arrays.sort(meetings, (a, b) -> a[2] - b[2]); // Sort meetings based on the third column (time)

        // Group Meetings in increasing order of time
        Map<Integer, List<int[]>> sameTimeMeetings = new TreeMap<>(); // Create a map to store meetings happening at the same time
        for (int[] meeting : meetings) { // Iterate through each meeting
            int x = meeting[0], y = meeting[1], t = meeting[2]; // Extract meeting details
            sameTimeMeetings.computeIfAbsent(t, k -> new ArrayList<>()).add(new int[]{x, y}); // Group meetings by time
        }
    
        // Create graph
        UnionFind graph = new UnionFind(n); // Create an instance of the UnionFind class with 'n' nodes
        graph.unite(firstPerson, 0); // Unite the first person who knows the secret with node 0

        // Process in increasing order of time
        for (int t : sameTimeMeetings.keySet()) { // Iterate through meetings happening at the same time
            // Unite two persons taking part in a meeting
            for (int[] meeting : sameTimeMeetings.get(t)) { // Iterate through meetings happening at the current time
                int x = meeting[0], y = meeting[1]; // Extract persons involved in the meeting
                graph.unite(x, y); // Unite the persons involved in the meeting
            }
            
            // If any one knows the secret, both will be connected to 0.
            // If no one knows the secret, then reset.
            for (int[] meeting : sameTimeMeetings.get(t)) { // Iterate through meetings happening at the current time
                int x = meeting[0], y = meeting[1]; // Extract persons involved in the meeting
                if (!graph.connected(x, 0)) { // Check if any person knows the secret
                    // No need to check for y since x and y were united
                    graph.reset(x); // Reset the properties of person x
                    graph.reset(y); // Reset the properties of person y
                }
            }
        }
        
        // All those who are connected to 0 will know the secret
        List<Integer> ans = new ArrayList<>(); // Create a list to store people who know the secret
        for (int i = 0; i < n; ++i) { // Iterate through each person
            if (graph.connected(i, 0)) { // Check if the person is connected to the first person who knows the secret
                ans.add(i); // Add the person to the list of people who know the secret
            }
        }
        return ans; // Return the list of people who know the secret
    }
}

class UnionFind { // Define the UnionFind class
    private int[] parent; // Array to store parent nodes
    private int[] rank; // Array to store ranks of nodes

    public UnionFind(int n) { // Constructor to initialize UnionFind
        // Initialize parent and rank arrays
        parent = new int[n]; // Initialize parent array with size 'n'
        rank = new int[n]; // Initialize rank array with size 'n'
        for (int i = 0; i < n; ++i) { // Iterate through each node
            parent[i] = i; // Set parent of each node to itself initially
        }
    }

    public int find(int x) { // Method to find the parent of a node
        // Find parent of node x. Use Path Compression
        if (parent[x] != x) { // Check if x is not the parent of itself
            parent[x] = find(parent[x]); // Update the parent of x using path compression
        }
        return parent[x]; // Return the parent of x
    }

    public void unite(int x, int y) { // Method to unite two nodes
        // Unite two nodes x and y, if they are not already united
        int px = find(x); // Find the parent of node x
        int py = find(y); // Find the parent of node y
        if (px != py) { // Check if x and y have different parents
            // Union by Rank Heuristic
            if (rank[px] > rank[py]) { // Check if the rank of x's parent is greater than the rank of y's parent
                parent[py] = px; // Set the parent of y to x
            } 
            else if (rank[px] < rank[py]) { // Check if the rank of x's parent is less than the rank of y's parent
                parent[px] = py; // Set the parent of x to y
            } 
            else { // If ranks are equal
                parent[py] = px; // Set the parent of y to x
                rank[px] += 1; // Increase the rank of x's parent
            }
        }
    }

    public boolean connected(int x, int y) { // Method to check if two nodes are connected
        // Check if two nodes x and y are connected or not
        return find(x) == find(y); // Return true if the parents of x and y are the same
    }

    public void reset(int x) { // Method to reset the properties of a node
        // Reset the initial properties of node x
        parent[x] = x; // Set the parent of node x to itself
        rank[x] = 0; // Reset the rank of node x to 0
    }
}
