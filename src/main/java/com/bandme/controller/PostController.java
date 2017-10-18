package com.bandme.controller;

import java.security.Principal;
import java.util.Arrays;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bandme.model.Band;
import com.bandme.model.Post;
import com.bandme.service.PostServiceImpl;
import com.bandme.service.UserServiceImpl;
import com.mysql.fabric.xmlrpc.base.Array;

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
		Band b1 = new Band();
		b1.setName("nombre banda");
		b1.setId(1l);
		Band b2 = new Band();
		b2.setName("nombre banda");
		b2.setId(2l);
		post.setInfluenceBands(Arrays.asList(b1,b2));
		return "addPost";
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public String createNewPost(Principal principal, @Valid Post post, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return  "addPost";
		} else {
			post.setUser(userService.findUserByEmail(principal.getName()));
			postService.savePost(post);
			return "redirect:/posts";
		}
	}
}
