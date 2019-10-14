package micronaut.tracker;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;

import java.util.List;

@Controller("/time-entries")
public class TimeEntryController {
    private TimeEntryRepository repository;

    public TimeEntryController(TimeEntryRepository repository) {
        this.repository = repository;
    }

    @Post
    public HttpResponse<TimeEntry> create(@Body TimeEntry timeEntryToCreate) {
        var timeEntry = repository.create(timeEntryToCreate);
        return HttpResponse.created(timeEntry);
    }

    @Get("{id}")
    public HttpResponse<TimeEntry> read(long id) {
        var timeEntry = repository.find(id);
        if (timeEntry == null) {
            return HttpResponse.notFound();
        }
        return HttpResponse.ok(timeEntry);
    }

    @Get
    public HttpResponse<List<TimeEntry>> list() {
        return HttpResponse.ok(repository.list());
    }

    @Put("{id}")
    public HttpResponse<TimeEntry> update(long id, @Body TimeEntry timeEntryToUpdate) {
        var updated = repository.update(id, timeEntryToUpdate);
        if (updated == null) {
            return HttpResponse.notFound();
        }
        return HttpResponse.ok(updated);
    }

    @Delete("{id}")
    public HttpResponse delete(long id) {
        repository.delete(id);
        return HttpResponse.noContent();
    }
}
