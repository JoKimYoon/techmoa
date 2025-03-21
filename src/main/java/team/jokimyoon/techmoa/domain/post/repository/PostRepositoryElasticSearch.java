package team.jokimyoon.techmoa.domain.post.repository;

import java.util.List;

import team.jokimyoon.techmoa.domain.post.repository.entity.Post;

public interface PostRepositoryElasticSearch {

	List<Post> findAllByKeyword(String keyword);

	void saveAllPostInElasticSearch(List<Post> postList);

	void deleteAllPostInElasticSearch();
}
