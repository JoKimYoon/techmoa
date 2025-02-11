package team.jokimyoon.techmoa.domain.user.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import team.jokimyoon.techmoa.domain.user.model.dto.UserFavoriteDto;
import team.jokimyoon.techmoa.domain.user.repository.UserFavoriteRepository;

@Service
@RequiredArgsConstructor
public class UserFavoriteService {

	private final UserFavoriteRepository userFavoriteRepository;

	public List<UserFavoriteDto>
}
