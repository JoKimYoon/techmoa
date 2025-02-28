package team.jokimyoon.techmoa.domain.post.model;

import static org.mapstruct.MappingConstants.ComponentModel.*;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import team.jokimyoon.techmoa.domain.post.model.dto.PostDto;
import team.jokimyoon.techmoa.domain.post.repository.entity.Post;

@Mapper(componentModel = SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = PostCompanyMapper.class)
public interface PostMapper {

	PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

	@Mapping(source = "postCompany", target = "postCompany")
	Post toEntity(PostDto postDto);

	@Mapping(source = "postCompany", target = "postCompany")
	PostDto toDto(Post post);
}
