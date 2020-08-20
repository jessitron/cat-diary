package com.jessitron.catdiary.pictures;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Service
@Slf4j
public class CatPictureService {

  final static String CAT_PICTURE_API_URL = "https://api.thecatapi.com/v1/images/search";
  private final HttpClient httpClient = HttpClient.newBuilder()
      .version(HttpClient.Version.HTTP_1_1)
      .connectTimeout(Duration.ofSeconds(10))
      .build();

  public URL randomCatPicture() {

    HttpRequest request = HttpRequest.newBuilder()
        .GET()
        .uri(URI.create(CAT_PICTURE_API_URL))
        .build();

    HttpResponse<String> response;
    try {
      response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    } catch (IOException e) {
      log.error("No cat picture for you");
      e.printStackTrace();
      return null;
    } catch (InterruptedException e) {
      log.error("Your cat picture was interrupted");
      e.printStackTrace();
      return null;
    }

    // print status code
    log.info("Cat picture response code: " + response.statusCode());

    // print response body
    var body = response.body();
    System.out.println(response.body());
    // god. parsing JSON is SO HARD in Java
    log.info("Response from cat API: " + body);

    String urlString;
    try {
      urlString = extractURLString(body);
    } catch (JsonProcessingException e) {
      log.error("Invalid JSON from the cat API: " + body);
      return null;
    }

    URL url;
    try {
      url = new URL(urlString);
    } catch (MalformedURLException e) {
      log.error("Malformed URL returned from cat API: " + urlString);
      return null;
    }

    return url;
  }


  /*
  [
{
breeds: [ ],
id: "5n9",
url: "https://cdn2.thecatapi.com/images/5n9.jpg",
width: 680,
height: 452
}
]
   */

  String extractURLString(String json) throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    JsonNode rootNode = mapper.readTree(json);
    return rootNode.get(0).get("url").asText();
  }
}
