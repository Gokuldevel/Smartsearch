package com.Engine.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Engine.model.AcademicPaper;
import com.Engine.model.Article;
import com.Engine.model.Blog;
import com.Engine.model.SearchResults;
import com.Engine.model.YouTubeVideo;
import com.Engine.service.AcademicPaperService;
import com.Engine.service.ArticleService;
import com.Engine.service.BlogService;
import com.Engine.service.YouTubeService;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api/search")
@CrossOrigin(origins = "http://127.0.0.1:5500/")
public class SearchController {

    private final YouTubeService youtubeService;
    private final ArticleService articleService;
    private final AcademicPaperService academicService;
    private final BlogService blogService;

    public SearchController(YouTubeService youtubeService, ArticleService articleService, AcademicPaperService academicService, BlogService blogService) {
        this.youtubeService = youtubeService;
        this.articleService = articleService;
        this.academicService = academicService;
        this.blogService = blogService;
    }

    @GetMapping
    public SearchResults search(@RequestParam String query, @RequestParam(required = false) String type) {
        List<YouTubeVideo> videos = new ArrayList<>();
        List<Article> articles = new ArrayList<>();
        List<AcademicPaper> papers = new ArrayList<>();
        List<Blog> blogs = new ArrayList<>();

        // Fetch results based on the specified type
        if (type == null || type.equalsIgnoreCase("youtube")) {
            videos = youtubeService.searchVideos(query);
        }
        if (type == null || type.equalsIgnoreCase("article")) {
            articles = articleService.searchArticles(query);
        }
        if (type == null || type.equalsIgnoreCase("academic")) {
            papers = academicService.searchPapers(query);
        }
        if (type == null || type.equalsIgnoreCase("blog")) {
            blogs = blogService.searchBlogs(query);
        }

        rankResults(videos, articles, papers, blogs);

        return new SearchResults(videos, articles, papers, blogs);
    }

    private void rankResults(List<YouTubeVideo> videos, List<Article> articles, List<AcademicPaper> papers, List<Blog> blogs) {
        double viewsWeight = 0.5;
        double likesWeight = 0.3;
        double relevanceWeight = 0.2;

        for (YouTubeVideo video : videos) {
            double score = (viewsWeight * video.getViews()) +
                           (likesWeight * video.getLikes());
            video.setScore(score);
        }
        videos.sort(Comparator.comparingDouble(YouTubeVideo::getScore).reversed());
    }
}
