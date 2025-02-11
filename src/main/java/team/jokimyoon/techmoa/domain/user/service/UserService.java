package team.jokimyoon.techmoa.domain.user.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import team.jokimyoon.techmoa.domain.user.model.UserMapper;
import team.jokimyoon.techmoa.domain.user.model.dto.UserDto;
import team.jokimyoon.techmoa.domain.user.repository.User;
import team.jokimyoon.techmoa.domain.user.repository.UserRepository;
import team.jokimyoon.techmoa.global.exception.BusinessException;
import team.jokimyoon.techmoa.global.security.auth.AuthDto;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final UserMapper userMapper;

	public UserDto getUserDto(AuthDto authDto) {
		User user = userRepository.findByUuid(authDto.getUuid())
			.orElseThrow(() -> new BusinessException("User not found"));
		return userMapper.toDto(user);
	}

	public void deleteUser(AuthDto authDto) {
		User user = userRepository.findByUuid(authDto.getUuid())
			.orElseThrow(() -> new BusinessException("User not found"));
		userRepository.delete(user);
	}
}
