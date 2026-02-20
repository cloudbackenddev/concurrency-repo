public class WaitNotifyDemo {
    private static final Object lock = new Object();
    private static boolean pizzaReady = false;

    public static void main(String[] args) {
        // Thread 1: The Waiter
        Thread waiter = new Thread(() -> {
            synchronized (lock) {
                System.out.println("Waiter: Waiting for pizza...");
                while (!pizzaReady) {
                    try {
                        // wait() puts the thread in the WAITING state and RELEASES the lock
                        lock.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        System.err.println("Waiter interrupted");
                    }
                }
                // Wake up (transition back to Runnable -> Running) and eat!
                System.out.println("Waiter: Pizza is ready! Time to eat!");
            }
        }, "Waiter-Thread");

        // Thread 2: The Chef
        Thread chef = new Thread(() -> {
            try {
                // Simulate pizza preparation
                System.out.println("Chef: Preparing pizza...");
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            synchronized (lock) {
                pizzaReady = true;
                System.out.println("Chef: Pizza is ready! Notifying waiter...");
                lock.notifyAll(); // Wakes up the Waiter
            }
        }, "Chef-Thread");

        waiter.start();
        chef.start();
    }
}
