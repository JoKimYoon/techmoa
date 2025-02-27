package team.jokimyoon.techmoa.domain.post.controller;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import team.jokimyoon.techmoa.domain.post.model.dto.PostDto;
import team.jokimyoon.techmoa.domain.post.service.PostService;
import team.jokimyoon.techmoa.global.model.ApiResponse;
import team.jokimyoon.techmoa.global.model.SliceCustom;

@RequestMapping("/api/v1/posts")
@RestController
@RequiredArgsConstructor
public class PostApiController {

	private final PostService postService;

	@GetMapping
	public ApiResponse<SliceCustom<PostDto>> getPostList(
		@RequestParam(required = false) Optional<LocalDate> lastPublishedAt,
		@RequestParam(required = false) Optional<Integer> pageSize) {

		LocalDate lastPublishedAtValue = lastPublishedAt.orElse(LocalDate.now());
		Integer pageSizeValue = pageSize.orElse(SliceCustom.DEFAULT_PAGE_SIZE);

		SliceCustom<PostDto> data = postService.getPostList(lastPublishedAtValue, pageSizeValue);

		return ApiResponse.success(data);
	}
}
