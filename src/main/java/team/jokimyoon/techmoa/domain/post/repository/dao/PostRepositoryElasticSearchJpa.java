package team.jokimyoon.techmoa.domain.post.repository.dao;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

import team.jokimyoon.techmoa.domain.post.repository.entity.PostDocument;

@Component
public interface PostRepositoryElasticSearchJpa extends ElasticsearchRepository<PostDocument, String> {

	List<PostDocument> findByTitleContainingOrSummaryContaining(String title, String summary);
}
