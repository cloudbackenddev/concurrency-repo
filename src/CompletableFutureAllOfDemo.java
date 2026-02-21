import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class CompletableFutureAllOfDemo {
    public static void main(String[] args) {
        HttpClient client = HttpClient.newHttpClient();

        CompletableFuture<String> call1 = client.sendAsync(
                HttpRequest.newBuilder().uri(URI.create("https://httpbin.org/ip")).build(),
                HttpResponse.BodyHandlers.ofString()
        ).thenApply(HttpResponse::body);

        CompletableFuture<String> call2 = client.sendAsync(
                HttpRequest.newBuilder().uri(URI.create("https://httpbin.org/user-agent")).build(),
                HttpResponse.BodyHandlers.ofString()
        ).thenApply(HttpResponse::body);

// Combine: wait for both
        CompletableFuture.allOf(call1, call2)
                .thenRun(() -> {
                    try {
                        System.out.println("Combined Results:");
                        System.out.println("IP: " + call1.get());
                        System.out.println("User-Agent: " + call2.get());
                    } catch (Exception e) { e.printStackTrace(); }
                }).join();


    }
}
