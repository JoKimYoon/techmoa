package team.jokimyoon.techmoa.domain.post.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import team.jokimyoon.techmoa.domain.post.model.vo.Company;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostCompanyDto {
	private String uuid;
	private Company name;
	private String iconImage;
}
