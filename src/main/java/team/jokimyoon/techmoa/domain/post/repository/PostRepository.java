package team.jokimyoon.techmoa.domain.post.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findByPostCompany(PostCompany postCompany, Pageable pageable);
    Page<Post> findByContaining(String keyword);
}
