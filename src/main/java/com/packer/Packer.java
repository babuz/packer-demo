package com.packer;

import com.packer.exception.ApiException;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Packer {

    public static String pack(String absoluteFilePath) throws ApiException {
        try (Stream<String> stream = Files.lines(Paths.get(absoluteFilePath), Charset.forName("ISO-8859-15"))) {

            List<PackageEntity> packageEntities = stream
                    .map(x -> x.replaceAll("[^0-9:,.()]", " "))
                    .filter(x -> x.length() > 0)
                    .map(x ->  PackageEntityTranslator.translateToPackageEntities(x))
                    .collect(Collectors.toList());

        } catch (Exception e) {
            throw new ApiException("Error processing file : " + absoluteFilePath, e);
        }

        return "";
    }
}
