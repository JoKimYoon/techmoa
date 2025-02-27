package team.jokimyoon.techmoa.domain.post.model;

import static org.mapstruct.MappingConstants.ComponentModel.*;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import team.jokimyoon.techmoa.domain.post.model.dto.PostCompanyDto;
import team.jokimyoon.techmoa.domain.post.repository.entity.PostCompany;

@Mapper(componentModel = SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostCompanyMapper {

	PostCompanyMapper INSTANCE = Mappers.getMapper(PostCompanyMapper.class);

	PostCompany toEntity(PostCompanyDto postCompanyDto);

	PostCompanyDto toDto(PostCompany postCompany);
}
