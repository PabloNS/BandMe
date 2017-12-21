package com.bandme.controller;

import javax.validation.Valid;

import com.bandme.model.Band;
import com.bandme.model.FavouriteBandsWrapper;
import com.bandme.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.bandme.model.User;
import com.bandme.service.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = {"/", "/login"}, method = RequestMethod.GET)
	public String login() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (userService.findUserByEmail(auth.getName()) != null) {
			return "redirect:/posts";
		}
		return "login";
	}

	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public String registration(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		return "registration";
	}

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public String createNewUser(@Valid User user, BindingResult bindingResult) {
		User userExists = userService.findUserByEmail(user.getEmail());
		if (userExists != null) {
			bindingResult
					.rejectValue("email", "error.user",
							"There is already a user registered with the email provided");
		}
		if (bindingResult.hasErrors()) {
			return "registration";
		} else {
			userService.saveUser(user);
			return "redirect:/login";
		}
	}

	@RequestMapping(value = "/admin/home", method = RequestMethod.GET)
	public ModelAndView home() {
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		modelAndView.addObject("userName", "Welcome " + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
		modelAndView.addObject("adminMessage", "Content Available Only for Users with Admin Role");
		modelAndView.setViewName("admin/home");
		return modelAndView;
	}

	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public String profile(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		model.addAttribute("user", user);
		return "profile";
	}

	@RequestMapping(value = "/user/updateFavouriteBands", method = RequestMethod.GET)
	public String updateUserBands(Model model){
		model.addAttribute("favouriteBands", new FavouriteBandsWrapper());
		return "updateFavouriteBands";
	}


	@RequestMapping(value="/user/updateFavouriteBands", method=RequestMethod.POST)
	public String updateUserBands(Principal principal, FavouriteBandsWrapper favouriteBandsWrapper, BindingResult bindingResult) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		user.setFavouriteBands(favouriteBandsWrapper.getListFavouriteBands());
		userService.saveUser(user);
		return "redirect:/profile";
	}
}
