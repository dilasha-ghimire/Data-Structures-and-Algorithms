public class MinimumCostVenueDecorator {
    
    public static int findMinimumCost (int[][] matrix){

        // storing the row count of the matrix (venue count)
        int n = matrix.length;
        // storing the column count of the matrix (theme count)
        int k = matrix[0].length;
        // creating variable to store the minimum cost to decorate all the venues
        int storeMinimum = 0;
        // creating variable to store the theme that needs to be excluded 
        // as adjacent venues should not have the same theme
        int excludeColumn = -1;

        for (int i = 0; i < n; i++) { // looping through each venue
            int minimum = Integer.MAX_VALUE; // setting its initial value to the highest possible integer value

            for (int j = 0; j < k; j++){ // looping through each theme
                if (j != excludeColumn){
                    minimum = Math.min(minimum, matrix[i][j]);
                }
            }

            storeMinimum += minimum; // adding the minimum cost
            
            // Updating the `excludeColumn` variable separately ensures accurate exclusion
            // of the previously chosen column during comparisons in subsequent row iterations

            // Updating 'excludeColumn' inside the loop caused it to incorrectly skip columns 
            // in subsequent iterations within the same row

            for (int j = 0; j < k; j++) { // to find the column index with the minimum cost in the current row
                if (matrix[i][j] == minimum) {
                    excludeColumn = j; // to exclude this column in the next row
                    break;
                }
            }

        }
        return storeMinimum;
    }

    public static void main(String[] args) {
        
        int[][] costMatrix = {
            {1, 2, 3}, 
            {2, 1, 3}, 
            {3, 2, 1}
        };

        int minimumCost = findMinimumCost(costMatrix);
        System.out.println("Minimum cost: " + minimumCost + " units");
    }
}
