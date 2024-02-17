import java.util.*;

// Class to represent an Edge in the graph
class Edge {
    int source, destination, weight;

    Edge(int source, int destination, int weight) {
        this.source = source;               // Initialize source vertex of the edge
        this.destination = destination;     // Initialize destination vertex of the edge
        this.weight = weight;               // Initialize weight of the edge
    }
}

// Class to represent a subset for union-find
class UnionFind {
    int[] parent, rank;

    UnionFind(int n) {
        parent = new int[n];    // Initialize parent array for each vertex
        rank = new int[n];      // Initialize rank array for each vertex
        for (int i = 0; i < n; i++) {
            parent[i] = i;      // Initially, each vertex is its own parent (representative)
            rank[i] = 0;        // Initially, each vertex has rank 0
        }
    }

    int find(int i) {
        if (parent[i] != i)
            parent[i] = find(parent[i]);   // Path compression: update parent to the root of the set
        return parent[i];                   // Return the parent of vertex i
    }

    void union(int x, int y) {
        int xroot = find(x);    // Find the root of the set containing vertex x
        int yroot = find(y);    // Find the root of the set containing vertex y

        if (rank[xroot] < rank[yroot])      // Attach smaller rank tree under root of high rank tree
            parent[xroot] = yroot;
        else if (rank[xroot] > rank[yroot])
            parent[yroot] = xroot;
        else {
            parent[yroot] = xroot;
            rank[xroot]++;                  // If ranks are the same, make one as root and increment its rank
        }
    }
}

class Graph {
    int V, E; // V -> no. of vertices & E -> no.of edges
    List<Edge> edges; // collection of all edges

    // Constructor
    Graph(int v, int e) {
        V = v;          // Initialize number of vertices
        E = e;          // Initialize number of edges
        edges = new ArrayList<>(); // Initialize list to store edges
    }

    // Function to construct MST using Kruskal's algorithm with a minimum heap
    void KruskalMSTUsingHeap() {
        PriorityQueue<Edge> minHeap = new PriorityQueue<>(Comparator.comparingInt(edge -> edge.weight)); // Initialize priority queue with edges sorted by weight
        Collections.addAll(minHeap, edges.toArray(new Edge[0])); // Add all edges to the priority queue

        UnionFind uf = new UnionFind(V); // Create a UnionFind object to detect cycles

        int edgeCount = 0; // Counter for edges included in MST
        while (edgeCount < V - 1 && !minHeap.isEmpty()) { // Continue until V-1 edges are selected or heap is empty
            Edge nextEdge = minHeap.poll(); // Get the next minimum weight edge from the heap
            int x = uf.find(nextEdge.source); // Find the representative of the source vertex
            int y = uf.find(nextEdge.destination); // Find the representative of the destination vertex

            if (x != y) { // If including this edge does not form a cycle
                uf.union(x, y); // Union the sets containing source and destination vertices
                System.out.println(nextEdge.source + " -- " + nextEdge.destination + " == " + nextEdge.weight); // Print the edge added to MST
                edgeCount++; // Increment the edge count
            }
        }
    }
}

class MinHeap {
    private int[] heap;
    private int size;
    private int maxSize;

    // Constructor to initialize the heap
    public MinHeap(int maxSize) {
        this.maxSize = maxSize;
        this.size = 0;
        heap = new int[this.maxSize + 1];
        heap[0] = Integer.MIN_VALUE; // Set the first element to the smallest possible integer
    }

    // Method to get the parent index of a given index
    private int parent(int pos) {
        return pos / 2;
    }

    // Method to get the left child index of a given index
    private int leftChild(int pos) {
        return (2 * pos);
    }

    // Method to get the right child index of a given index
    private int rightChild(int pos) {
        return (2 * pos) + 1;
    }

    // Method to check if a node is a leaf
    private boolean isLeaf(int pos) {
        return pos >= (size / 2) && pos <= size;
    }

    // Method to swap two nodes of the heap
    private void swap(int fpos, int spos) {
        int tmp;
        tmp = heap[fpos];
        heap[fpos] = heap[spos];
        heap[spos] = tmp;
    }

    // Method to check if the heap is empty
    public boolean isEmpty() {
        return size == 0;
    }

    // Method to heapify the node at a given position
    private void minHeapify(int pos) {
        if (!isLeaf(pos)) {
            if (heap[pos] > heap[leftChild(pos)] || heap[pos] > heap[rightChild(pos)]) {
                if (heap[leftChild(pos)] < heap[rightChild(pos)]) {
                    swap(pos, leftChild(pos));
                    minHeapify(leftChild(pos));
                } else {
                    swap(pos, rightChild(pos));
                    minHeapify(rightChild(pos));
                }
            }
        }
    }

    // Method to insert a new element into the heap
    public void insert(int element) {
        if (size >= maxSize) {
            return;
        }
        heap[++size] = element; // Add the new element to the end of the heap
        int current = size;
        while (heap[current] < heap[parent(current)]) { // Maintain heap property by swapping with parent if necessary
            swap(current, parent(current));
            current = parent(current);
        }
    }

    // Method to remove and return the minimum element from the heap
    public int remove() {
        int popped = heap[1]; // Store the root element (minimum)
        heap[1] = heap[size--]; // Replace root with last element
        minHeapify(1); // Restore heap property
        return popped; // Return the removed minimum element
    }

    // Method to print the heap
    public void print() {
        for (int i = 1; i <= size / 2; i++) { // Iterate through heap nodes
            System.out.print(" PARENT : " + heap[i] + " LEFT CHILD : " + heap[2 * i] + " RIGHT CHILD : " + heap[2 * i + 1]); // Print parent and children
            System.out.println();
        }
    }
}

class PriorityQueueImpl {
    private MinHeap minHeap;

    public PriorityQueueImpl(int maxSize) {
        minHeap = new MinHeap(maxSize); // Initialize a MinHeap object
    }

    public void enqueue(int priority) {
        minHeap.insert(priority); // Insert an element into the priority queue
    }

    public int dequeue() {
        return minHeap.remove(); // Remove and return the minimum element from the priority queue
    }

    public boolean isEmpty() {
        return minHeap.isEmpty(); // Check if the priority queue is empty
    }

    public void printQueue() {
        minHeap.print(); // Print the priority queue
    }
}

public class Main {
    public static void main(String[] args) {
        int V = 4; // Number of vertices in graph
        int E = 5; // Number of edges in graph
        Graph graph = new Graph(V, E); // Create a graph object with V vertices and E edges

        // Add edges
        graph.edges.add(new Edge(0, 1, 10)); // Add edge from vertex 0 to vertex 1 with weight 10
        graph.edges.add(new Edge(0, 2, 6));  // Add edge from vertex 0 to vertex 2 with weight 6
        graph.edges.add(new Edge(0, 3, 5));  // Add edge from vertex 0 to vertex 3 with weight 5
        graph.edges.add(new Edge(1, 3, 15)); // Add edge from vertex 1 to vertex 3 with weight 15
        graph.edges.add(new Edge(2, 3, 4));  // Add edge from vertex 2 to vertex 3 with weight 4

        System.out.println("---- Kruskal's algorithm to find the Minimum Spanning Tree (MST) of a given graph using a minimum heap ----");

        // Find and print the MST using Kruskal's algorithm with minimum heap
        graph.KruskalMSTUsingHeap(); // Call Kruskal's algorithm method

        // Demonstrate the priority queue implementation
        PriorityQueueImpl priorityQueue = new PriorityQueueImpl(15); // Create a priority queue object with maximum size 15
        priorityQueue.enqueue(5); // Enqueue element 5
        priorityQueue.enqueue(3); // Enqueue element 3
        priorityQueue.enqueue(17); // Enqueue element 17
        priorityQueue.enqueue(10); // Enqueue element 10
        priorityQueue.enqueue(84); // Enqueue element 84
        priorityQueue.enqueue(19); // Enqueue element 19
        priorityQueue.enqueue(6); // Enqueue element 6
        priorityQueue.enqueue(22); // Enqueue element 22
        priorityQueue.enqueue(9); // Enqueue element 9

        System.out.println();
        System.out.println("---- Priority Queue using a minimum heap ----");

        System.out.println("The Priority Queue is:");
        priorityQueue.printQueue(); // Print the priority queue

        System.out.println("Dequeued element: " + priorityQueue.dequeue()); // Dequeue and print the minimum element
        System.out.println("Dequeued element: " + priorityQueue.dequeue()); // Dequeue and print the minimum element

        System.out.println("The Priority Queue is:");
        priorityQueue.printQueue(); // Print the priority queue
    }
}
