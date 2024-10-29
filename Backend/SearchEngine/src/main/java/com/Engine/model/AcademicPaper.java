package com.Engine.model;

public class AcademicPaper {
	    private String title;
	    private String link;
	    private String authors;
	    private String publishedDate;
	    
	    
		public AcademicPaper(String title, String link, String authors, String publishedDate) {
			super();
			this.title = title;
			this.link = link;
			this.authors = authors;
			this.publishedDate = publishedDate;
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
		public String getAuthors() {
			return authors;
		}
		public void setAuthors(String authors) {
			this.authors = authors;
		}
		public String getPublishedDate() {
			return publishedDate;
		}
		public void setPublishedDate(String publishedDate) {
			this.publishedDate = publishedDate;
		}    
}
