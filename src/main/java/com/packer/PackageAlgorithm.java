package com.packer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class PackageAlgorithm {
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

    }

    public static String execute(PackageEntity packageEntity){
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
                    matrix[item][weight] = Math.max (packageEntity.getPackageItems().get(item-1).getCost()
                                                                                                    +matrix[item-1][ (int) Math.round (weight- packageEntity.getPackageItems().get(item-1).getWeight())],
                                                                                                    matrix[item-1][weight]);
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

        return getMaximumProfit(packageEntity, maxWeight, numberOfItems, matrix);
    }

    private static String getMaximumProfit(PackageEntity packageEntity, Integer maxWeight, int numberOfItems, int[][] matrix) {
        Integer  maxCost = matrix[numberOfItems][maxWeight];
        System.out.println("Maximum Cost : " +  maxCost);
        System.out.println("Maximum Weight: " +  maxWeight);
        System.out.println("Maximum Items: " +  numberOfItems);

        //Printing the matrix
        List<Integer> itemNumber = new ArrayList<>();

        boolean itemFind = false;

        for(int row=numberOfItems; row>0; row--){
            for(int col=1;col<= maxWeight && !itemFind; col++){
                if( matrix[row-1][col] == maxCost){
                    itemFind = true;
                }
            }
            if(!itemFind && maxCost>0){
                itemNumber.add(packageEntity.getPackageItems().get(row-1).getItemNumber());
                maxCost = maxCost - packageEntity.getPackageItems().get(row-1).getCost();
            }
            itemFind  =false;
        }

        String returnValue = "" ;
        for (int i=0;i< itemNumber.size();i++){
            returnValue += itemNumber.get(i);
        }

        findMaxProfitAndLessWeight(itemNumber, packageEntity.getPackageItems());

        System.out.println("Return Value" + itemNumber);
        return  returnValue;
    }

    private static  List<Integer> findMaxProfitAndLessWeight(List<Integer> selectedItem, List<PackageItem> packageItems){
        Double maxWeight = 0d;
        Integer maxProfit = 0;
        for (Integer item : selectedItem ) {
            maxWeight +=packageItems.get(item-1).getWeight();
            maxProfit +=packageItems.get(item-1).getCost();
            System.out.println(packageItems.get(item-1).getCost());
            System.out.println(packageItems.get(item-1).getWeight());
        }

        System.out.println("maxWeight - " + maxWeight);
        System.out.println("maxProfit  - " + maxProfit);


        return selectedItem;
    }
}
