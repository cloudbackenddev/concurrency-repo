import java.util.concurrent.atomic.AtomicInteger;

public class AtomicCounterDemo {
    public static void main(String[] args) throws InterruptedException {
        AtomicInteger atomicCount = new AtomicInteger(0);
        int numThreads = 10;
        int incrementsPerThread = 1000;

        Thread[] threads = new Thread[numThreads];

        for (int i = 0; i < numThreads; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < incrementsPerThread; j++) {
                    // Atomic increment (CAS - Compare And Swap)
                    atomicCount.incrementAndGet();
                }
            });
            threads[i].start();
        }

        for (Thread t : threads) {
            t.join();
        }

        System.out.println("Expected count: " + (numThreads * incrementsPerThread));
        System.out.println("Actual atomic count: " + atomicCount.get());
    }
}
