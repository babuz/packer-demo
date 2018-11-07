package com.packer;

import java.util.List;

public interface PackingAlgorithm {
    List<PackageItem> execute(PackageEntity packageEntity);
}
