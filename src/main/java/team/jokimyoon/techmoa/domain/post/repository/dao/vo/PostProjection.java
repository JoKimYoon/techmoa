package team.jokimyoon.techmoa.domain.post.repository.dao.vo;

import java.time.LocalDateTime;

public interface PostProjection {
	String getUuid();

	String getTitle();

	String getSummary();

	String getUrl();

	LocalDateTime getPublishedAt();

	String getCompanyUuid();

	String getCompanyName();

	String getCompanyIconImage();
}
