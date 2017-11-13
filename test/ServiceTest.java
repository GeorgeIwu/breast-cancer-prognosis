import org.junit.Test;
import play.Logger;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertTrue;

/**
 * Created by chigozirim on 6/6/2016.
 */
public class ServiceTest {
    @Test
  public void test() {
        Map<String, Object> data = new HashMap<>();
        String dataString = MappingClient.getInstance().map("finacle-add-document", data);
        Logger.info(dataString);
    }
}
