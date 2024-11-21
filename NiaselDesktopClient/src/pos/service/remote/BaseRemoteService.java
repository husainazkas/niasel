/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pos.service.remote;

import io.vavr.control.Option;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import pos.entity.AuthToken;
import pos.interfaces.RemoteService;
import pos.utils.JsonParser;

/**
 *
 * @author husainazkas
 */
public abstract class BaseRemoteService implements RemoteService {

    private static final String BASE_URL = System.getProperty("BASE_URL_API");
    private static HttpClient client;

    private final Supplier<Option<AuthToken>> getAuthToken;

    public BaseRemoteService(Supplier<Option<AuthToken>> authTokenGetter) {
        getAuthToken = authTokenGetter;
    }

    @Override
    public HttpClient getClient() {
        if (client == null) {
            client = HttpClient.newBuilder()
                    .connectTimeout(Duration.ofSeconds(30))
                    .build();
        }
        return client;
    }

    @Override
    public HttpResponse<String> get(String path) throws IOException, InterruptedException {
        return post(path, null);
    }

    @Override
    public HttpResponse<String> get(String path, HashMap<String, String> query) throws IOException, InterruptedException {
        HttpRequest.Builder request = HttpRequest.newBuilder(parseURL(path, query))
                .setHeader("Content-Type", "application/json")
                .GET();

        getAuthToken.get().peek((t) -> request.setHeader("Authorization", "Bearer " + t.getAccessToken()));

        return getClient().send(request.build(), BodyHandlers.ofString());
    }

    @Override
    public HttpResponse<String> post(String path, Object data) throws IOException, InterruptedException {
        return post(path, null, data);
    }

    @Override
    public HttpResponse<String> post(String path, HashMap<String, String> query, Object data) throws IOException, InterruptedException {
        HttpRequest.Builder request = HttpRequest.newBuilder(parseURL(path, query))
                .setHeader("Content-Type", "application/json")
                .POST(data == null
                        ? BodyPublishers.noBody()
                        : BodyPublishers.ofString(JsonParser.toJson(data))
                );

        getAuthToken.get().peek((t) -> request.setHeader("Authorization", "Bearer " + t.getAccessToken()));

        return getClient().send(request.build(), BodyHandlers.ofString());
    }

    private URI parseURL(String path, HashMap<String, String> query) {
        if (query != null && !query.isEmpty()) {
            path += "?";
            for (Map.Entry<String, String> entry : query.entrySet()) {
                String key = entry.getKey();
                String val = entry.getValue();

                if (!path.endsWith("?")) {
                    path += "&";
                }
                path += key + "=" + val;
            }
        }
        return URI.create(path.startsWith("http") ? path : BASE_URL + path).normalize();
    }
}
