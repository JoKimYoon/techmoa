package team.jokimyoon.techmoa.domain.post.repository.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@Document(indexName = "post_document")
@AllArgsConstructor
public class PostDocument {

	@Id
	private String id;
	private String uuid;
	private String title;
	private String summary;
}
