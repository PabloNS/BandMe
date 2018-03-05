package com.bandme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bandme.model.User;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long> {
	User findById(Long id);
 	User findByEmail(String email);

	@Query("SELECT u FROM User u WHERE LOWER(u.nickName) = LOWER(:nickName)")
	User findByNickName(@Param("nickName") String nickName);
}
