package micronaut.tracker;

import io.micronaut.context.annotation.Value;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

import java.util.Map;

@Controller
public class EnvController {
    private final String port;
    private final String memoryLimit;
    private final String cfInstanceIndex;
    private final String cfInstanceAddr;

    public EnvController(@Value("${port:NOT SET}") String port,
                         @Value("${memory.limit:NOT SET}") String memoryLimit,
                         @Value("${cf.instance.index:NOT SET}") String cfInstanceIndex,
                         @Value("${cf.instance.addr:NOT SET}") String cfInstanceAddr) {
        this.port = port;
        this.memoryLimit = memoryLimit;
        this.cfInstanceIndex = cfInstanceIndex;
        this.cfInstanceAddr = cfInstanceAddr;
    }

    @Get("/env")
    public Map<String, String> getEnv() {
        return Map.of(
                "PORT", port,
                "MEMORY_LIMIT", memoryLimit,
                "CF_INSTANCE_INDEX", cfInstanceIndex,
                "CF_INSTANCE_ADDR", cfInstanceAddr
        );
    }
}
