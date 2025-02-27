package team.jokimyoon.techmoa.domain.post.repository.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import team.jokimyoon.techmoa.domain.post.repository.dao.vo.PostProjection;
import team.jokimyoon.techmoa.domain.post.repository.entity.Post;

public interface PostRepositoryCustomJpa extends JpaRepository<Post, Long> {

	@Query(value = """
		 SELECT
			 A.uuid AS uuid,
			 A.guid AS guid,
			 A.title AS title,
			 A.summary AS summary,
			 A.url AS url,
			 A.published_at AS publishedAt,
			 B.uuid AS companyUuid,
			 B.name AS companyName,
			 B.icon_image AS companyIconImage
		FROM post A
		LEFT JOIN post_company B FORCE INDEX (`PRIMARY`) ON A.post_company_id = B.id
		WHERE A.published_at < :lastPublishedAt
		ORDER BY A.published_at DESC
		LIMIT :limitSize;
		""", nativeQuery = true)
	List<PostProjection> findAllByPublishedAtDesc(LocalDate lastPublishedAt, int limitSize);

}
