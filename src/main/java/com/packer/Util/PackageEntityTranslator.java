package com.packer.Util;

import com.packer.Domain.PackageEntity;
import com.packer.Domain.PackageItem;
import com.packer.exception.ApiException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.packer.Util.PackerUtil.getRecordList;

public class PackageEntityTranslator {

    private static final int MAX_WEIGHT_POSITION = 0;
    private static final int RECORD_POSITION = 1;
    private static final  int ITEM_NUMBER_POSITION =0;
    private static final  int WEIGHT_POSITION = 1;
    private static final  int COST_POSITION = 2;
    private static final  String DATA_DELIMITER = ",";
    private static final  String MAX_WEIGHT_DATA_DELIMITER = ":";

    public  static PackageEntity translateToPackageEntities(String line) throws ApiException {
        PackageValidator.isValidRecord(line);

        List<PackageItem> packageItems = new ArrayList<>();

        String[] records = line.split(MAX_WEIGHT_DATA_DELIMITER );
        Integer maxWeight = Integer.valueOf(records[MAX_WEIGHT_POSITION].trim());
        String recordLine = records[RECORD_POSITION].trim();

        List<String> recordList = getRecordList(recordLine);

        recordList.forEach(record -> {
                    packageItems.add(createPackageItem(record));
                }
        );

       List<PackageItem>  filterdPackageItems =  packageItems.stream().filter( item -> item.getWeight() <= maxWeight).collect(Collectors.toList());

       PackageValidator.validate(maxWeight, filterdPackageItems);

        return new PackageEntity(maxWeight, filterdPackageItems);
    }

    private static  PackageItem createPackageItem(String packageRecord){

        String[] arrayItem = packageRecord.split(DATA_DELIMITER );
        return new PackageItem(Integer.parseInt(arrayItem[ITEM_NUMBER_POSITION].trim()),
                Double.parseDouble(arrayItem[WEIGHT_POSITION ].trim()),
                Integer.parseInt(arrayItem[COST_POSITION ].trim()));
    }
}
