package com.bandme.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bandme.model.Post;
import com.bandme.repository.PostRepository;

@Service("postService")
public class PostServiceImpl implements PostService{

	public static final int PAGE_SIZE = 10;

	@Autowired
	private PostRepository postRepository;
	
	@Override
	public void savePost(Post post) {
		postRepository.save(post);
	}

	@Override
	public long countAll() {
		return postRepository.count();
	}

	@Override
	public List<Post> findAll() {
		return postRepository.findAll();
	}

	@Override
	public List<Post> findAllLimited(int page) {
		Pageable limit = new PageRequest(page,PAGE_SIZE);
		Page<Post> pagePosts = postRepository.findAll(limit);
		return pagePosts.getContent();
	}

}
