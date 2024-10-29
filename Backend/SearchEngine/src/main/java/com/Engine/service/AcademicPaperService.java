package com.Engine.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.Engine.model.AcademicPaper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

@Service
public class AcademicPaperService {
    private static final String API_URL = "https://www.googleapis.com/customsearch/v1";
    private static final String API_KEY = "********************";
    private static final String CX = "f49c7d8347d524c4e"; // Custom Search Engine ID configured for academic sources

    public List<AcademicPaper> searchPapers(String query) {
        List<AcademicPaper> papers = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();


        String url = UriComponentsBuilder.fromHttpUrl(API_URL)
                .queryParam("q", query)
                .queryParam("cx", CX)
                .queryParam("key", API_KEY)
                .queryParam("num", 10) // Fetch up to 5 results
                .toUriString();

        try { 
            // Fetch and parse response from Google Custom Search API
            String response = restTemplate.getForObject(url, String.class);
            JsonNode root = mapper.readTree(response);

            // Process each item in the response
            for (JsonNode item : root.path("items")) {
                String title = item.path("title").asText();
                String link = item.path("link").asText();
                String snippet = item.path("snippet").asText();
                String source = item.path("displayLink").asText(); // Source of the paper

                // Create an AcademicPaper object
                AcademicPaper paper = new AcademicPaper(title, link, source, snippet);
                papers.add(paper);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Log or handle error as needed
        }

        return papers;
    }
}
