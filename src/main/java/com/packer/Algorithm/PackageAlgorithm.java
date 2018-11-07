package com.packer.Algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.packer.Domain.PackageEntity;
import com.packer.Domain.PackageItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PackageAlgorithm implements PackingAlgorithm {
    private static final Logger log = LoggerFactory.getLogger(PackageAlgorithm.class);

    public List<PackageItem> execute(PackageEntity packageEntity){
        Integer maxWeight = packageEntity.getMaxWeight();
        int numberOfItems = packageEntity.getPackageItems().size();

        int[][] valueMatrix = new int[numberOfItems+1][maxWeight+1];

        fillValueMatrixWithDefaultValue(maxWeight, numberOfItems, valueMatrix);

        for(int item=1; item <= numberOfItems;item++){
            for(int weight=1; weight<= maxWeight; weight++){
                //current Item weight less that running weight
                if(packageEntity.getPackageItems().get(item-1).getWeight()  <= weight){

                    valueMatrix[item][weight] = Math.max (packageEntity.getPackageItems().get(item-1).getCost()
                                                                                                    +valueMatrix[item-1][ (int) Math.round (weight- packageEntity.getPackageItems().get(item-1).getWeight())],
                                                                                                    valueMatrix[item-1][weight]);
                }
                else
                {
                    //current item weight more that existing just carry forward them
                    valueMatrix[item][weight]=valueMatrix[item-1][weight];
                }
            }
        }
        return getMaximumProfit(packageEntity,  numberOfItems, valueMatrix);
    }

    private void fillValueMatrixWithDefaultValue(Integer maxWeight, int numberOfItems, int[][] matrix) {
        for(int col=0; col <= maxWeight; col++){
            matrix[0][col] = 0;
        }

        for(int row=0; row <= numberOfItems; row++){
            matrix[row][0] = 0;
        }
    }

    /***
     * This method will  sequence method to collect the max profitable item from the list
     * @param packageEntity : this contains the packaged entity list with maximum weight
     * @param numberOfItems
     * @param matrix
     * @return
     */
    private static List<PackageItem> getMaximumProfit(PackageEntity packageEntity, int numberOfItems, int[][] matrix) {
        Integer  maxCost = matrix[numberOfItems][packageEntity.getMaxWeight()];

        List<Integer> itemNumber = new ArrayList<>();

        boolean itemFind = false;

        for(int row=numberOfItems; row>0; row--){
            for(int col=1;col<= packageEntity.getMaxWeight()&& !itemFind; col++){
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

    /***
     * This method will get  suitable and matching records which has less weight and max cost if there is any duplications
     * @param selectedItem : selected item numbers
     *  @param packageItems : the whole packaged items
     * @return return best package items list
     */
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
