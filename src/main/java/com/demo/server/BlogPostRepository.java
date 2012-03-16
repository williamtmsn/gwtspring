package com.demo.server;

import java.util.*;

import org.ektorp.*;
import org.ektorp.support.*;
import org.ektorp.util.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Component
public class BlogPostRepository extends CouchDbRepositorySupport<BlogPost> {

	@Autowired
	public BlogPostRepository(@Qualifier("blogPostDatabase") CouchDbConnector db) {
		super(BlogPost.class, db);
		initStandardDesignDocument();
	}

	@GenerateView @Override
	public List<BlogPost> getAll() {
		ViewQuery q = createQuery("all")
						.descending(true)
						.includeDocs(true);
		return db.queryView(q, BlogPost.class);
	}
	
	public Page<BlogPost> getAll(PageRequest pr) {
		ViewQuery q = createQuery("all")
						.descending(true)
						.includeDocs(true);
		return db.queryForPage(q, pr, BlogPost.class);
	}
	
	@GenerateView
	public List<BlogPost> findByTag(String tag) {
		return queryView("by_tag", tag);
	}
	
	public void addComment(Comment c) {
		Assert.notNull(c, "Comment may not be null");
		Assert.hasText(c.getBlogPostId(), "Comment must have a blog post id");
		db.create(c);
	}

}
