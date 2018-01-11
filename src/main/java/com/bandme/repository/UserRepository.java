package com.bandme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bandme.model.User;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long> {
	User findById(Long id);
 	User findByEmail(String email);
}
