package com.bandme.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bandme.model.Band;
import com.bandme.repository.BandRepository;

@Service
public class BandServiceImpl implements BandService{

	@Autowired
	private BandRepository bandRepository;
	
	@Override
	public List<Band> findAll() {
		return bandRepository.findAll();
	}

}
