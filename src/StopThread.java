public class StopThread {
    public static void main(String[] args) {
        Runnable task = () -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    System.out.println("Working...");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("I was told to stop!");
                    // Important: Re-interrupt the thread locally if you need to propagate the state
                   // Thread.currentThread().interrupt();
                    break; // Exit the loop
                }
            }
        };
        Thread thread = new Thread(task);
        thread.start();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
// Later...
        thread.interrupt(); // Sends the signal

    }
}
