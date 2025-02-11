package team.jokimyoon.techmoa.domain.user.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import team.jokimyoon.techmoa.domain.user.model.dto.UserDto;
import team.jokimyoon.techmoa.domain.user.service.UserService;
import team.jokimyoon.techmoa.global.model.ApiResponse;
import team.jokimyoon.techmoa.global.security.auth.Auth;
import team.jokimyoon.techmoa.global.security.auth.AuthDto;

@RequestMapping("/api/users")
@RestController
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@GetMapping("/me")
	public ApiResponse<UserDto> getUserInformation(@Auth AuthDto authDto) {
		UserDto userDto = userService.getUserDto(authDto);
		return ApiResponse.success(userDto);
	}

	@DeleteMapping("/me")
	public ApiResponse<String> deleteUser(@Auth AuthDto authDto) {
		userService.deleteUser(authDto);
		return ApiResponse.success();
	}
}
