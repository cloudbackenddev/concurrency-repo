import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BasicExecutorService {

    public static void main(String[] args){
        // 1. Create a pool with 2 reusable threads
        ExecutorService executor = Executors.newFixedThreadPool(2);

// 2. Submit tasks
        for (int i = 0; i < 5; i++) {
            int taskId = i;
            executor.submit(() -> {
                System.out.println("Task " + taskId + " running on " + Thread.currentThread().getName());
            });
        }

// 3. Shutdown the pool (Important!)
        executor.shutdown();
    }
}
