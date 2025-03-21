package team.jokimyoon.techmoa.domain.post.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import team.jokimyoon.techmoa.domain.post.repository.entity.Post;
import team.jokimyoon.techmoa.domain.post.repository.entity.PostCompany;

public interface PostRepositoryJpa extends JpaRepository<Post, Long> {

	List<Post> findAllByPostCompany(PostCompany postCompany);

	@Query("select p from Post p join fetch p.postCompany where p.uuid in :uuidList")
	List<Post> findAllByUuidIn(List<String> uuidList);
}
