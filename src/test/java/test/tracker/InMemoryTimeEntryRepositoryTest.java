package test.tracker;

import micronaut.tracker.InMemoryTimeEntryRepository;
import micronaut.tracker.TimeEntry;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;

public class InMemoryTimeEntryRepositoryTest {
    @Test
    public void create() throws Exception {
        InMemoryTimeEntryRepository repo = new InMemoryTimeEntryRepository();

        long projectId = 123L;
        long userId = 456L;
        TimeEntry createdTimeEntry = repo.create(new TimeEntry(projectId, userId, LocalDate.parse("2017-01-08"), 8));

        long timeEntryId = 1L;
        TimeEntry expected = new TimeEntry(timeEntryId, projectId, userId, LocalDate.parse("2017-01-08"), 8);
        assertEquals(expected, createdTimeEntry);

        TimeEntry readEntry = repo.find(createdTimeEntry.getId());
        assertEquals(expected, readEntry);
    }

    @Test
    public void find() throws Exception {
        InMemoryTimeEntryRepository repo = new InMemoryTimeEntryRepository();

        long projectId = 123L;
        long userId = 456L;
        repo.create(new TimeEntry(projectId, userId, LocalDate.parse("2017-01-08"), 8));

        long timeEntryId = 1L;
        TimeEntry expected = new TimeEntry(timeEntryId, projectId, userId, LocalDate.parse("2017-01-08"), 8);
        TimeEntry readEntry = repo.find(timeEntryId);
        assertEquals(expected, readEntry);
    }

    @Test
    public void find_MissingEntry() {
        InMemoryTimeEntryRepository repo = new InMemoryTimeEntryRepository();

        long timeEntryId = 1L;

        TimeEntry readEntry = repo.find(timeEntryId);
        assertNull(readEntry);
    }

    @Test
    public void list() throws Exception {
        InMemoryTimeEntryRepository repo = new InMemoryTimeEntryRepository();
        repo.create(new TimeEntry(123L, 456L, LocalDate.parse("2017-01-08"), 8));
        repo.create(new TimeEntry(789L, 654L, LocalDate.parse("2017-01-07"), 4));

        List<TimeEntry> expected = asList(
                new TimeEntry(1L, 123L, 456L, LocalDate.parse("2017-01-08"), 8),
                new TimeEntry(2L, 789L, 654L, LocalDate.parse("2017-01-07"), 4)
        );
        var sortedResult = repo.list()
                .stream()
                .sorted(Comparator.comparing(TimeEntry::getId))
                .collect(Collectors.toList());
        assertIterableEquals(expected, sortedResult);
    }

    @Test
    public void update() throws Exception {
        InMemoryTimeEntryRepository repo = new InMemoryTimeEntryRepository();
        TimeEntry created = repo.create(new TimeEntry(123L, 456L, LocalDate.parse("2017-01-08"), 8));

        TimeEntry updatedEntry = repo.update(
                created.getId(),
                new TimeEntry(321L, 654L, LocalDate.parse("2017-01-09"), 5));

        TimeEntry expected = new TimeEntry(created.getId(), 321L, 654L, LocalDate.parse("2017-01-09"), 5);
        assertEquals(expected, updatedEntry);
        assertEquals(expected, repo.find(created.getId()));
    }

    @Test
    public void update_MissingEntry() {
        InMemoryTimeEntryRepository repo = new InMemoryTimeEntryRepository();

        TimeEntry updatedEntry = repo.update(
                1L,
                new TimeEntry(321L, 654L, LocalDate.parse("2017-01-09"), 5));

        assertNull(updatedEntry);
    }

    @Test
    public void delete() throws Exception {
        InMemoryTimeEntryRepository repo = new InMemoryTimeEntryRepository();

        long projectId = 123L;
        long userId = 456L;
        TimeEntry created = repo.create(new TimeEntry(projectId, userId, LocalDate.parse("2017-01-08"), 8));

        repo.delete(created.getId());
        assertEquals(List.of(), repo.list());
    }

    @Test
    public void deleteKeepsTrackOfLatestIdProperly() {
        InMemoryTimeEntryRepository repo = new InMemoryTimeEntryRepository();

        long projectId = 123L;
        long userId = 456L;
        TimeEntry created = repo.create(new TimeEntry(projectId, userId, LocalDate.parse("2017-01-08"), 8));

        assertEquals(1, created.getId());

        repo.delete(created.getId());

        TimeEntry createdSecond = repo.create(new TimeEntry(projectId, userId, LocalDate.parse("2017-01-08"), 8));

        assertEquals(2, createdSecond.getId());
    }
}
