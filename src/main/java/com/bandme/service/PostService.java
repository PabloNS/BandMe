package com.bandme.service;

import java.util.List;

import com.bandme.model.Post;

public interface PostService {
	public void savePost(Post post);
	public List<Post> findAll();
}
