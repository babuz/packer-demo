package com.packer;

public class PackageItem {
    private  Double weight;
    private  Integer cost;
    private  int itemNumber;

    public PackageItem(int itemNumber, Double weight, Integer cost) {
        this.weight = weight;
        this.cost = cost;
        this.itemNumber = itemNumber;
    }

    public Double getWeight() {
        return weight;
    }

    public Integer getCost() {
        return cost;
    }

    public int getItemNumber() {
        return itemNumber;
    }

    @Override
    public String toString() {
        return "PackageItem{" +
                "weight=" + weight +
                ", cost=" + cost +
                ", itemNumber=" + itemNumber +
                '}';
    }
}
