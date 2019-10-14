package test.trackerapi;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
public class WelcomeApiTest {

    @Inject
    private EmbeddedServer server;
    @Inject
    @Client("/")
    private HttpClient client;

    @Test
    public void server_should_respond_with_message_from_environment() {
        var response = client.toBlocking()
                .retrieve(HttpRequest.GET("/"));
        assertEquals("Hello from test", response);
    }
}
