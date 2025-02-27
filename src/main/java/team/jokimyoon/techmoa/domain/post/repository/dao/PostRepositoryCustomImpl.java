package team.jokimyoon.techmoa.domain.post.repository.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import team.jokimyoon.techmoa.domain.post.model.PostMapper;
import team.jokimyoon.techmoa.domain.post.model.dto.PostCompanyDto;
import team.jokimyoon.techmoa.domain.post.model.dto.PostDto;
import team.jokimyoon.techmoa.domain.post.model.vo.Company;
import team.jokimyoon.techmoa.domain.post.repository.PostRepositoryCustom;
import team.jokimyoon.techmoa.domain.post.repository.dao.vo.PostProjection;

@Repository
@RequiredArgsConstructor
public class PostRepositoryCustomImpl implements PostRepositoryCustom {

	private final PostRepositoryCustomJpa postRepositoryCustomJpa;
	private final PostMapper postMapper;

	@Override
	public List<PostDto> findAllByPublishedAtDesc(LocalDate lastPublishedAt, int limitSize) {
		List<PostProjection> postProjectionList
			= postRepositoryCustomJpa.findAllByPublishedAtDesc(lastPublishedAt, limitSize);

		if (postProjectionList == null) {
			return new ArrayList<>();
		}

		List<PostDto> postList = new ArrayList<>();
		for (PostProjection postProjection : postProjectionList) {
			PostCompanyDto postCompanyDto = PostCompanyDto.builder()
				.uuid(postProjection.getCompanyUuid())
				.name(Company.of(postProjection.getCompanyName()))
				.iconImage(postProjection.getCompanyIconImage())
				.build();

			PostDto post = PostDto.builder()
				.uuid(postProjection.getUuid())
				.url(postProjection.getUrl())
				.title(postProjection.getTitle())
				.summary(postProjection.getSummary())
				.publishedAt(postProjection.getPublishedAt())
				.postCompany(postCompanyDto)
				.build();

			postList.add(post);
		}

		return postList;
	}
}
