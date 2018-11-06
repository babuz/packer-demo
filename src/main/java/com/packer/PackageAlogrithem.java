package com.packer;

public class PackageAlogrithem {
    public  static  class  product {

    }
    public static void main(String[] args) throws Exception {

        //75 : (1,85.31,€29) (2,14.55,€74) (3,3.98,€16) (4,26.24,€55)
        //(5,63.69,€52) (6,76.25,€75) (7,60.02,€74) (8,93.18,€35) (9,89.95,€78)

        //75 : (1,85.31,29) (2,14.55,74) (3,3.98,16) (4,26.24,55) (5,63.69,52) (6,76.25,75) (7,60.02,74) (8,93.18,35) (9,89.95,78)


        int cost[] = {29, 74, 16, 55,52,75,74,35,78};
        //int cost[] = {16, 74,55,74,52,75,29,78, 35};

        //double weights[] = {5.0, 4.0, 6.0, 3.0};
        //double weights[] = {3.98, 14.55,  26.24,60.02,63.69,76.25,85.31,89.95,93.18};
        double weights[] = {85.31, 14.55, 3.98, 26.24,63.69,76.25,60.02,93.18,89.95};

        int packsize = 75;

        System.out.println(execute(cost, weights, packsize));
    }

    public static void execute(PackageEntity packageEntity){
        Integer maxWeight = packageEntity.getMaxWeight();
        int numberOfItems = packageEntity.getPackageItems().size();

        int[][] matrix = new int[numberOfItems+1][maxWeight+1];

        for(int col=0; col <= maxWeight; col++){
            matrix[0][col] = 0;
        }

        for(int row=0; row <= numberOfItems; row++){
            matrix[row][0] = 0;
        }

        for(int item=1; item <= numberOfItems;item++){
            for(int weight=1; weight<= maxWeight; weight++){
                if(packageEntity.getPackageItems().get(item-1).getWeight()  <= weight){
                    matrix[item][weight]=Math.max (packageEntity.getPackageItems().get(item-1).getCost()
                                                                                                    +matrix[item-1][ (int) Math.round (weight- packageEntity.getPackageItems().get(item-1).getWeight())], matrix[item-1][weight]);
                }
                else
                {
                    matrix[item][weight]=matrix[item-1][weight];
                }
            }
        }

        //Printing the matrix
        for (int[] rows : matrix) {

            for (int col : rows) {
                System.out.format("%5d", col);
            }
            System.out.println();
        }
    }

    public static int execute(int cost[], double weights[], int packSize) {
        //Get the total number of items.
        //Could be weights.length or cost.length. Doesn't matter

        int NumberOfItems = weights.length;
        //Create a matrix.
        //Items are in rows and weight at in columns +1 on each side

        int[][] matrix = new int[NumberOfItems + 1][packSize + 1];

        //What if the knapsack's capacity is 0 - Set
        //all columns at row 0 to be 0

        for (int col = 0; col <= packSize; col++) {
            matrix[0][col] = 0;
        }

        //What if there are no items at home.
        //Fill the first row with 0
        for (int row = 0; row <= NumberOfItems; row++) {
            matrix[row][0] = 0;
        }

        for (int item=1;item<=NumberOfItems;item++){
            //Let's fill the values row by row
            for (int weight=1;weight<=packSize;weight++){
                //Is the current items weight less
                //than or equal to running weight

                if (weights[item-1]<=weight){

                    //Given a weight, check if the value of the current
                    //item + value of the item that we could afford
                    //with the remaining weight is greater than the value
                    //without the current item itself

                    matrix[item][weight]=Math.max (cost[item-1] +matrix[item-1][ (int) Math.round (weight-weights[item-1])], matrix[item-1][weight]);
                }
                else {
                            //If the current item's weight is more than the
                            //running weight, just carry forward the value
                            //without the current item
                        matrix[item][weight]=matrix[item-1][weight];
                }
            }
        }

        //Printing the matrix
        for (int[] rows : matrix) {

            for (int col : rows) {
                System.out.format("%5d", col);
            }
            System.out.println();
        }
        return matrix[NumberOfItems][packSize];
    }
}
