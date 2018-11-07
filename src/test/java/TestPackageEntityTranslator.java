import com.packer.*;
import com.packer.Algorithm.AlgorithmFactory;
import com.packer.Domain.PackageEntity;
import com.packer.Util.PackageEntityTranslator;
import com.packer.Domain.PackageItem;
import com.packer.Util.PackerUtil;
import com.packer.exception.ApiException;
import mockit.Deencapsulation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestPackageEntityTranslator {

    @DisplayName("Throw Api exception for wrong file path")
    @Test
    void shouldThrowFileNotFoundException() {
        assertThrows(ApiException.class, () -> {
            Packer.pack("c:/x/dd/test.txt");
        });
    }

    @DisplayName("Throw Api exception  for invalid argument ")
    @Test
    void shouldThrowExceptionForInvalidData() {
        Throwable exception = assertThrows(RuntimeException.class, () -> {
            Deencapsulation.invoke(PackageEntityTranslator.class, "translateToPackageEntities", "(1,53.38,45)");
        });
        assertThat(exception.getMessage(), containsString("Invalid data format "));
    }

    @DisplayName("Parse invalid max weight data throw exception")
    @Test
    void shouldThrowExceptionForInvalidMaxWeightData() {
        Throwable exception = assertThrows(NumberFormatException.class, () -> {
            Deencapsulation.invoke(PackageEntityTranslator.class, "translateToPackageEntities", "test: (1,53.38,45)");
        });
        assertThat(exception.getMessage(), containsString("test"));
    }

    @DisplayName("Successfully get valid records  after  removing max Weight ")
    @Test
    void shouldGetValidRecords() {
        String validRecord = "(1,53.38,45),(2,53.3,25),(3,23.18,15)";
        List<String> expectedRecordList = Arrays.asList("1,53.38,45","2,53.3,25","3,23.18,15");
        List<String> actualList = PackerUtil.getRecordList(validRecord);
        assertEquals(expectedRecordList.size(),actualList.size());
    }

    @DisplayName("Successfully get valid package items ")
    @Test
    void shouldGetValidPackageItems() {
        String line = "81 : (1,53.38,45) (2,88.62,98) (3,78.48,3) (4,72.30,76) (5,30.18,9) (6,46.34,48)";

        List<PackageItem> expectedRecordList = this.getData();
        PackageEntity expectedPackageEntity = new PackageEntity(81,expectedRecordList);
        PackageEntity packageEntity = Deencapsulation.invoke(PackageEntityTranslator.class, "translateToPackageEntities", line);

        assertEquals(expectedPackageEntity.getMaxWeight(), packageEntity.getMaxWeight());
        assertEquals(expectedPackageEntity.getPackageItems().size(), packageEntity.getPackageItems().size());
    }

    @DisplayName("Successfully Get Valid Records Package Items")
    @Test
    void shouldRunSuccessfullyGetValidPackageItems() {
        String line = "81 : (1,53.38,45) (2,88.62,98) (3,78.48,3) (4,72.30,76) (5,30.18,9) (6,46.34,48)";
        List<PackageItem> expectedRecordList = Arrays.asList( new PackageItem(4,72.30,76));

        PackageEntity packageEntity = Deencapsulation.invoke(PackageEntityTranslator.class, "translateToPackageEntities", line);

        List<PackageItem> actualList =   AlgorithmFactory.getPackingAlogrithm().execute(packageEntity);

        assertEquals(expectedRecordList .size(), actualList.size());
        assertEquals(expectedRecordList.get(0).getItemNumber(), 4);
    }

    private List<PackageItem> getData(){
        List<PackageItem> expectedRecordList = Arrays.asList(
                new PackageItem(1,53.38,45),
                new PackageItem(3,78.48,3),
                new PackageItem(4,72.30,76),
                new PackageItem(5,30.18,9),
                new PackageItem(6,46.34,48)
        );
        return expectedRecordList;
    }
}
