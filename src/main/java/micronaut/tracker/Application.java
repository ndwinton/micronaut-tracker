package micronaut.tracker;

import io.micronaut.context.annotation.Factory;
import io.micronaut.runtime.Micronaut;

import javax.inject.Singleton;

@Factory
public class Application {

    public static void main(String[] args) {
        Micronaut.run(Application.class);
    }

    @Singleton
    public TimeEntryRepository timeEntryRepository() {
        return new InMemoryTimeEntryRepository();
    }
}