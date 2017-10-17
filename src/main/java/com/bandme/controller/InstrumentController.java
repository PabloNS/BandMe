package com.bandme.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bandme.model.Instrument;
import com.bandme.service.InstrumentServiceImpl;

@RequestMapping("instruments")
@RestController
public class InstrumentController {

	@Autowired
	private InstrumentServiceImpl instrumentService;
	
	@GetMapping
	public List<Instrument> getAll(){
		return instrumentService.findAll();
	}
}
