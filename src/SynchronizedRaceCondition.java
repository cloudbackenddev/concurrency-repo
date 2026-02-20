public class SynchronizedRaceCondition {
    private int count = 0;

    public synchronized void increment() {
        count++;
    }

    public int getCount() { return count; }

    public static void main(String[] args) throws InterruptedException {
        SynchronizedRaceCondition counter = new SynchronizedRaceCondition();

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1_000; i++) counter.increment();
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1_000; i++) counter.increment();
        });

        t1.start();
        t2.start();
        t1.join(); // Wait for t1 to finish
        t2.join(); // Wait for t2 to finish

        System.out.println("Expected: 2000");
        System.out.println("Actual:   " + counter.getCount());
    }
}