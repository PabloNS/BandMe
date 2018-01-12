package com.bandme.controller;

import javax.validation.Valid;

import com.bandme.model.*;
import com.bandme.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.bandme.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	StorageService storageService;

	@RequestMapping(value = {"/", "/login"}, method = RequestMethod.GET)
	public String login() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (userService.findUserByEmail(auth.getName()) != null) {
			return "redirect:/posts/1";
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
		userExists = userService.findUserByNickName(user.getNickName());
		if (userExists != null) {
			bindingResult
					.rejectValue("nickName", "error.user",
							"There is already a user registered with the nickname provided");
		}
		if (bindingResult.hasErrors()) {
			return "registration";
		} else {
			userService.registerUser(user);
			return "redirect:/login?registered";
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

	@RequestMapping(value = "/viewProfile/{idUser}", method = RequestMethod.GET)
	public String viewProfile(Model model, @PathVariable("idUser") Long idUser) {
		User user = userService.findUserById(idUser);
		if(user==null){
			return "redirect:/error?profileNotFound";
		}
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		model.addAttribute("user", user);
		if(idUser == userService.findUserByEmail(auth.getName()).getId()){
			return "redirect:/profile";
		}
		return "viewProfile";
	}

	@RequestMapping(value = "/user/updateFavouriteBands", method = RequestMethod.GET)
	public String updateUserBands(Model model){
		model.addAttribute("favouriteBands", new FavouriteBandsWrapper());
		return "updateFavouriteBands";
	}


	@RequestMapping(value="/user/updateFavouriteBands", method=RequestMethod.POST)
	public String updateUserBands(FavouriteBandsWrapper favouriteBandsWrapper, BindingResult bindingResult) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		user.setFavouriteBands(favouriteBandsWrapper.getListFavouriteBands());
		userService.saveUser(user);
		return "redirect:/profile";
	}


	@RequestMapping(value="/user/updateProfilePicture", method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> updateProfilePicture(@RequestParam("file") MultipartFile multipartFile) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		if(!multipartFile.isEmpty()){
			try {
				//byte[] bytes = multipartFile.getBytes();
				//user.setProfilePicture(bytes);
				userService.saveUser(user);
			} catch (Exception e) {
				//ex.printStackTrace();
			}
		}
		//return "redirect:/profile";

		return new ResponseEntity("Successfully uploaded - " +
				multipartFile.getOriginalFilename(), new HttpHeaders(), HttpStatus.OK);
	}

	@GetMapping("profilePicture")
	@ResponseBody
	public String profilePicture(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		return user.getImageBytes();
	}

	// Multiple file upload
	@PostMapping("/changeProfilePicture")
	@ResponseBody
	public String uploadFileMulti(@RequestParam("uploadfile") MultipartFile file) {
		try {
			storageService.changeProfilePicture(file);
			return "You successfully uploaded - " + file.getOriginalFilename();
		} catch (Exception e) {
			return "FAIL! Maybe You had uploaded the file before or the file's size > 500KB";
		}
	}
}
