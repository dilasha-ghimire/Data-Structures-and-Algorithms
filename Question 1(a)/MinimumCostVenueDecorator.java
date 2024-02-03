public class MinimumCostVenueDecorator{
    
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
            int minimum = matrix[i][0];

            for (int j = 0; j < k; j++){ // looping through each theme

                if (j == excludeColumn){
                    continue; // as adjacent venues should not have the same theme
                }

                if (matrix[i][j] < minimum) {
                    minimum = matrix[i][j];
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
            {20, 12, 8},
            {35, 12, 92},
            {9, 4, 70}
        };

        int minimunCost = findMinimumCost(costMatrix);
        System.out.println("Minimum cost:" + minimunCost);
    }
}