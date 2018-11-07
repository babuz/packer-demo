package com.packer.Util;

import java.util.ArrayList;
import java.util.List;

public class PackerUtil {

    public static List<String> getRecordList(String lineAfterMaxWeight) {
        int recordStartIndex = -1;
        int recordEndIndex = -1;

        List<String> recordList = new ArrayList<>();
        boolean startDelimiterFound = false;
        boolean endDelimiterFound = false;

        for (int i = 0; i < lineAfterMaxWeight.length(); i++) {

            if (lineAfterMaxWeight.charAt(i) == '(' && startDelimiterFound == false) {
                startDelimiterFound = true;
                recordStartIndex = i;
            }

            if (lineAfterMaxWeight.charAt(i) == ')' && startDelimiterFound == true) {
                endDelimiterFound = true;
                recordEndIndex = i;
            }

            if (startDelimiterFound && endDelimiterFound) {
                recordList.add(lineAfterMaxWeight.substring(recordStartIndex + 1, recordEndIndex));
                endDelimiterFound = startDelimiterFound = false;
            }
        }
        return recordList;
    }
}
