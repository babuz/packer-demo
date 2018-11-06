package com.packer;

import com.packer.exception.ApiException;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import  java.util.stream.Stream;


public class Main {


    public  static  void main(String args[]){
        String filePath = "C:\\Babu\\rabo\\packer-demo\\inputs.txt";
        try {
            pack(filePath);
        }
        catch (ApiException ex){
            ex.printStackTrace();
        }
    }

    public  static String pack(String absoluteFilePath) throws  ApiException{
        try (Stream<String> stream = Files.lines(Paths.get(absoluteFilePath), Charset.forName("ISO-8859-15"))) {
            //stream.forEach(System.out::println);
            stream
                    .map( x -> x.replaceAll("[^0-9:,.()]"," "))
                    .filter(x -> x.length()>0)
                    .map( x -> parse(x))
                    .forEach(x ->  System.out.println(x));
        }
        catch (Exception e){
            throw new ApiException("Error processing file : " + absoluteFilePath ,e);
        }

        return "";
    }

    private  static String parse(String line) {
        //81 : (1,53.38,45) (2,88.62,98) (3,78.48,3) (4,72.30,76) (5,30.18,9) (6,46.34,48)
        PackerUtil.isValidRecord(line)
        Integer maxWeight = Integer.valueOf (line.split(":")[0].trim());
        PackerUtil.isWeightInRange(maxWeight );

        return "";
    }

    private static  String getPackage(Integer maxWeight,  Double[][][] item){
        return "";
    }
}
