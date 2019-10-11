package micronaut.tracker;

import io.micronaut.context.annotation.Value;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

@Controller("/")
public class WelcomeController {

    private String message;

    public WelcomeController(@Value("${welcome.message}") String message) {
        this.message = message;
    }

    @Get
    public String sayHello() {
        return message;
    }
}
