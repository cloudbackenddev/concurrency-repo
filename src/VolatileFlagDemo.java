public class VolatileFlagDemo {
    // volatile ensures that changes made by one thread are visible to others
    private static volatile  boolean running = true;

    public static void main(String[] args) throws InterruptedException {
        Thread worker = new Thread(() -> {
            System.out.println("Worker: Starting loop...");
            long count = 0;
            while (running) {
                // Do some work
                count++;
            }
            System.out.println("Worker: Stopped. Iterations: " + count);
        });

        worker.start();

        // Let the worker run for a bit
        Thread.sleep(1000);

        System.out.println("Main: Setting running to false...");
        running = false;

        worker.join();
        System.out.println("Main: Worker has finished.");
    }
}
