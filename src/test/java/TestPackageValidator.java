import com.packer.Domain.PackageItem;
import com.packer.Util.PackageEntityTranslator;
import com.packer.Util.PackageValidator;
import com.packer.exception.ApiException;
import mockit.Deencapsulation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestPackageValidator {

    @DisplayName("Throw Api exception  for invalid line  ")
    @Test
    void shouldThrowExceptionForInvalidData() {
        Throwable exception = assertThrows(RuntimeException.class, () -> {
            Deencapsulation.invoke(PackageValidator.class, "isValidRecord", "test(1,53.38,45)");
        });
        assertThat(exception.getMessage(), containsString("Invalid data format "));
    }

    @DisplayName("Throw Api exception  for invalid Max Weight  ")
    @Test
    void shouldThrowExceptionForInvalidMaxWeight() {
        Throwable exception = assertThrows(ApiException.class, () -> {
            Deencapsulation.invoke(PackageValidator.class, "validate", new Object[]{200, TestDataUtil.getData()});
        });
        assertThat(exception.getMessage(), containsString("Max Weight exceeds the limitation"));
    }

    @DisplayName("Throw Api exception  for exceed  more package Items  ")
    @Test
    void shouldThrowExceptionForMorePackageItems() {
        Throwable exception = assertThrows(ApiException.class, () -> {
            Deencapsulation.invoke(PackageValidator.class, "validate", new Object[]{100, TestDataUtil.getDataMorePackageItems()});
        });
        assertThat(exception.getMessage(), containsString("Exceeds maximum number of item"));
    }

    @DisplayName("Throw Api exception  for exceed  Cost")
    @Test
    void shouldThrowExceptionForExceedCost() {
        Throwable exception = assertThrows(ApiException.class, () -> {
            Deencapsulation.invoke(PackageValidator.class, "validate", new Object[]{100, TestDataUtil.getCostExceedData()});
        });
        assertThat(exception.getMessage(), containsString("Cost exceeds the limitation"));
    }

    @DisplayName("Throw Api exception  for weight Cost")
    @Test
    void shouldThrowExceptionForExceedWeight() {
        Throwable exception = assertThrows(ApiException.class, () -> {
            Deencapsulation.invoke(PackageValidator.class, "validate", new Object[]{100, TestDataUtil.getWeightExceedData()});
        });
        assertThat(exception.getMessage(), containsString("Weight exceeds limitation"));
    }
}
