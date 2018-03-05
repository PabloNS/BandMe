package com.bandme.service;

import java.util.List;

import com.bandme.model.Instrument;

public interface InstrumentService {

	List<Instrument> findAll();

	Instrument findByName(String name);
}
