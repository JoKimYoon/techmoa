package team.jokimyoon.techmoa.domain.post.model.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
	private String uuid;
	private String title;
	private String summary;
	private String url;
	private LocalDateTime publishedAt;
	private PostCompanyDto postCompany;
}


