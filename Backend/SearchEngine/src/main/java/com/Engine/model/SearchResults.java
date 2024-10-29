package com.Engine.model;

import java.util.List;

public class SearchResults {
	 private List<YouTubeVideo> videos;
	    private List<Article> articles;
	    private List<AcademicPaper> papers;
	    private List<Blog> Blogs;

	    public SearchResults(List<YouTubeVideo> videos, List<Article> articles, List<AcademicPaper> papers,List<Blog> Blogs) {
	        this.videos = videos;
	        this.articles = articles;
	        this.papers = papers;
	        this.Blogs=Blogs;
	    }

		public List<YouTubeVideo> getVideos() {
			return videos;
		}

		public void setVideos(List<YouTubeVideo> videos) {
			this.videos = videos;
		}

		public List<Article> getArticles() {
			return articles;
		}

		public void setArticles(List<Article> articles) {
			this.articles = articles;
		}

		public List<AcademicPaper> getPapers() {
			return papers;
		}

		public void setPapers(List<AcademicPaper> papers) {
			this.papers = papers;
		}

		public List<Blog> getBlogs() {
			return Blogs;
		}

		public void setBlogs(List<Blog> blogs) {
			Blogs = blogs;
		}
	    
	    
	    
}
