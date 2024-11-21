/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package pos.interfaces;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.HashMap;

/**
 *
 * @author husainazkas
 */
public interface RemoteService {

    public HttpClient getClient();

    public HttpResponse<String> get(String path) throws IOException, InterruptedException;

    public HttpResponse<String> get(String path, HashMap<String, String> query) throws IOException, InterruptedException;

    public HttpResponse<String> post(String path, Object data) throws IOException, InterruptedException;

    public HttpResponse<String> post(String path, HashMap<String, String> query, Object data) throws IOException, InterruptedException;

}
