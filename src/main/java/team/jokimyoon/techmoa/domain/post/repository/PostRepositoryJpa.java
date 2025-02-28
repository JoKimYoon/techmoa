package team.jokimyoon.techmoa.domain.post.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import team.jokimyoon.techmoa.domain.post.repository.entity.Post;
import team.jokimyoon.techmoa.domain.post.repository.entity.PostCompany;

public interface PostRepositoryJpa extends JpaRepository<Post, Long> {

	List<Post> findAllByPostCompany(PostCompany postCompany);
}
