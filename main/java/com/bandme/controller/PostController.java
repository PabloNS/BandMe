package com.bandme.controller;

import java.security.Principal;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bandme.model.Post;
import com.bandme.service.PostServiceImpl;
import com.bandme.service.UserServiceImpl;

@RequestMapping("posts")
@Controller
public class PostController {

	@Autowired
	private PostServiceImpl postService;
	
	@Autowired
	private UserServiceImpl userService;
	
	/*@RequestMapping
//	@PreAuthorize ("hasRole('USER')")
	public String getAllPosts(Model model) {
		model.addAttribute("posts", postService.findAll());
		return "posts";
	}*/

	@RequestMapping
	public String getAllPosts(Model model) {
		return "redirect:/posts/1";
	}

	@RequestMapping("/{page}")
	public String getAllPosts(Model model, @PathVariable("page") int page) {
		long totalPosts = postService.countAll();
		long lastPage = totalPosts/ PostServiceImpl.PAGE_SIZE;
		if(totalPosts<PostServiceImpl.PAGE_SIZE){
			lastPage=1;
		}
		if(page>lastPage && totalPosts>=PostServiceImpl.PAGE_SIZE){
			return "redirect:/posts/"+(lastPage);
		}

		//page-1 because Spring uses 0 as first page index but we dont want the user to see that
		model.addAttribute("posts", postService.findAllLimited(page-1));
		model.addAttribute("currentPage", page);
		model.addAttribute("lastPage", lastPage);
		return "posts";
	}
	
	@RequestMapping("/addPost")
	public String addPost(Model model) {
		Post post = new Post();
		model.addAttribute("post", post);
		return "addPost";
	}
	
	@RequestMapping(value="/addPost", method=RequestMethod.POST)
	public String createNewPost(Principal principal, @Valid Post post, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "addPost";
		} else {
			post.setUser(userService.findUserByEmail(principal.getName()));
			post.setDate(new Date());
			postService.savePost(post);
			return "redirect:/posts/1";
		}
	}
}
