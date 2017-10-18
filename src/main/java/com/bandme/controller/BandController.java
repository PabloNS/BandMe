package com.bandme.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bandme.model.Band;
import com.bandme.service.BandServiceImpl;

@RequestMapping("bands")
@RestController
public class BandController {

	@Autowired
	private BandServiceImpl bandService;
	
	@GetMapping
	public List<Band> getAll(){
		return bandService.findAll();
	}
}
