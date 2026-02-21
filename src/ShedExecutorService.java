import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ShedExecutorService {

    public static void main(String[] args) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

// Run task every 5 seconds, with an initial delay of 1 second
        scheduler.scheduleAtFixedRate(
                () -> System.out.println("Checking email..."),
                10, 5, TimeUnit.SECONDS
        );
    }
}
