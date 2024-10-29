package com.Engine.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.Engine.model.YouTubeVideo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;

@Service
public class YouTubeService {
    private static final String API_KEY = "AIzaSyCrrSzhpDClGBo64IGHSnzzy0tpPVAPM78";
    private static final String API_URL = "https://www.googleapis.com/youtube/v3/search";
    private static final String VIDEO_DETAILS_URL = "https://www.googleapis.com/youtube/v3/videos";
    public double relevance=0;
    public List<YouTubeVideo> searchVideos(String query) {
        List<YouTubeVideo> videos = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();

        // Build the search URL
        String searchUrl = UriComponentsBuilder.fromHttpUrl(API_URL)
                .queryParam("part", "snippet")
                .queryParam("type", "video")
                .queryParam("q", query)
                .queryParam("key", API_KEY)
                .queryParam("maxResults", 10)
                .toUriString();

        try {
            // Fetch the response from the YouTube Data API search endpoint
            String response = restTemplate.getForObject(searchUrl, String.class);
            JsonNode root = mapper.readTree(response);
            List<String> videoIds = new ArrayList<>();

            // Extract video IDs from the search response
            for (JsonNode item : root.path("items")) {
                String videoId = item.path("id").path("videoId").asText();
                videoIds.add(videoId);
            }

            // Fetch additional video details using the videos API
            if (!videoIds.isEmpty()) {
                videos.addAll(fetchVideoDetails(videoIds,query));
            }

        } catch (Exception e) {
            e.printStackTrace();
            // Log error or handle exception
        }

        return videos;
    }

    private List<YouTubeVideo> fetchVideoDetails(List<String> videoIds,String query) {
        List<YouTubeVideo> videos = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();

        // Build the videos API URL with the list of video IDs
        String videoUrl = UriComponentsBuilder.fromHttpUrl(VIDEO_DETAILS_URL)
                .queryParam("part", "snippet,statistics")
                .queryParam("id", String.join(",", videoIds))
                .queryParam("key", API_KEY)
                .toUriString();

        try {
            String response = restTemplate.getForObject(videoUrl, String.class);
            JsonNode root = mapper.readTree(response);

            for (JsonNode item : root.path("items")) {
                String videoId = item.path("id").asText();
                String title = item.path("snippet").path("title").asText();
                String link = "https://www.youtube.com/watch?v=" + videoId;
                long views = item.path("statistics").path("viewCount").asLong();
                long likes = item.path("statistics").path("likeCount").asLong();
                double score = calculateScore(views, likes);
                relevance = calculateRelevance(item, query); 
                videos.add(new YouTubeVideo(title, link, views, likes,score,relevance));
            }

        } catch (Exception e) {
            e.printStackTrace();
            // Log error or handle exception
        }

        return videos;
    }
    
    private double calculateRelevance(JsonNode item, String query) {
        String title = item.path("snippet").path("title").asText();
        String description = item.path("snippet").path("description").asText();
        
        // Example: Simple keyword matching
        double relevanceScore = 0.0;
        if (title.toLowerCase().contains(query.toLowerCase())) {
            relevanceScore += 1.0; // Increase relevance score for title match
        }
        if (description.toLowerCase().contains(query.toLowerCase())) {
            relevanceScore += 0.5; // Increase relevance score for description match
        }

        return relevanceScore; // Return the calculated relevance score
    }
    private double calculateScore(long views, long likes) {
        if (views == 0) return 0; // Avoid division by zero
        return (likes / (double) views) * 100; // Example scoring logic
    }

    private YouTubeVideo fetchVideoDetails(String videoId, String title, String link) {
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();

        // URL to fetch video details (like views and likes)
        String url = UriComponentsBuilder.fromHttpUrl(VIDEO_DETAILS_URL)
                .queryParam("part", "statistics")
                .queryParam("id", videoId)
                .queryParam("key", API_KEY)
                .toUriString();

        try {
            String response = restTemplate.getForObject(url, String.class);
            JsonNode root = mapper.readTree(response);

            int views = root.path("items").get(0).path("statistics").path("viewCount").asInt();
            int likes = root.path("items").get(0).path("statistics").path("likeCount").asInt();
            double score = calculateScore(views, likes);
            return new YouTubeVideo(title, link, views, likes,score,relevance);

        } catch (Exception e) {
            e.printStackTrace();
            // Log error or handle exception
            return new YouTubeVideo(title, link, 0,0,0.0,0); // Return with default values on error
        }
    }
}

