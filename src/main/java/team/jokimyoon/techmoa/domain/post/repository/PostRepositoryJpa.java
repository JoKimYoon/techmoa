package team.jokimyoon.techmoa.domain.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepositoryJpa extends JpaRepository<Post, Long> {

	// SELECT *
	// FROM post_sorted_by_published_at a
	// JOIN post b ON a.id = b.id
	// WHERE a.id IN (
	// 	SELECT id FROM post_sorted_by_published_at
	// 		WHERE id > :last_index
	// 		LIMIT 20
	// );
	// Slice<Post> findAll(Pageable pageable);

}
