package team.jokimyoon.techmoa.domain.post.repository;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.jokimyoon.techmoa.global.model.BaseEntity;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String uuid;

	@Column
	private String guid;

	@Column
	private String title;

	public void changeTitle(String title) {
		this.title = title;
	}

	@Column
	private String summary;

	public void changeSummary(String newSummary) {
		this.summary = newSummary;
	}

	@Column
	private String url;

	public void changeUrl(String url) {
		this.url = url;
	}

	@Column
	private LocalDate publishedAt;

	public void changePublishedAt(LocalDate publishedAt) {
		this.publishedAt = publishedAt;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_company_id")
	private PostCompany postCompany;

	@Builder
	public Post(
		String guid,
		String title,
		String summary,
		String url,
		LocalDate publishedAt,
		PostCompany postCompany) {

		this.uuid = UUID.randomUUID().toString();
		this.guid = guid;
		this.title = title;
		this.summary = summary;
		this.url = url;
		this.publishedAt = publishedAt;
		this.postCompany = postCompany;
	}

}
