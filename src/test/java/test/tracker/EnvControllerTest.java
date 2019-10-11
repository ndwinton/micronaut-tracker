package test.tracker;

import micronaut.tracker.EnvController;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EnvControllerTest {

    @Test
    public void getEnv() throws Exception {
        EnvController controller = new EnvController(
                "8675",
                "12G",
                "34",
                "123.sesame.street"
        );

        Map<String, String> env = controller.getEnv();

        assertEquals("8675", env.get("PORT"));
        assertEquals("12G", env.get("MEMORY_LIMIT"));
        assertEquals("34", env.get("CF_INSTANCE_INDEX"));
        assertEquals("123.sesame.street", env.get("CF_INSTANCE_ADDR"));
    }
}
