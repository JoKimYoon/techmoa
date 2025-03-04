package team.jokimyoon.techmoa.domain.post.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import team.jokimyoon.techmoa.domain.post.model.dto.PostDto;
import team.jokimyoon.techmoa.global.model.SliceCustom;

@RequestMapping("/posts")
@Controller
@RequiredArgsConstructor
public class PostMvcController {

	@GetMapping
	public String postsPage(
		@RequestParam(required = false) Optional<LocalDateTime> lastPublishedAt,
		@RequestParam(required = false) Optional<Integer> pageSize,
		Model model) {

		LocalDateTime lastPublishedAtValue = lastPublishedAt.orElse(LocalDateTime.now());
		Integer pageSizeValue = pageSize.orElse(SliceCustom.DEFAULT_PAGE_SIZE);

		model.addAttribute("lastPublishedAt", lastPublishedAtValue);
		model.addAttribute("pageSize", pageSizeValue);

		return "pages/Post";
	}

	@PostMapping("/card-list")
	public String createPostCardList(
		@RequestBody List<PostDto> postDtoList,
		Model model) {

		model.addAttribute("dataList", postDtoList);

		return "components/post/PostCardList";
	}
}
