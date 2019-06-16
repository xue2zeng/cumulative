package com.xspace.java11.feature;

import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * JEP321: 标准 HTTP客户端
 *
 * @author xue.zeng
 * @date 2019-06-16 16:15
 */
@Slf4j
public class HttpClientExample {
  /**
   * 同步 Get 方法
   *
   * @param uri
   * @throws Exception
   */
  private static void syncGet(String uri) throws Exception {
    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder().uri(URI.create(uri)).build();
    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    log.info("sync response status code: {}", response.statusCode());
    log.info("sync response body: {}", response.body());
  }

  /**
   * 异步 Get 方法
   *
   * @param uri
   * @throws Exception
   */
  private static void asyncGet(String uri) throws Exception {
    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder().uri(URI.create(uri)).build();
    CompletableFuture<HttpResponse<String>> future =
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
    future
        .whenComplete(
            (resp, e) -> {
              if (Objects.nonNull(e)) {
                log.error("call failure.", e);
              } else {
                log.info("async response status code: {}", resp.statusCode());
                log.info("async response body: {}", resp.body());
              }
            })
        .join();
  }

  public static void main(String[] args) throws Exception {
    String uri = "http://t.weather.sojson.com/api/weather/city/101030100";
    syncGet(uri);
    asyncGet(uri);
  }
}
