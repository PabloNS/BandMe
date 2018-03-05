package com.bandme.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bandme.model.Band;

public interface BandRepository extends JpaRepository<Band, Long>{

    Band findByName(String name);
}
