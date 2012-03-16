package com.demo.server;

import javax.servlet.http.*;

import org.ektorp.*;
import org.joda.time.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.*;

@Controller
public class BlogController {

	@Autowired
	BlogPostRepository blogPostRepo;
	
	@RequestMapping( value = "/posts/", method = RequestMethod.GET)
	public String viewAll(Model m, @RequestParam(value = "p", required = false) String pageLink) {
		PageRequest pr = pageLink != null ? PageRequest.fromLink(pageLink) : PageRequest.firstPage(5);
		m.addAttribute(blogPostRepo.getAll(pr));
		return "/posts/index";
	}
	
	@RequestMapping( value = "/posts/new", method = RequestMethod.GET)
	public ModelAndView newPost() {
		return new ModelAndView("/posts/edit", "command", new BlogPost());
	}
	
	@RequestMapping( value = "/posts/", method = RequestMethod.POST)
	public String submitPost(@ModelAttribute("command") BlogPost post) {
		if (post.isNew()) {
			post.setId(createId(post.getTitle()));
			post.setDateCreated(new DateTime());
			blogPostRepo.add(post);
		} else {
			blogPostRepo.update(post);	
		}
		
		return "redirect:/blog/posts/";
	}
	
	@ExceptionHandler(UpdateConflictException.class)
	public ModelAndView onUpdateConflictException(HttpServletRequest req) {
		ModelAndView mav = new ModelAndView("posts/update_conflict");
		BlogPost existing = blogPostRepo.get(req.getParameter("title"));
		mav.addObject("command", existing);
		return mav;
	}
	
	private String createId(String title) {
		return title.replaceAll("\\s", "-");
	}

	@RequestMapping("/posts/{postId}")
	public ModelAndView viewPost(@PathVariable("postId") String postId) {
		ModelAndView model = new ModelAndView("/posts/view");
		model.addObject(blogPostRepo.get(postId));
//		model.addObject(commentRepo.findByBlogPostId(postId));
		return model;
	}
	
	@RequestMapping( value = "/posts/{postId}/edit", method = RequestMethod.GET)
	public BlogPost editPost(@PathVariable("postId") String postId) {
		return blogPostRepo.get(postId);
	}
	
	@RequestMapping( value = "/posts/{postId}/comment", method = RequestMethod.POST)
	public String addComment(@PathVariable("postId") String postId, @ModelAttribute("command") Comment comment) {
		comment.setBlogPostId(postId);
		comment.setDateCreated(new DateTime());
		blogPostRepo.addComment(comment);
		return "redirect:/blog/posts/" + comment.getBlogPostId();
	}
}
