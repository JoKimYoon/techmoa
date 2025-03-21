package team.jokimyoon.techmoa.domain.post.repository.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import team.jokimyoon.techmoa.domain.post.repository.PostRepositoryElasticSearch;
import team.jokimyoon.techmoa.domain.post.repository.PostRepositoryJpa;
import team.jokimyoon.techmoa.domain.post.repository.entity.Post;
import team.jokimyoon.techmoa.domain.post.repository.entity.PostDocument;

@Repository
@RequiredArgsConstructor
public class PostRepositoryElasticSearchImpl implements PostRepositoryElasticSearch {

	private final PostRepositoryElasticSearchJpa postRepositoryElasticSearchJpa;
	private final PostRepositoryJpa postRepositoryJpa;

	@Override
	public List<Post> findAllByKeyword(String keyword) {
		List<PostDocument> postList
			= postRepositoryElasticSearchJpa.findByTitleContainingOrSummaryContaining(keyword, keyword);

		if (postList.isEmpty()) {
			return new ArrayList<>();
		}

		List<String> uuidList = postList.stream().map(PostDocument::getUuid).toList();

		return postRepositoryJpa.findAllByUuidIn(uuidList);
	}

	@Override
	public void saveAllPostInElasticSearch(List<Post> postList) {
		List<PostDocument> postDocumentList = postList.stream()
			.map(o -> PostDocument.builder()
				.uuid(o.getUuid())
				.title(o.getTitle())
				.summary(o.getSummary())
				.build()
			).toList();
		postRepositoryElasticSearchJpa.saveAll(postDocumentList);
	}

	@Override
	public void deleteAllPostInElasticSearch() {
		postRepositoryElasticSearchJpa.deleteAll();
	}
}
