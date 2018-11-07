package com.packer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PackageAlgorithm implements PackingAlgorithm {
    private static final Logger log = LoggerFactory.getLogger(PackageAlgorithm.class);

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

    public List<PackageItem> execute(PackageEntity packageEntity){
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
        /*//Printing the matrix
        for (int[] rows : matrix) {

            for (int col : rows) {
                System.out.format("%5d", col);
            }
            System.out.println();
        }*/

        return getMaximumProfit(packageEntity, maxWeight, numberOfItems, matrix);
    }

    private static List<PackageItem> getMaximumProfit(PackageEntity packageEntity, Integer maxWeight, int numberOfItems, int[][] matrix) {
        Integer  maxCost = matrix[numberOfItems][maxWeight];

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

        return findMaxProfitAndLessWeight(itemNumber, packageEntity.getPackageItems());
    }

    private static  List<PackageItem> findMaxProfitAndLessWeight(List<Integer> selectedItem, List<PackageItem> packageItems){
       List<PackageItem> choosenItems =   packageItems.stream().filter(  item -> selectedItem .contains(item.getItemNumber())).collect(Collectors.toList());

        List<PackageItem> bestItems = new ArrayList<>();
        List<Integer> overWeightItem = new ArrayList<>();

        choosenItems.forEach( item -> {
                    packageItems.forEach( existingItem -> {
                                if(existingItem.getCost() == item.getCost()
                                        && existingItem.getWeight() < item.getWeight()
                                        && !choosenItems.contains(existingItem)
                                ){
                                    overWeightItem.add(item.getItemNumber());
                                    bestItems.add(existingItem);
                                }
                    });
                }
        );

        choosenItems.removeIf( item -> overWeightItem.contains(item.getItemNumber()));
        bestItems.addAll(choosenItems);
        return bestItems;
    }
}
