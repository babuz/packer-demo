import com.packer.Main;
import com.packer.exception.ApiException;
import mockit.Deencapsulation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestPackerUtil {

    @DisplayName("Throw Api exception for wrong file path")
    @Test
    void shouldThrowFileNotFoundException() {
        assertThrows(ApiException.class, () -> {
            Main.pack("c:/x/dd/test.txt");
        });
    }

    @DisplayName("parse with invalid argument throw exception")
    @Test
    void shouldThrowExceptionForInvalidData() {
        Throwable exception = assertThrows(RuntimeException.class, () -> {
            Deencapsulation.invoke(Main.class, "parse", "(1,53.38,45)");
        });
        assertThat(exception.getMessage(), containsString("Invalid data format "));
    }

    @DisplayName("parse invalid max weight data throw exception")
    @Test
    void shouldThrowExceptionForØInvalidMaxWeightData() {
        Throwable exception = assertThrows(NumberFormatException.class, () -> {
            Deencapsulation.invoke(Main.class, "parse", "test: (1,53.38,45)");
        });
        assertThat(exception.getMessage(), containsString("test"));
    }

    @DisplayName("parse  out of range maximum weight data throw exception")
    @ParameterizedTest
    @ValueSource(strings = {"200: (1,53.38,45)","0: (1,53.38,45)","-1: (1,53.38,45)"})
    void shouldThrowExceptionIfMaxWeightOutOfRange(String line) {
        Throwable exception = assertThrows(RuntimeException.class, () -> {
            Deencapsulation.invoke(Main.class, "parse", line);
        });
        assertThat(exception.getMessage(), containsString("max weight out of range"));
    }
}
