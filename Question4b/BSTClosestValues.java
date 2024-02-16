package Question4b;

import java.util.*; // Importing necessary packages

class Node { // Defining a class Node
    int parentNode; // Declaring an integer variable parentNode
    Node leftChildNode, rightChildNode; // Declaring Node type variables leftChildNode and rightChildNode

    public Node(int parentNode) { // Constructor with an integer parameter
        this.parentNode = parentNode; // Assigning the parameter value to the parentNode variable
        leftChildNode = rightChildNode = null; // Initializing leftChildNode and rightChildNode to null
    }
}

public class BSTClosestValues { // Defining a public class BSTClosestValues
    public List<Integer> closestValues(Node root, double target, int numberOfValues) { // Method to find closest values to a target in a binary tree
        Queue<Integer> closestValues = new LinkedList<>(); // Creating a queue to store closest values
        inorder(root, target, closestValues, numberOfValues); // Calling the inorder traversal method
        return new ArrayList<>(closestValues); // Returning the list of closest values as an ArrayList
    }

    private void inorder(Node root, double target, Queue<Integer> closestValues, int numberOfValues) { // Method for inorder traversal
        if (root == null) // Base case: if root is null, return
            return;

        inorder(root.leftChildNode, target, closestValues, numberOfValues); // Recursively traverse left subtree

        if (closestValues.size() < numberOfValues) { // If number of closest values found is less than required
            closestValues.offer(root.parentNode); // Add current node's value to the queue
        } 
        else { // If number of closest values found is equal to required
            double present = Math.abs(target - root.parentNode); // Calculate difference between target and current node's value
            double outermost = Math.abs(target - closestValues.peek()); // Calculate difference between target and farthest value in the queue
            if (present < outermost) { // If current node is closer to the target than the farthest value in the queue
                closestValues.poll(); // Remove the farthest value from the queue
                closestValues.offer(root.parentNode); // Add current node's value to the queue
            }
        }

        inorder(root.rightChildNode, target, closestValues, numberOfValues); // Recursively traverse right subtree
    }

    public static void main(String[] args) { // Main method
        BSTClosestValues values = new BSTClosestValues(); // Creating an instance of the ClosestValues class

        Node root = new Node(5); // Creating the root node with value 5
        root.leftChildNode = new Node(3); // Creating left child node with value 3
        root.rightChildNode = new Node(7); // Creating right child node with value 7
        root.leftChildNode.leftChildNode = new Node(2); // Creating left child node of left child node with value 2
        root.leftChildNode.rightChildNode = new Node(4); // Creating right child node of left child node with value 4
        root.rightChildNode.leftChildNode = new Node(6); // Creating left child node of right child node with value 6
        root.rightChildNode.rightChildNode = new Node(8); // Creating right child node of right child node with value 8

        double target = 3.8; // Setting the target value
        int numberOfValues = 4; // Setting the number of closest values required
        List<Integer> output = values.closestValues(root, target, numberOfValues); // Finding closest values
        System.out.println("The " + numberOfValues + " closest values to " + target + " are: " + output); // Printing the closest values
    }
}
