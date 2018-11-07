package com.packer.Util;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class PackerUtil {
    public static List<String> getRecordList(String lineAfterMaxWeight) {
        String[] records  =Pattern.compile("\\(|\\),\\(|\\)").split(lineAfterMaxWeight);
        List<String> recordList =  Arrays.stream(records).filter(x -> !x.trim().isEmpty()).collect(Collectors.toList());
        return recordList;
    }
}
