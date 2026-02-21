import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class CompletableFutureChaining {
    public static void main(String[] args) {

        HttpClient client = HttpClient.newHttpClient();

        CompletableFuture<String> tokenFuture = client.sendAsync(
                        HttpRequest.newBuilder().uri(URI.create("https://httpbin.org/uuid")).build(),
                        HttpResponse.BodyHandlers.ofString()
                )
                .thenApply(HttpResponse::body)
                // FIX: Extract the actual UUID value from the JSON string {"uuid": "..."}
                .thenApply(json -> json.replaceAll("(?s).*\"uuid\":\\s*\"([^\"]+)\".*", "$1").trim());

        // Chaining: Use the extracted UUID string
        CompletableFuture<String> result = tokenFuture.thenCompose(token -> {
            System.out.println("Using Token: " + token);
            return client.sendAsync(
                    HttpRequest.newBuilder().uri(URI.create("https://httpbin.org/headers"))
                            .header("X-Auth-Token", token).build(),
                    HttpResponse.BodyHandlers.ofString()
            ).thenApply(HttpResponse::body);
        });

        result.thenAccept(System.out::println).join();
    }
}