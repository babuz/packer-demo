package com.packer.Util;

import com.packer.Domain.PackageItem;
import com.packer.exception.ApiException;

import java.util.List;

public class PackageValidator {
        private final static  int  MAX_ITEM_SIZE = 15;
        private final static  int  MAX_COST_ITEM_WEIGHT = 100;
        private final static  int  MAX_WEIGHT = 100;

    public static boolean isValidRecord(String line) {
        String[] record = line.split(":");
        if (record.length != 2 || record[0].isEmpty() || record[1].isEmpty()) {
            throw new RuntimeException("Invalid data format : " + line);
        }
        return true;
    }

        public static  void validate(Integer maxWeight, List<PackageItem> packageItems) throws ApiException{

            if(packageItems.size() > MAX_ITEM_SIZE){
                throw new ApiException("Exceeds maximum number of item");
            }

            if(maxWeight > MAX_WEIGHT){
                throw new ApiException("Max Weight exceeds the limitation");
            }

            for (PackageItem packageItem : packageItems
                 ) {
                if( packageItem.getCost() > MAX_COST_ITEM_WEIGHT  ){
                    throw new ApiException("Cost exceeds the limitation");
                }
                if( packageItem.getWeight() > MAX_COST_ITEM_WEIGHT  ){
                    throw new ApiException("Weight exceeds limitation");
                }
            }
        }
}
