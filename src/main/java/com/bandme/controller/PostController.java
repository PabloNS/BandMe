package com.bandme.controller;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
	
	@RequestMapping
//	@PreAuthorize ("hasRole('USER')")
	public String getAllPosts(Model model) {
		model.addAttribute("posts", postService.findAll());
		return "posts";
	}
	
	@RequestMapping("/addPost")
	public String addPost(Model model) {
		Post post = new Post();
		model.addAttribute("post", post);
		return "addPost";
	}
	
	@RequestMapping(method=RequestMethod.POST)
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
