import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class HashMapIssueDemo {
    public static void main(String[] args) throws InterruptedException {
        Map<String, Integer> map = new HashMap<>(); // Standard HashMap
        int numberOfThreads = 2;
        int incrementsPerThread = 5000;

        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);

        for (int i = 0; i < numberOfThreads; i++) {
            executor.submit(() -> {
                for (int j = 0; j < incrementsPerThread; j++) {
                    // Two threads trying to put 10,000 unique keys
                    map.put(Thread.currentThread().getName() + "-" + j, j);
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);

        System.out.println("Expected size: " + (numberOfThreads * incrementsPerThread));
        System.out.println("Actual size:   " + map.size());
    }
}
