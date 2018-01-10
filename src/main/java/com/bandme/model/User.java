package com.bandme.model;

import java.util.List;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Transient;

@Entity
@Table(name = "user")
public class User {

	@Lob
	private String imageBytes;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="user_id")
	private Long id;
	
	@Email(message = "Please provide a valid Email")
	@NotEmpty(message = "Please provide an email")
	private String email;
	
	@Length(min = 5, message = "Your password must have at least 5 characters")
	@NotEmpty(message = "Please provide your password")
	@Transient
	private String password;
	
	@NotEmpty(message = "Please provide your name")
	private String name;
	
	@NotEmpty(message = "Please provide your last name")
	private String lastName;
	
	private int active;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles;
	
	@ManyToMany
	private List<Band> favouriteBands;
	
	@ManyToMany
	private List<MusicGenre> favouriteGenres;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public List<Band> getFavouriteBands() {
		return favouriteBands;
	}

	public void setFavouriteBands(List<Band> favouriteBands) {
		this.favouriteBands = favouriteBands;
	}

	public List<MusicGenre> getFavouriteGenres() {
		return favouriteGenres;
	}

	public void setFavouriteGenres(List<MusicGenre> favouriteGenres) {
		this.favouriteGenres = favouriteGenres;
	}

	public String getImageBytes() {
		return imageBytes;
	}

	public void setImageBytes(String imageBytes) {
		this.imageBytes = imageBytes;
	}
}
