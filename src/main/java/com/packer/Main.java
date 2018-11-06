package com.packer;

import com.packer.exception.ApiException;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.packer.PackerUtil.getRecordList;


public class Main {

    private  static final Integer MAX_WEIGHT = 100;
    private  static final Integer MIN_WEIGHT = 0;

    public static void main(String args[]) {
        String filePath = "C:\\Babu\\rabo\\packer-demo\\inputs.txt";
        try {
            pack(filePath);
        } catch (ApiException ex) {
            ex.printStackTrace();
        }
    }

    public static String pack(String absoluteFilePath) throws ApiException {
        try (Stream<String> stream = Files.lines(Paths.get(absoluteFilePath), Charset.forName("ISO-8859-15"))) {

            List<List<PackageItem>> listAllLineRecords = stream
                    .map(x -> x.replaceAll("[^0-9:,.()]", " "))
                    .filter(x -> x.length() > 0)
                    .map(x -> parse(x))
                    .collect(Collectors.toList());

        } catch (Exception e) {
            throw new ApiException("Error processing file : " + absoluteFilePath, e);
        }

        return "";
    }

    private static List<PackageItem> parse(String line) {
        //81 : (1,53.38,45) (2,88.62,98) (3,78.48,3) (4,72.30,76) (5,30.18,9) (6,46.34,48)
        List<PackageItem> packageItems = new ArrayList<>();

        PackerUtil.isValidRecord(line);
        String[] records = line.split(":");
        Integer maxWeight = Integer.valueOf(line.split(":")[0].trim());
        String recordLine = line.split(":")[1].trim();
        List<String> recordList = getRecordList(recordLine);
        recordList.forEach(record -> {
                    String[] arrayItem = record.split(",");
                    packageItems.add(new PackageItem(Integer.parseInt(arrayItem[0]), Double.parseDouble(arrayItem[1]), Integer.parseInt(arrayItem[2])));
                }
        );

        return packageItems;
    }

    private static void getValidPackage(Double packageMaxWeight, List<PackageItem> packageItems) {
        packageItems.sort(new Comparator<PackageItem>() {
            @Override
            public int compare(PackageItem o1, PackageItem o2) {
                return o2.getCostPercentage().compareTo(o1.getCostPercentage());
            }
        });

        List<Integer> selectedItem = new ArrayList();
        Double totalCost = 0d;

        for (PackageItem item : packageItems) {
            if (packageMaxWeight - item.getWeight()>= 0) {
                packageMaxWeight = packageMaxWeight- item.getWeight();
                selectedItem.add(item.getItemNumber());
                totalCost+= item.getCost();
            }
        }

        System.out.println("Total Cost" + totalCost);
        selectedItem.forEach(System.out::println);
    }

    public static  void napsack(){

    }

    public static void packGreedyValid(Double packageMaxWeight, Double[] weights, int[] cost) {
        PackageItem[] items = new PackageItem[weights.length];

        for (int i = 0; i < weights.length; i++) {
            items[i] = new PackageItem(i + 1, weights[i], cost[i]);
        }

        Arrays.sort(items, new Comparator<PackageItem>() {
            @Override
            public int compare(PackageItem o1, PackageItem o2) {
                return o2.getCostPercentage().compareTo(o1.getCostPercentage());
            }
        });

        List<Integer> selectedItem = new ArrayList();
        Double capacity = packageMaxWeight;

        for (PackageItem item : items) {
            Double currentWeight = item.getWeight();

            if (capacity - currentWeight >= 0) {
                capacity = capacity - currentWeight;
                selectedItem.add(item.getItemNumber());
            }
        }

        selectedItem.forEach(System.out::println);
    }


    public static void packGreedy(Double packageMaxWeight, Double[] weights, int[] cost) {
        PackageItem[] items = new PackageItem[weights.length];

        for (int i = 0; i < weights.length; i++) {
            items[i] = new PackageItem(i + 1, weights[i], cost[i]);
        }

        Arrays.sort(items, new Comparator<PackageItem>() {
            @Override
            public int compare(PackageItem o1, PackageItem o2) {
                return o2.getCostPercentage().compareTo(o1.getCostPercentage());
            }
        });

        //int[] selectedItem = new int[weights.length];
        List<Integer> selectedItem = new ArrayList();
        int i = 0;
        Double capacity = packageMaxWeight;

        for (PackageItem item : items) {
            Double currentWeight = item.getWeight();
            int currentCost = item.getCost();

            if (capacity - currentWeight >= 0) {
                capacity = capacity - currentWeight;
                selectedItem.add(item.getItemNumber());
                i++;
            }
        }

        selectedItem.forEach(System.out::println);

    }


    /*public static void packDynamic(int packageMaxWeight, Double[] weights, int[] cost) {
        int totalItemCount = weights.length;
        Double[][] matrix = new Double[totalItemCount + 1][packageMaxWeight + 1];

        for (int column = 0; column <= packageMaxWeight; column++) {
            matrix[0][column] = 0d;
        }

        for (int row = 0; row <= totalItemCount; row++) {
            matrix[row][0] = 0d;
        }

        for (int item = 1; item <= totalItemCount; item++) {
            for (int weight = 1; weight <= packageMaxWeight; weight++) {
                if (weights[item - 1] <= weight) {
                    matrix[item][weight] = Math.max(cost[item - 1] + matrix[item - 1][(weight - (int) Math.ceil(weights[item - 1]))], matrix[item - 1][weight]);
                } else {
                    matrix[item][weight] = matrix[item - 1][weight];
                }
            }
        }

        for (Double[] rows : matrix) {

            for (Double col : rows) {

                System.out.format("%5d", col);
            }
            System.out.println();
        }
    }*/

    private static String getPackage(Integer maxWeight, Double[][][] item) {
        return "";
    }
}
