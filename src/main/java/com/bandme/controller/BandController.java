package com.bandme.controller;

import java.util.List;

import com.bandme.model.User;
import com.bandme.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bandme.model.Band;
import com.bandme.service.BandServiceImpl;

@RequestMapping("bands")
@RestController
public class BandController {

	@Autowired
	private BandServiceImpl bandService;

	@Autowired
	private UserService userService;
	
	@GetMapping
	public List<Band> getAll(){
		return bandService.findAll();
	}

	@RequestMapping(value="/user", method = RequestMethod.GET)
	public List<Band> favouriteUserBands(Model model){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		return user.getFavouriteBands();
	}
}
