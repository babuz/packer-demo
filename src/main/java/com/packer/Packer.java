package com.packer;

import com.packer.Algorithm.AlgorithmFactory;
import com.packer.Algorithm.PackageAlgorithm;
import com.packer.Domain.PackageEntity;
import com.packer.Util.PackageEntityTranslator;
import com.packer.Domain.PackageItem;
import com.packer.exception.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Packer {

    private static final Logger log = LoggerFactory.getLogger(PackageAlgorithm.class);
    private static final String VALID_CHARS = "[^0-9:,.()]";
    private static final String DELIMITER = ",";
    private static final String EMPTY_DELIMITER = "-";

    public static List<String> pack(String absoluteFilePath) throws ApiException {
        List<String> result = new ArrayList<>();
        List<PackageEntity> packageEntities;

        try (Stream<String> stream = Files.lines(Paths.get(absoluteFilePath), Charset.forName("ISO-8859-15"))) {

            packageEntities = stream
                    .map(x -> x.replaceAll(VALID_CHARS  , " "))
                    .filter(line -> line.length() > 0)
                    .map(line -> getTranslatedPackage(line) )
                    .collect(Collectors.toList());

            result  = getBestPackage(packageEntities);

        } catch (Exception e) {
            throw new ApiException("Error processing file : " + absoluteFilePath, e);
        }

        log.info(result.stream().collect(Collectors.joining("\n")));
        return result;
    }

    private static List<String> getBestPackage(List<PackageEntity> packageEntities) {
        List<String>  result = new ArrayList<>();

        packageEntities.forEach( packageEntity -> {
            List<PackageItem> bestItems =  AlgorithmFactory.getPackingAlogrithm().execute(packageEntity);

            String output =  bestItems.stream()
                    .map( packageItem -> String.valueOf(packageItem.getItemNumber()))
                    .collect(Collectors.joining(DELIMITER ));

            output=output.isEmpty() ?EMPTY_DELIMITER : output;

            result.add(output);
        });

        return result;
    }

    private static  PackageEntity getTranslatedPackage(String line){
        PackageEntity packageEntity = null;
        try{
            packageEntity = PackageEntityTranslator.translateToPackageEntities(line);
        }
        catch (ApiException ex){
            ex.printStackTrace();
        }
        return  packageEntity;
    }
}
