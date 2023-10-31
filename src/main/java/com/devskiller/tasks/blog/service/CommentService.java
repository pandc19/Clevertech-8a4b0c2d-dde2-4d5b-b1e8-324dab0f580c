package com.devskiller.tasks.blog.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.devskiller.tasks.blog.model.Comment;
import com.devskiller.tasks.blog.model.Post;
import com.devskiller.tasks.blog.model.dto.CommentDto;
import com.devskiller.tasks.blog.model.dto.NewCommentDto;
import com.devskiller.tasks.blog.repository.CommentRepository;
import com.devskiller.tasks.blog.repository.PostRepository;

@Service
public class CommentService {

	private final CommentRepository commentRepository;
	private final PostRepository postRepository;

	public CommentService(CommentRepository commentRepository,PostRepository postRepository) {
		this.commentRepository = commentRepository;
		this.postRepository = postRepository;
	}
	
	/**
	 * Returns a list of all comments for a blog post with passed id.
	 *
	 * @param postId id of the post
	 * @return list of comments sorted by creation date descending - most recent first
	 */
	public List<CommentDto> getCommentsForPost(Long postId) {
		return commentRepository.findAllByPostId(postId)
				.stream()
				.map(comment -> new CommentDto(comment.getId(),comment.getContent(), comment.getAuthor(), comment.getCreationDate()))
				.toList();
	}

	/**
	 * Creates a new comment
	 *
	 * @param postId id of the post
	 * @param newCommentDto data of new comment
	 * @return id of the created comment
	 *
	 * @throws IllegalArgumentException if postId is null or there is no blog post for passed postId
	 */
	public Long addComment(Long postId, NewCommentDto newCommentDto) {
		Post post = postRepository.findById(postId).orElse(null);

		if(post == null) return -1L;
		
		Comment comment = new Comment();
		comment.setAuthor(newCommentDto.author());
		comment.setContent(newCommentDto.content());
		comment.setCreationDate(LocalDateTime.now());
		comment.setPost(post);
		
		Comment createdComment = commentRepository.save(comment);
		
		return createdComment.getId();
		
	}
}
