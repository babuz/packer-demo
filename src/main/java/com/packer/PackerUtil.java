package com.packer;

public class PackerUtil {
    private  static final Integer MAX_WEIGHT = 100;
    private  static final Integer MIN_WEIGHT = 0;

    public  static  boolean isWeightInRange(Integer value){
        if(MIN_WEIGHT<=0 || MAX_WEIGHT> 100){
            throw new RuntimeException("max weight out of range");
        }
        return  true;
    }

    public  static  boolean isValidRecord(String line){
        String[] record = line.split(":");
        if(record.length !=2 || record[0].isEmpty() || record[1].isEmpty()){
            throw new RuntimeException("Invalid data format : " + line);
        }

        return  true;
    }
}
