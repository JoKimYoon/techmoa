package team.jokimyoon.techmoa.domain.post.repository;

import jakarta.persistence.*;
import lombok.*;
import team.jokimyoon.techmoa.global.model.BaseEntity;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String uuid;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_company_id", nullable = false)
	private PostCompany postCompany;

	@Column
	private String title;

	@Column
	private String summary;

	@Column
	private String thumbnail;

	@Column
	private LocalDateTime publishedAt;

	@Column
	private String url;

	@Builder
	public Post(PostCompany postCompany, String title, String summary, String thumbnail, LocalDateTime publishedAt, String url) {
		this.uuid = UUID.randomUUID().toString();
		this.postCompany = postCompany;
		this.title = title;
		this.summary = summary;
		this.thumbnail = thumbnail;
		this.publishedAt = publishedAt;
		this.url = url;
	}
}
