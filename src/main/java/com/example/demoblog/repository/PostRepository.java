package com.example.demoblog.repository;

import com.example.demoblog.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {

   Page<Post> findAll(Pageable pageable);
}
