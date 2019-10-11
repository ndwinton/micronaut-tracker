package test.tracker;

import micronaut.tracker.WelcomeController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WelcomeControllerTest {
    @Test
    public void itSaysHello() {
        WelcomeController controller = new WelcomeController("A welcome message");

        assertEquals("A welcome message", controller.sayHello());
    }
}
