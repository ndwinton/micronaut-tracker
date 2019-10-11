package micronaut.tracker;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

@Controller("/")
public class WelcomeController {

    @Get
    public String sayHello() {
        return "hello";
    }
}
