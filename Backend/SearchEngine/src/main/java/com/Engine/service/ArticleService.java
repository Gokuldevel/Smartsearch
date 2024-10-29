package com.Engine.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import com.Engine.model.Article;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;



@Service
public class ArticleService {
    private static final String API_KEY = "AIzaSyDWSgjIM9uLLJtS058r0mHFxXf61ckIWFk";
    private static final String CX = "f49c7d8347d524c4e";
    private static final String API_URL = "https://www.googleapis.com/customsearch/v1";

    public List<Article> searchArticles(String query) {
        List<Article> articles = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();

        String url = UriComponentsBuilder.fromHttpUrl(API_URL)
                .queryParam("q", query)
                .queryParam("cx", CX)
                .queryParam("key", API_KEY)
                .queryParam("num", 10) // Fetch up to 5 results
                .toUriString();

        try {
            // Fetch the response from Google Custom Search API
            String response = restTemplate.getForObject(url, String.class);
            JsonNode root = mapper.readTree(response);

            for (JsonNode item : root.path("items")) {
                String title = item.path("title").asText();
                String link = item.path("link").asText();
                String snippet = item.path("snippet").asText();

                Article article = new Article(title, link, snippet);
                articles.add(article);
            }

        } catch (Exception e) {
            e.printStackTrace();
            // Log error or handle exception as needed
        }

        return articles;
    }
}

