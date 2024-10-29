package com.Engine.service;

import com.Engine.model.Blog;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

@Service
public class BlogService {
    private static final String SEARCH_URL = "https://www.googleapis.com/customsearch/v1"; // Your Custom Search API endpoint
    private static final String API_KEY = "AIzaSyDWSgjIM9uLLJtS058r0mHFxXf61ckIWFk"; // Your API key
    private static final String CX = "f49c7d8347d524c4e"; // Your Custom Search Engine ID

    public List<Blog> searchBlogs(String query) {
        List<Blog> blogs = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();

        // Build URL for Custom Search API
        String url = UriComponentsBuilder.fromHttpUrl(SEARCH_URL)
                .queryParam("key", API_KEY)
                .queryParam("cx", CX)
                .queryParam("q", query + " blog") // Optionally filter for blogs
                .toUriString();

        try {
            String response = restTemplate.getForObject(url, String.class);
            JsonNode root = mapper.readTree(response);
            JsonNode items = root.path("items");

            for (JsonNode item : items) {
                String title = item.path("title").asText();
                String link = item.path("link").asText();
                String author = ""; // Author information might not be available in the response
                String publishedDate = ""; // Published date might not be available in the response

                // Create a Blog object
                Blog blog = new Blog(title, link, author, publishedDate);
                blogs.add(blog);
            }

        } catch (Exception e) {
            e.printStackTrace();
            // Log or handle the error as needed
        }

        return blogs;
    }
}