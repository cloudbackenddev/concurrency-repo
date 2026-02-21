public class MyTask implements Runnable {

    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();
        System.out.println("Hello from " + threadName);
    }
}
