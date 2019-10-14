package test.tracker;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import micronaut.tracker.TimeEntry;
import micronaut.tracker.TimeEntryController;
import micronaut.tracker.TimeEntryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class TimeEntryControllerTest {
    private TimeEntryRepository timeEntryRepository;
    private TimeEntryController controller;

    @BeforeEach
    public void setUp() {
        timeEntryRepository = mock(TimeEntryRepository.class);
        controller = new TimeEntryController(timeEntryRepository);
    }

    @Test
    public void testCreate() {
        long projectId = 123L;
        long userId = 456L;
        TimeEntry timeEntryToCreate = new TimeEntry(projectId, userId, LocalDate.parse("2017-01-08"), 8);

        long timeEntryId = 1L;
        TimeEntry expectedResult = new TimeEntry(timeEntryId, projectId, userId, LocalDate.parse("2017-01-08"), 8);
        doReturn(expectedResult)
                .when(timeEntryRepository)
                .create(any(TimeEntry.class));


        HttpResponse<TimeEntry> response = controller.create(timeEntryToCreate);


        verify(timeEntryRepository).create(timeEntryToCreate);
        assertEquals(HttpStatus.CREATED, response.status());
        assertEquals(expectedResult, response.body());
    }

    @Test
    public void testRead() {
        long timeEntryId = 1L;
        long projectId = 123L;
        long userId = 456L;
        TimeEntry expected = new TimeEntry(timeEntryId, projectId, userId, LocalDate.parse("2017-01-08"), 8);
        doReturn(expected)
                .when(timeEntryRepository)
                .find(timeEntryId);

        HttpResponse<TimeEntry> response = controller.read(timeEntryId);

        verify(timeEntryRepository).find(timeEntryId);
        assertEquals(HttpStatus.OK, response.status());
        assertEquals(expected, response.body());
    }

    @Test
    public void testRead_NotFound() {
        long nonExistentTimeEntryId = 1L;
        doReturn(null)
                .when(timeEntryRepository)
                .find(nonExistentTimeEntryId);

        HttpResponse<TimeEntry> response = controller.read(nonExistentTimeEntryId);
        assertEquals(HttpStatus.NOT_FOUND, response.status());
    }

    @Test
    public void testList() {
        List<TimeEntry> expected = asList(
                new TimeEntry(1L, 123L, 456L, LocalDate.parse("2017-01-08"), 8),
                new TimeEntry(2L, 789L, 321L, LocalDate.parse("2017-01-07"), 4)
        );
        doReturn(expected).when(timeEntryRepository).list();

        HttpResponse<List<TimeEntry>> response = controller.list();

        verify(timeEntryRepository).list();
        assertEquals(HttpStatus.OK, response.status());
        assertEquals(expected, response.body());
    }

    @Test
    public void testUpdate() {
        long timeEntryId = 1L;
        long projectId = 987L;
        long userId = 654L;
        TimeEntry expected = new TimeEntry(timeEntryId, projectId, userId, LocalDate.parse("2017-01-07"), 4);
        doReturn(expected)
                .when(timeEntryRepository)
                .update(eq(timeEntryId), any(TimeEntry.class));

        HttpResponse<TimeEntry> response = controller.update(timeEntryId, expected);

        verify(timeEntryRepository).update(timeEntryId, expected);
        assertEquals(HttpStatus.OK, response.status());
        assertEquals(expected, response.body());
    }

    @Test
    public void testUpdate_NotFound() {
        long nonExistentTimeEntryId = 1L;
        doReturn(null)
                .when(timeEntryRepository)
                .update(eq(nonExistentTimeEntryId), any(TimeEntry.class));

        HttpResponse<TimeEntry> response = controller.update(nonExistentTimeEntryId, new TimeEntry());
        assertEquals(HttpStatus.NOT_FOUND, response.status());
    }

    @Test
    public void testDelete() {
        long timeEntryId = 1L;
        HttpResponse response = controller.delete(timeEntryId);
        verify(timeEntryRepository).delete(timeEntryId);
        assertEquals(HttpStatus.NO_CONTENT, response.status());
    }
}
