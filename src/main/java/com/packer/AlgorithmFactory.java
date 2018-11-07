package com.packer;

public class AlgorithmFactory {
    private static  PackingAlgorithm packingAlgorithm = null;
    private static Object mutex = new Object();

    public  static PackingAlgorithm getPackingAlogrithm(){
        if(packingAlgorithm ==null) {
            synchronized (mutex) {
                packingAlgorithm = new PackageAlgorithm();
            }
        }
        return packingAlgorithm;
    }
}
