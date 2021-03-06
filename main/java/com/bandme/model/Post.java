package com.bandme.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	private User user;
	
	@ManyToOne
	@NotNull(message = "What instrument are you looking for?")
	private Instrument instrument;
	
	@NotEmpty(message = "Please provide a description")
	private String description;
	
	@ManyToMany
	@NotNull(message = "Select 3 Influence Bands")
	@Size(min=3, message = "Select 3 Influence Bands")
	private List<Band> influenceBands;
	
	@ManyToMany
	private List<MusicGenre> musicGenres;

	private Date date;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Instrument getInstrument() {
		return instrument;
	}

	public void setInstrument(Instrument instrument) {
		this.instrument = instrument;
	}

	public List<Band> getInfluenceBands() {
		return influenceBands;
	}

	public void setInfluenceBands(List<Band> influenceBands) {
		this.influenceBands = influenceBands;
	}

	public List<MusicGenre> getMusicGenres() {
		return musicGenres;
	}

	public void setMusicGenres(List<MusicGenre> musicGenres) {
		this.musicGenres = musicGenres;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
