package test.trackerapi;

import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.annotation.MicronautTest;
import micronaut.tracker.TimeEntry;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.List;

import static io.micronaut.http.HttpRequest.*;
import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
public class TimeEntryApiTest {
    @Inject
    EmbeddedServer server;

    @Inject
    @Client("/time-entries")
    private HttpClient client;

    private final long PROJECT_ID = 123L;
    private final long USER_ID = 456L;
    private final LocalDate ENTRY_DATE = LocalDate.parse("2017-01-08");
    private final int ENTRY_HOURS = 8;
    private TimeEntry timeEntry = new TimeEntry(PROJECT_ID, USER_ID, ENTRY_DATE, ENTRY_HOURS);

    @Test
    void testCreate() {
        var createResponse = client.toBlocking().exchange(POST("/", timeEntry), TimeEntry.class);

        assertEquals(HttpStatus.CREATED, createResponse.status());

        var createdEntry = createResponse.body();
        assertNotNull(createdEntry);
        assertTrue(createdEntry.getId() > 0);
        assertEquals(PROJECT_ID, createdEntry.getProjectId());
        assertEquals(USER_ID, createdEntry.getUserId());
        assertEquals(ENTRY_DATE, createdEntry.getDate());
        assertEquals(ENTRY_HOURS, createdEntry.getHours());
    }

    @Test
    void testList() {
        var id = createTimeEntry();

        var response = client.toBlocking().exchange(GET("/"), Argument.of(List.class, TimeEntry.class));

        assertEquals(HttpStatus.OK, response.status());
        List<TimeEntry> entries = response.body();
        assertEquals(1, response.body().size());
        assertEquals(id, entries.get(0).getId());
    }

    @Test
    void testRead() {
        var id = createTimeEntry();

        var response = client.toBlocking().exchange(GET("/" + id), TimeEntry.class);

        assertEquals(HttpStatus.OK, response.status());
        var entry = response.body();
        assertNotNull(entry);
        assertEquals(id, entry.getId());
        assertEquals(PROJECT_ID, entry.getProjectId());
        assertEquals(USER_ID, entry.getUserId());
        assertEquals(ENTRY_DATE, entry.getDate());
        assertEquals(ENTRY_HOURS, entry.getHours());

    }

    @Test
    void testUpdate() {
        var id = createTimeEntry();
        var updatedTimeEntry = new TimeEntry(2L, 3L, LocalDate.parse("2017-01-09"), 9);

        var response = client.toBlocking().exchange(PUT("/" + id, updatedTimeEntry), TimeEntry.class);

        assertEquals(HttpStatus.OK, response.status());
        var entry = response.body();
        assertNotNull(entry);
        assertEquals(id, entry.getId());
        assertEquals(2L, entry.getProjectId());
        assertEquals(3L, entry.getUserId());
        assertEquals(LocalDate.parse("2017-01-09"), entry.getDate());
        assertEquals(9, entry.getHours());

    }

    @Test
    void testDelete() {

    }
    private long createTimeEntry() {
        var response = client.toBlocking().exchange(POST("/", timeEntry), TimeEntry.class);
        assertEquals(HttpStatus.CREATED, response.status());
        return response.body().getId();
    }
}
