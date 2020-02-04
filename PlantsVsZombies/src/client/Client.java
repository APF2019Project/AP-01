package client;

import util.Effect;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;

import javafx.application.Platform;

public class Client {
  public static String base = "http://127.0.0.1:8000";

  public static Effect<String> get(String path, String body) {
    String url = base + "/" + path;
    System.out.println(url+" {\n"+body+"\n}");
    return new Effect<>(h -> {
      HttpClient client = HttpClient.newBuilder()
        .version(Version.HTTP_1_1)
        .followRedirects(Redirect.NORMAL)
        .connectTimeout(Duration.ofSeconds(20))
        .build();
      HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(url))
        .timeout(Duration.ofSeconds(2))
        .header("Content-Type", "application/json")
        .POST(BodyPublishers.ofString(body))
        .build();
      client.sendAsync(request, BodyHandlers.ofString())
        .thenApply(HttpResponse::body)
        .thenAccept((d) -> {
          Platform.runLater(()->{
            h.success(d);
          });
        })
        .exceptionally(e -> {
          Platform.runLater(()->{
            h.failure(e);
          });
          return null;
        }); 
    });
  }

  public static Effect<String> get(String path, String[] data) {
    return get(path, String.join("\n", data)+"\n");
  }
}