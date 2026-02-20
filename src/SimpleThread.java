//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class SimpleThread {
    public static void main(String[] args) {
        // 1. Defining the task
        Runnable task = () -> {
            String threadName = Thread.currentThread().getName();
            System.out.println("Hello from " + threadName);
        };

        // 2. Creating the thread (The Worker)
        Thread thread = new Thread(task);

        // 3. Starting the thread
        thread.start();

        System.out.println("Hello from " + Thread.currentThread().getName());
    }
}