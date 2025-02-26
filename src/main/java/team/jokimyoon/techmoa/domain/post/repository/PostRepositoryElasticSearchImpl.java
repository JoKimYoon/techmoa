package team.jokimyoon.techmoa.domain.post.repository;

import java.awt.print.Pageable;
import java.util.List;

import org.springframework.stereotype.Component;

import team.jokimyoon.techmoa.domain.post.model.dto.PostDto;

@Component
public class PostRepositoryElasticSearchImpl implements PostRepositoryElasticSearch {
	@Override
	public List<PostDto> findAllByKeyword(String keyword, Pageable pageable) {
		return List.of();
	}
}
