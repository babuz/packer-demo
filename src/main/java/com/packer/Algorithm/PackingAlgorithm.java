package com.packer.Algorithm;

import com.packer.Domain.PackageEntity;
import com.packer.Domain.PackageItem;

import java.util.List;

public interface PackingAlgorithm {
    List<PackageItem> execute(PackageEntity packageEntity);
}
