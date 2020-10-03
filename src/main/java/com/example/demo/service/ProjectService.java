package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Comment;
import com.example.demo.repostory.CommentRepository;

@Service
public class ProjectService {
	
	@Autowired
	private CommentRepository commentRepository;
	
	public Comment addComment(Comment comment) {
		LocalDateTime dateTime = LocalDateTime.now();
		comment.setCommentTime(dateTime);
		return commentRepository.save(comment);
	}
	
	public List<Comment> getCommentsByIdProject(Integer id) {
		return commentRepository.findByProjectId(id);
	}
	
	public List<String> getIdProjectHaveNewComment() {
		return commentRepository.findIdProjectHaveNewComment();
	}
}
