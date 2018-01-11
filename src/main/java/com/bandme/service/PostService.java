package com.bandme.service;

import java.util.List;

import com.bandme.model.Post;
import org.springframework.data.domain.Pageable;

public interface PostService {
	public void savePost(Post post);
	public List<Post> findAllLimited(int page);
	public List<Post> findAll();
	public long countAll();
}
