package com.packer.Domain;

import java.util.List;

public class PackageEntity {
    private Integer maxWeight;
    private List<PackageItem>  packageItems;

    public PackageEntity(Integer maxWeight, List<PackageItem> packageItems) {
        this.maxWeight = maxWeight;
        this.packageItems = packageItems;
    }

    public Integer getMaxWeight() {
        return maxWeight;
    }

    public List<PackageItem> getPackageItems() {
        return packageItems;
    }
}
