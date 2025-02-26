package team.jokimyoon.techmoa.domain.post.repository;

import java.awt.print.Pageable;
import java.util.List;

import team.jokimyoon.techmoa.domain.post.model.dto.PostDto;

public interface PostRepositoryElasticSearch {

	List<PostDto> findAllByKeyword(String keyword, Pageable pageable);
}
