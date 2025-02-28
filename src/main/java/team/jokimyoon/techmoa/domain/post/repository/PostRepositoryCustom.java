package team.jokimyoon.techmoa.domain.post.repository;

import java.time.LocalDate;
import java.util.List;

import team.jokimyoon.techmoa.domain.post.model.dto.PostDto;

public interface PostRepositoryCustom {
	List<PostDto> findAllByPublishedAtDesc(LocalDate lastPublishedAt, int limitSize);
}
