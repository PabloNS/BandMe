package com.bandme.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bandme.model.Instrument;

public interface InstrumentRepository extends JpaRepository<Instrument, Long>{
}
