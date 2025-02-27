package team.jokimyoon.techmoa.domain.post.repository.dao;

import java.awt.print.Pageable;
import java.util.List;

import org.springframework.stereotype.Component;

import team.jokimyoon.techmoa.domain.post.model.dto.PostDto;
import team.jokimyoon.techmoa.domain.post.repository.PostRepositoryElasticSearch;

@Component
public class PostRepositoryElasticSearchImpl implements PostRepositoryElasticSearch {
	@Override
	public List<PostDto> findAllByKeyword(String keyword, Pageable pageable) {
		return List.of();
	}
}
