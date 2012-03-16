package com.demo.server;

import org.ektorp.support.*;
import org.joda.time.*;

public class Comment extends CouchDbDocument implements Comparable<Comment> {

	private static final long serialVersionUID = 1L;

	private String blogPostId;
	private String comment;
	private DateTime dateCreated;
	private String email;
	
	public String getBlogPostId() {
		return blogPostId;
	}
	
	public void setBlogPostId(String blogPostId) {
		this.blogPostId = blogPostId;
	}
	
	public String getComment() {
		return comment;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public DateTime getDateCreated() {
		return dateCreated;
	}
	
	public void setDateCreated(DateTime dateCreated) {
		this.dateCreated = dateCreated;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String username) {
		this.email = username;
	}

	@Override
	public int compareTo(Comment other) {
		if (other == this) return 0;
		if (dateCreated != null) {
			return - dateCreated.compareTo(other.dateCreated);	
		}
		return 0;
	}
	
}
