import com.packer.Domain.PackageItem;

import java.util.Arrays;
import java.util.List;

public class TestDataUtil {

    public static List<PackageItem> getWeightExceedData(){
        List<PackageItem> expectedRecordList = Arrays.asList(
                new PackageItem(1,200.38,4),
                new PackageItem(3,78.48,5),
                new PackageItem(4,200.30,76),
                new PackageItem(5,30.18,9),
                new PackageItem(6,46.34,48)
        );
        return expectedRecordList;
    }

    public static List<PackageItem> getCostExceedData(){
        List<PackageItem> expectedRecordList = Arrays.asList(
                new PackageItem(1,53.38,101),
                new PackageItem(3,78.48,2000),
                new PackageItem(4,72.30,76),
                new PackageItem(5,30.18,9),
                new PackageItem(6,46.34,48)
        );
        return expectedRecordList;
    }

    public static List<PackageItem> getData(){
        List<PackageItem> expectedRecordList = Arrays.asList(
                new PackageItem(1,53.38,45),
                new PackageItem(3,78.48,3),
                new PackageItem(4,72.30,76),
                new PackageItem(5,30.18,9),
                new PackageItem(6,46.34,48)
        );
        return expectedRecordList;
    }

    public static List<PackageItem> getDataMorePackageItems(){
        List<PackageItem> expectedRecordList = Arrays.asList(
                new PackageItem(1,53.38,45),
                new PackageItem(1,53.38,45),
                new PackageItem(1,53.38,45),
                new PackageItem(1,53.38,45),
                new PackageItem(1,53.38,45),
                new PackageItem(1,53.38,45),
                new PackageItem(1,53.38,45),
                new PackageItem(1,53.38,45),
                new PackageItem(1,53.38,45),
                new PackageItem(1,53.38,45),
                new PackageItem(1,53.38,45),
                new PackageItem(1,53.38,45),
                new PackageItem(1,53.38,45),
                new PackageItem(3,78.48,3),
                new PackageItem(4,72.30,76),
                new PackageItem(5,30.18,9),
                new PackageItem(6,46.34,48)
        );
        return expectedRecordList;
    }
}
