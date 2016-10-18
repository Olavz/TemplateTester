import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UtilTest {

    @Test
    public void testRegEx() {
        String data = "30 EURO";
        Pattern pattern = Pattern.compile("[0-9] [A-Z]", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(data);
        boolean found = false;
        if(matcher.find()) {
            found = true;
        } else {
            found = false;
        }
        Assert.assertEquals(found, true);
    }

}
