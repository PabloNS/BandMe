package com.bandme.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bandme.model.Post;

public interface PostRepository extends JpaRepository<Post, Long>{

}
