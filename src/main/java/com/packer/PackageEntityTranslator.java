package com.packer;

import java.util.ArrayList;
import java.util.List;

import static com.packer.PackerUtil.getRecordList;

public class PackageEntityTranslator {

    private  static final Integer MAX_WEIGHT = 100;
    private  static final Integer MIN_WEIGHT = 0;
    private static final int MAX_WEIGHT_POSITION = 0;
    private static final int RECORD_POSITION = 1;
    private static final  int ITEM_NUMBER_POSITION =0;
    private static final  int WEIGHT_POSITION = 1;
    private static final  int COST_POSITION = 2;
    private static final  String DATA_DELIMITER = ",";
    private static final  String MAX_WEIGHT_DATA_DELIMITER = ":";

    public  static  PackageEntity translateToPackageEntities(String line){
        //81 : (1,53.38,45) (2,88.62,98) (3,78.48,3) (4,72.30,76) (5,30.18,9) (6,46.34,48)
        List<PackageItem> packageItems = new ArrayList<>();

        PackerUtil.isValidRecord(line);
        String[] records = line.split(MAX_WEIGHT_DATA_DELIMITER );
        Integer maxWeight = Integer.valueOf(records[MAX_WEIGHT_POSITION].trim());
        String recordLine = records[RECORD_POSITION].trim();
        List<String> recordList = getRecordList(recordLine);
        recordList.forEach(record -> {
                    packageItems.add(createPackageItem(record));
                }
        );

        return new PackageEntity(maxWeight, packageItems);
    }

    private static  PackageItem createPackageItem(String packageRecord){

        String[] arrayItem = packageRecord.split(DATA_DELIMITER );
        return new PackageItem(Integer.parseInt(arrayItem[ITEM_NUMBER_POSITION].trim()),
                Double.parseDouble(arrayItem[WEIGHT_POSITION ].trim()),
                Integer.parseInt(arrayItem[COST_POSITION ].trim()));
    }
}
