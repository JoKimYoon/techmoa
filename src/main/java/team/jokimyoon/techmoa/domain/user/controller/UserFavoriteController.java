package team.jokimyoon.techmoa.domain.user.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import team.jokimyoon.techmoa.domain.user.model.dto.UserFavoriteDto;
import team.jokimyoon.techmoa.domain.user.service.UserFavoriteService;
import team.jokimyoon.techmoa.global.model.ApiResponse;
import team.jokimyoon.techmoa.global.security.auth.Auth;
import team.jokimyoon.techmoa.global.security.auth.AuthDto;

@RequestMapping("/api/users/favorites")
@RestController
@RequiredArgsConstructor
public class UserFavoriteController {

	private final UserFavoriteService userFavoriteService;

	@GetMapping
	public ApiResponse<List<UserFavoriteDto>> getFavorites(@Auth AuthDto authDto) {

	}

	@PostMapping("/{postUuid}")
	public ApiResponse<List<UserFavoriteDto>> addFavorite(
		@Auth AuthDto authDto,
		@RequestBody UserFavoriteDto userFavoriteDto) {

	}

	@DeleteMapping("")
	public ApiResponse<List<UserFavoriteDto>> deleteFavorite(
		@Auth AuthDto authDto,
		@RequestBody UserFavoriteDto userFavoriteDto) {

	}

}
