package team.jokimyoon.techmoa.domain.post.repository.dao.vo;

import java.time.LocalDate;

public interface PostProjection {
	String getUuid();

	String getTitle();

	String getSummary();

	String getUrl();

	LocalDate getPublishedAt();

	String getCompanyUuid();

	String getCompanyName();

	String getCompanyIconImage();
}
