package com.packer;

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

    public static List<String> pack(String absoluteFilePath) throws ApiException {
        List<String> result = new ArrayList<>();

        try (Stream<String> stream = Files.lines(Paths.get(absoluteFilePath), Charset.forName("ISO-8859-15"))) {

            List<PackageEntity> packageEntities = stream
                    .map(x -> x.replaceAll("[^0-9:,.()]", " "))
                    .filter(x -> x.length() > 0)
                    .map(x ->  PackageEntityTranslator.translateToPackageEntities(x))
                    .collect(Collectors.toList());

            packageEntities.forEach( packageEntity -> {
                List<PackageItem> bestItems =  AlgorithmFactory.getPackingAlogrithm().execute(packageEntity);

                String output =  bestItems.stream()
                        .map( packageItem -> String.valueOf(packageItem.getItemNumber()))
                        .collect(Collectors.joining(","));

                output=output.isEmpty() ?"-": output;

                result.add(output);

            });
        } catch (Exception e) {
            throw new ApiException("Error processing file : " + absoluteFilePath, e);
        }

        log.info(result.toString());
        return result;
    }
}
