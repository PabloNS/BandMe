package com.bandme.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bandme.model.Instrument;
import com.bandme.repository.InstrumentRepository;

@Service("instrumentService")
public class InstrumentServiceImpl implements InstrumentService{

	@Autowired
	private InstrumentRepository instrumentRepository;
	
	@Override
	public List<Instrument> findAll() {
		return instrumentRepository.findAll();
	}

	@Override
	public Instrument findByName(String name) {
		return instrumentRepository.findByName(name);
	}

}
