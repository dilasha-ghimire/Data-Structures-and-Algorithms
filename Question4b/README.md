# Closest Values in Binary Tree

This Java program implements a method to find the closest values to a target in a binary tree. It utilizes an inorder traversal approach to explore the tree and identify the closest values to the given target.

## Implementation Details

1. The `Node` class defines a node in the binary tree structure. Each node contains an integer value as its data and references to its left and right child nodes.

2. The `ClosestValues` class provides a method closestValues that takes the root of the binary tree, the target value, and the number of closest values required as input parameters. It returns a list of integers representing the closest values to the target.

3. The `inorder` method is a helper method for inorder traversal of the binary tree. During traversal, it calculates the difference between the target and the current node's value, and maintains a queue of closest values found so far.

## Examples

        Node root = new Node(5); 
        root.leftChildNode = new Node(3); 
        root.rightChildNode = new Node(7); 
        root.leftChildNode.leftChildNode = new Node(2); 
        root.leftChildNode.rightChildNode = new Node(4); 
        root.rightChildNode.leftChildNode = new Node(6);
        root.rightChildNode.rightChildNode = new Node(8); 

        double target = 3.8; 
        int numberOfValues = 4;

        Output - [2, 3, 4, 5]

---

        Node root = new Node(10); 
        root.leftChildNode = new Node(5); 
        root.rightChildNode = new Node(15); 
        root.leftChildNode.leftChildNode = new Node(3); 
        root.leftChildNode.rightChildNode = new Node(7); 
        root.rightChildNode.leftChildNode = new Node(12); 
        root.rightChildNode.rightChildNode = new Node(17); 

        double target = 9.5; 
        int numberOfValues = 3;

        Output - [7, 10, 12] 
