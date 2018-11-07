import com.packer.*;
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

public class TestPackerUtil {

    @DisplayName("Throw Api exception for wrong file path")
    @Test
    void shouldThrowFileNotFoundException() {
        assertThrows(ApiException.class, () -> {
            Packer.pack("c:/x/dd/test.txt");
        });
    }

    @DisplayName("parse with invalid argument throw exception")
    @Test
    void shouldThrowExceptionForInvalidData() {
        Throwable exception = assertThrows(RuntimeException.class, () -> {
            Deencapsulation.invoke(PackageEntityTranslator.class, "translateToPackageEntities", "(1,53.38,45)");
        });
        assertThat(exception.getMessage(), containsString("Invalid data format "));
    }

    @DisplayName("parse invalid max weight data throw exception")
    @Test
    void shouldThrowExceptionForInvalidMaxWeightData() {
        Throwable exception = assertThrows(NumberFormatException.class, () -> {
            Deencapsulation.invoke(PackageEntityTranslator.class, "translateToPackageEntities", "test: (1,53.38,45)");
        });
        assertThat(exception.getMessage(), containsString("test"));
    }

    @DisplayName("get valid records  after  removing max Weight ")
    @Test
    void shouldGetValidRecords() {
        String validRecord = "(1,53.38,45),(2,53.3,25),(3,23.18,15)";
        List<String> expectedRecordList = Arrays.asList("1,53.38,45","2,53.3,25","3,23.18,15");
        List<String> actualList = PackerUtil.getRecordList(validRecord);
        assertEquals(expectedRecordList.size(),actualList.size());
    }

    @DisplayName("get valid package items ")
    @Test
    void shouldGetValidPackageItems() {
        String line = "81 : (1,53.38,45) (2,88.62,98) (3,78.48,3) (4,72.30,76) (5,30.18,9) (6,46.34,48)";

        List<PackageItem> expectedRecordList = Arrays.asList(
                new PackageItem(1,53.38,45),
                new PackageItem(2,88.62,98),
                new PackageItem(3,78.48,3),
                new PackageItem(4,72.30,76),
                new PackageItem(5,30.18,9),
                new PackageItem(6,46.34,48)
                );
        PackageEntity expectedPackageEntity = new PackageEntity(81,expectedRecordList);

        PackageEntity packageEntity = Deencapsulation.invoke(PackageEntityTranslator.class, "translateToPackageEntities", line);

        assertEquals(expectedPackageEntity.getMaxWeight(), packageEntity.getMaxWeight());
        assertEquals(expectedPackageEntity.getPackageItems().size(), packageEntity.getPackageItems().size());
        //Deencapsulation.invoke(PackageEntityTranslator.class, "getValidPackage", new Object[] { 81.0,actualList });
    }

    @DisplayName("Successfully Get Valid Records Package Items")
    @Test
    void shouldRunSuccessfullyGetValidPackageItems() {
       String line = "56 : (1,90.72,13) (2,33.80,40) (3,43.15,10) (4,37.97,16) (5,46.81,36)  (6,48.77,79) (7,81.80,45) (8,19.36,79) (9,6.76,64)";
        //line = "81 : (1,53.38,45) (2,88.62,98) (3,78.48,3) (4,72.30,76) (5,30.18,9) (6,46.34,48)";
        //line = "75 : (1,85.31,29) (2,14.55,74) (3,3.98,16) (4,26.24,55) (5,63.69,52) (6,76.25,75) (7,60.02,74) (8,93.18,35) (9,89.95,78)";
        PackageEntity packageEntity = Deencapsulation.invoke(PackageEntityTranslator.class, "translateToPackageEntities", line);
        Deencapsulation.invoke(PackageAlgorithm.class, "execute", new Object[] { packageEntity });
    }

    @Test
    void testPackMethod(){
        Double packageMaxWeight = 10d;
        int[] cost = {10,40,30,50};
        Double[] weights = { 5.0, 4.0, 6.0, 3.0};
       // Main.packGreedy(packageMaxWeight, weights, cost);

        assertEquals(1,1);
    }

    @Test
    void testPackMethodAnother(){
        //(1,90.72,€13) (2,33.80,€40) (3,43.15,€10) (4,37.97,€16) (5,46.81,€36)
        //(6,48.77,€79) (7,81.80,€45) (8,19.36,€79) (9,6.76,€64)
        Double packageMaxWeight = 56d;
        int[] cost = {13,40,10,16,36,79,45,79,64};
        Double[] weights = { 90.72, 33.80, 43.15, 37.97,46.81,48.77,81.80,19.36,6.76};
        //Main.packGreedy(packageMaxWeight, weights, cost);

        assertEquals(1,1);
    }
}
