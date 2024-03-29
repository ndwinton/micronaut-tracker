package micronaut.tracker;

import java.time.LocalDate;
import java.util.Objects;

public class TimeEntry {
    private final long id;
    private final long projectId;
    private final long userId;
    //@JsonDeserialize(using = MultiDateDeserialiser.class, as = LocalDate.class)
    private final LocalDate date;
    private final int hours;

    public TimeEntry(long id, long projectId, long userId, LocalDate date, int hours) {
        this.id = id;
        this.projectId = projectId;
        this.userId = userId;
        this.date = date;
        this.hours = hours;
    }

    public TimeEntry(long projectId, long userId, LocalDate date, int hours) {
        this(0, projectId, userId, date, hours);
    }

    public TimeEntry() {
        this(-1, -1, -1, null, -1);
    }

    public TimeEntry(long id, TimeEntry other) {
        this(id, other.getProjectId(), other.getUserId(), other.getDate(), other.getHours());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeEntry entry = (TimeEntry) o;
        return id == entry.id &&
                projectId == entry.projectId &&
                userId == entry.userId &&
                hours == entry.hours &&
                Objects.equals(date, entry.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, projectId, userId, date, hours);
    }

    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "TimeEntry{" +
                "id=" + id +
                ", projectId=" + projectId +
                ", userId=" + userId +
                ", date=" + date +
                ", hours=" + hours +
                '}';
    }

    public long getProjectId() {
        return projectId;
    }

    public long getUserId() {
        return userId;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getHours() {
        return hours;
    }
}

