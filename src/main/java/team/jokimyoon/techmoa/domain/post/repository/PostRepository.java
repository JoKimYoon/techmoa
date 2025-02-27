package team.jokimyoon.techmoa.domain.post.repository;

import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository
	extends PostRepositoryElasticSearch, PostRepositoryJpa, PostRepositoryCustom {
}
