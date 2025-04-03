package team.jokimyoon.techmoa.domain.post.repository;

import java.time.LocalDateTime;
import java.util.List;

import team.jokimyoon.techmoa.domain.post.model.dto.PostDto;

public interface PostRepositoryCustom {
	List<PostDto> findAllByPublishedAtDesc(LocalDateTime lastPublishedAt, int limitSize);

	List<PostDto> findAllByPublishedAtDesc(LocalDateTime startDateTime, LocalDateTime endDateTime);
}
