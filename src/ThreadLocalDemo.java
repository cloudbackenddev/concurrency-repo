/**
 * 2.10 ThreadLocal (Per-Thread Data) Demo
 *
 * This demo simulates a web server environment where multiple user requests are
 * processed.
 * We use ThreadLocal to store a "UserContext" unique to each thread.
 *
 * Use Case: User Sessions, Transaction IDs, or any data that should be private
 * to a thread
 * but accessible across different service layers without being passed as a
 * parameter.
 */

class UserContext {
    private final String userId;
    private final String transactionId;

    public UserContext(String userId, String transactionId) {
        this.userId = userId;
        this.transactionId = transactionId;
    }

    @Override
    public String toString() {
        return "UserContext{id='" + userId + "', txn='" + transactionId + "'}";
    }
}

class SecurityService {
    // This method can access the user context without it being passed as a
    // parameter!
    public void processSensitiveAction() {
        UserContext context = ThreadLocalDemo.userContextHolder.get();
        System.out.println("  [SecurityService] Processing action for: " + context);
    }
}

public class ThreadLocalDemo {
    // 1. Define the ThreadLocal variable
    // We use a static final holder to make it accessible across the application.
    public static final ThreadLocal<UserContext> userContextHolder = new ThreadLocal<>();

    public static void main(String[] args) throws InterruptedException {
        SecurityService securityService = new SecurityService();

        // Simulate two different "Requests" (Threads)
        Thread request1 = new Thread(() -> {
            try {
                // 2. Set the data for THIS thread
                String userId = "user-123";
                String txnId = "TXN-AAAA";
                userContextHolder.set(new UserContext(userId, txnId));

                System.out.println("Processing Request 1 for " + userId);

                // Deep in the call stack, another service needs the data
                securityService.processSensitiveAction();

            } finally {
                // 3. CRITICAL: Always remove to prevent memory leaks in thread pools!
                userContextHolder.remove();
                System.out.println("Request 1 cleaned up.");
            }
        });

        Thread request2 = new Thread(() -> {
            try {
                // Set DIFFERENT data for THIS thread
                String userId = "user-999";
                String txnId = "TXN-ZZZZ";
                userContextHolder.set(new UserContext(userId, txnId));

                System.out.println("Processing Request 2 for " + userId);

                securityService.processSensitiveAction();

            } finally {
                userContextHolder.remove();
                System.out.println("Request 2 cleaned up.");
            }
        });

        request1.start();
        request2.start();

        request1.join();
        request2.join();

        System.out.println("Main thread: " + userContextHolder.get()); // Should be null
    }
}
