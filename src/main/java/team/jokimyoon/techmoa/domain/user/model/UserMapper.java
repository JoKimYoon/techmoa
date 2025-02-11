package team.jokimyoon.techmoa.domain.user.model;

import static org.mapstruct.MappingConstants.ComponentModel.*;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import team.jokimyoon.techmoa.domain.user.model.dto.UserDto;
import team.jokimyoon.techmoa.domain.user.repository.User;
import team.jokimyoon.techmoa.global.security.oauth.Oauth2UserInfo;

@Mapper(componentModel = SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

	UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

	User toEntity(Oauth2UserInfo oauth2UserInfo);

	User toEntity(UserDto userDto);

	UserDto toDto(User user);
}
