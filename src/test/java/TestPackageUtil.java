import com.packer.Util.PackerUtil;
import mockit.Deencapsulation;
import org.junit.jupiter.api.Test;

public class TestPackageUtil {

    @Test
    void shouldReturnValidStrings(){
        Deencapsulation.invoke(PackerUtil.class, "getPackageItemList", "(1,53.38,45),(1,53.38,45),(1,53.38,45)");
    }
}
