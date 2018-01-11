package com.bandme.controller;

import java.security.Principal;

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

	/*@RequestMapping
	public String getAllPosts(Model model) {
		int page = 0;
		model.addAttribute("posts", postService.findAllLimited(page));
		return "posts";
	}*/

	@RequestMapping("/{page}")
	public String getAllPosts(Model model, @PathVariable("page") int page) {
		long totalPosts = postService.countAll();
		long lastPage = totalPosts/PostServiceImpl.PAGE_SIZE;
		if(page>lastPage-1){
			return "redirect:/posts/"+(lastPage-1);
		}
		model.addAttribute("posts", postService.findAllLimited(page));
		model.addAttribute("currentPage", page);
		model.addAttribute("lastPage", lastPage-1);
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
			postService.savePost(post);
			return "redirect:/posts";
		}
	}
}
