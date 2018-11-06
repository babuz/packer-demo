package com.packer;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class PackerUtil {



    public  static  boolean isValidRecord(String line){
        String[] record = line.split(":");
        if(record.length !=2 || record[0].isEmpty() || record[1].isEmpty()){
            throw new RuntimeException("Invalid data format : " + line);
        }

       // Integer maxWeight = Integer.valueOf (line.split(":")[0].trim());

   /*     if( !isWeightInRange(maxWeight)){
            throw new RuntimeException("max weight out of range");
        }*/

        return  true;
    }

    /*public static  List<String> getRecordsBySplitingMatching(String lineAfterMaxWeight){
        String[] list =  lineAfterMaxWeight.split(Pattern.quote("),("))
    }
*/

        public  static  List<String> getRecordList(String lineAfterMaxWeight){
        int recordStartIndex = -1;
        int recordEndIndex = -1;

        List<String> recordList = new ArrayList<>();
        boolean startDelimiterFound = false;
        boolean endDelimiterFound = false;

        for(int i=0;i<lineAfterMaxWeight.length();i++){

            if(lineAfterMaxWeight.charAt(i) == '('  && startDelimiterFound== false) {
                startDelimiterFound = true;
                recordStartIndex = i;
            }

            if(lineAfterMaxWeight.charAt(i) == ')'  && startDelimiterFound == true) {
               endDelimiterFound = true;
               recordEndIndex = i;
            }

            if(startDelimiterFound && endDelimiterFound){
                recordList.add(lineAfterMaxWeight.substring(recordStartIndex+1,recordEndIndex));
                endDelimiterFound  = startDelimiterFound = false;
            }
        }
        return recordList;
    }
}
