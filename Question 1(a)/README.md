## Question 1(a)
You are a planner working on organizing a series of events in a row of n venues. Each venue can be decorated with
one of the k available themes. However, adjacent venues should not have the same theme. The cost of decorating
each venue with a certain theme varies.

The costs of decorating each venue with a specific theme are represented by an n x k cost matrix. For example,
costs [0][0] represents the cost of decorating venue 0 with theme 0, and costs[1][2] represents the cost of
decorating venue 1 with theme 2. Your task is to find the minimum cost to decorate all the venues while adhering
to the adjacency constraint.

For example, given the input costs = [[1, 5, 3], [2, 9, 4]], the minimum cost to decorate all the venues is 5. One
possible arrangement is decorating venue 0 with theme 0 and venue 1 with theme 2, resulting in a minimum cost of
1 + 4 = 5. Alternatively, decorating venue 0 with theme 2 and venue 1 with theme 0 also yields a minimum cost of
3 + 2 = 5.

Write a function that takes the cost matrix as input and returns the minimum cost to decorate all the venues while
satisfying the adjacency constraint.

Please note that the costs are positive integers.

Example: Input: [[1, 3, 2], [4, 6, 8], [3, 1, 5]] 

Output: 10

Explanation: Decorate venue 0 with theme 0, venue 1 with theme 1, and venue 2 with theme 0. Minimum cost: 1 +
6 + 3 = 10

---
## Approach

I have used the approach of nested looping. The outer loop is for iterating through each venue (i), while the inner loop is for each theme (j). 

First you loop through each row, representing different venues. Then for each column in that row representing different themes, then you find the minimum of each row. Once you find the minimum, you store the index where you have found the minimum value at. In the next iteration, that index will be skipped as adjacent venues can't have the same theme. The loop continues until all the rows are completed and at the end you will get the minimum cost to decorate all the venues while
satisfying the adjacency constraint.
