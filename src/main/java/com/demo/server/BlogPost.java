package com.demo.server;

import java.util.*;

import org.codehaus.jackson.annotate.*;
import org.ektorp.docref.*;
import org.ektorp.support.*;
import org.joda.time.*;
import org.springframework.util.*;

@JsonIgnoreProperties("typeName")
public class BlogPost extends CouchDbDocument {

	private static final long serialVersionUID = 1L;
	/**
	 * @TypeDiscriminator is used to mark properties that makes this class's documents unique in the database. 
	 */
	@TypeDiscriminator
	private String title;
	private String body;
	private List<String> tags;
	private DateTime dateCreated;
	
	/**
	 * @DocumentReferences is used to refer to other documents in the database, in this case comments.
	 */
	@DocumentReferences(fetch = FetchType.LAZY, descendingSortOrder = true, orderBy = "dateCreated", backReference = "blogPostId")
	private Set<Comment> comments;

	public DateTime getDateCreated() {
		return dateCreated;
	}
	
	public void setDateCreated(DateTime dateCreated) {
		this.dateCreated = dateCreated;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getBody() {
		return body;
	}
	
	public void setBody(String body) {
		this.body = body;
	}
	
	public List<String> getTags() {
		return tags;
	}
	
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	
	@Override
	public void setRevision(String s) {
		// downstream code does not like revision set to emtpy string, which Spring does when binding
		if (s != null && !s.isEmpty()) super.setRevision(s);
	}
	
	public Set<Comment> getComments() {
		return comments;
	}
	
	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}

	public void addComment(Comment c) {
		Assert.notNull(c, "Comment may not be null");
		if (getComments() == null) {
			comments = new TreeSet<Comment>();
		}
		c.setBlogPostId(this.getId());
		comments.add(c);
	}
}
