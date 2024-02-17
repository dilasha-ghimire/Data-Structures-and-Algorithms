# Overview

This Java implementation offers two main functionalities:

1. Finding the Minimum Spanning Tree (MST) of a graph using Kruskal's algorithm.
2. Implementing a priority queue using a minimum heap.

### Classes

- **Edge**: Represents a connection between two vertices in the graph. Each edge has a source, destination, and weight.
- **UnionFind**: Implements the Union-Find (disjoint-set) data structure, crucial for efficiently detecting cycles during Kruskal's algorithm.
- **Graph**: Represents the graph structure and contains the implementation of Kruskal's algorithm using a minimum heap.
- **MinHeap**: Implements the minimum heap data structure, which is utilized for priority queue operations.
- **PriorityQueueImpl**: Wraps the MinHeap class to provide a priority queue interface.
- **Main**: Contains the main method where the graph is instantiated, edges are added, and Kruskal's algorithm is invoked to find the MST. It also demonstrates the usage of the priority queue implementation.

### Functionality

- **Kruskal's Algorithm**: The `KruskalMSTUsingHeap` method in the Graph class finds the MST by sorting the edges based on weight using a priority queue (min heap). It then iteratively adds the next minimum weighted edge to the MST if it does not create a cycle.
- **Priority Queue**: The `PriorityQueueImpl` class demonstrates the usage of the MinHeap class as a priority queue by providing methods to enqueue, dequeue, and print the queue.

## Example

```java

Graph graph = new Graph(4, 5);

graph.edges.add(new Edge(0, 1, 10));
graph.edges.add(new Edge(0, 2, 6));
graph.edges.add(new Edge(0, 3, 5));
graph.edges.add(new Edge(1, 3, 15));
graph.edges.add(new Edge(2, 3, 4));

graph.KruskalMSTUsingHeap();

PriorityQueueImpl priorityQueue = new PriorityQueueImpl(15);
priorityQueue.enqueue(5);
priorityQueue.enqueue(3);
priorityQueue.enqueue(17);
priorityQueue.enqueue(10);
priorityQueue.enqueue(84);
priorityQueue.enqueue(19);
priorityQueue.enqueue(6);
priorityQueue.enqueue(22);
priorityQueue.enqueue(9);

priorityQueue.printQueue();
System.out.println("Dequeued element: " + priorityQueue.dequeue());
System.out.println("Dequeued element: " + priorityQueue.dequeue());
priorityQueue.printQueue();
