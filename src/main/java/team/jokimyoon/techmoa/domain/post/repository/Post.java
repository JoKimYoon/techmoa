package team.jokimyoon.techmoa.domain.post.repository;

import java.time.LocalDateTime;

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
@Builder
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
	private String title;

	@Column
	private String summary;

	@Column
	private String url;

	@Column
	private LocalDateTime publishedAt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_company_id")
	private PostCompany postCompany;

}
