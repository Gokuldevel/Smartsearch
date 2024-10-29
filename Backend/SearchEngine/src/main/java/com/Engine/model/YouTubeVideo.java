package com.Engine.model;

public class YouTubeVideo {
    private String title;
    private String link;
    private long views; 
    private long  likes;
    private double revelance;
    private double score;
    
    
    
	public YouTubeVideo(String title, String link, long views, long likes, double revelance, double score) {
		super();
		this.title = title;
		this.link = link;
		this.views = views;
		this.likes = likes;
		this.revelance = revelance;
		this.score = score;
	}
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public long getViews() {
		return views;
	}
	public void setViews(long views) {
		this.views = views;
	}
	public long getLikes() {
		return likes;
	}
	public void setLikes(long likes) {
		this.likes = likes;
	}
	public double getRevelance() {
		return revelance;
	}
	public void setRevelance(double revelance) {
		this.revelance = revelance;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
    

}