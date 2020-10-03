package com.example.demo.repostory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer>{
	List<Comment> findByProjectId(Integer projectId);
	
	@Query(
			value = "SELECT DISTINCT c.project_id "
					+ "FROM comment c "
					+ "ORDER BY c.id "
					+ "DESC LIMIT 10 ", 
			nativeQuery = true)
	List<String> findIdProjectHaveNewComment();
}
