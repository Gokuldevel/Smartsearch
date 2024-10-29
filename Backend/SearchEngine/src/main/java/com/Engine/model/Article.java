package com.Engine.model;

public class Article {
	private String title;
	private String link;
	private String source;

	public Article(String title, String link, String source) {
		super();
		this.title = title;
		this.link = link;
		this.source = source;
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

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

}
