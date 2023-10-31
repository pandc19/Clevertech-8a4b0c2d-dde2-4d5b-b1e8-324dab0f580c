package com.devskiller.tasks.blog.rest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.devskiller.tasks.blog.model.dto.CommentDto;
import com.devskiller.tasks.blog.model.dto.NewCommentDto;
import com.devskiller.tasks.blog.model.dto.PostDto;
import com.devskiller.tasks.blog.service.CommentService;
import com.devskiller.tasks.blog.service.PostService;

import jakarta.servlet.http.HttpServletResponse;

@Controller
@RestController
@RequestMapping("/posts")
public class PostController {

	private final PostService postService;
	private final CommentService commentService;


	public PostController(PostService postService, CommentService commentService) {
		this.postService = postService;
		this.commentService = commentService;
	}

	@GetMapping(value = "/{id}")
	@ResponseStatus(HttpStatus.OK)
	public PostDto getPost(@PathVariable Long id) {
		return postService.getPost(id);
	}
	
	@GetMapping(value = "/{postId}/comments")
	@ResponseStatus(HttpStatus.OK)
	public List<CommentDto> getCommentsForPost(@PathVariable Long postId) {
		return commentService.getCommentsForPost(postId);
	}
	
	@PostMapping(value = "/{postId}/comments")
//	@ResponseStatus(HttpStatus.CREATED)
	public void addComment(@PathVariable Long postId, @RequestBody NewCommentDto newCommentDto, HttpServletResponse response) {
		if(commentService.addComment(postId, newCommentDto) == -1L) 
		    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		else
		    response.setStatus(HttpServletResponse.SC_CREATED);

	}


}
