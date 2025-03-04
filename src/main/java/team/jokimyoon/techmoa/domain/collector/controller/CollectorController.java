package team.jokimyoon.techmoa.domain.collector.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import team.jokimyoon.techmoa.domain.collector.PostCollectorScheduler;
import team.jokimyoon.techmoa.domain.post.repository.PostRepository;
import team.jokimyoon.techmoa.global.model.ApiResponse;

@RequestMapping("/api/v1/collectors")
@RestController
@RequiredArgsConstructor
public class CollectorController {

	private final PostCollectorScheduler postCollectorScheduler;
	private final PostRepository postRepository;

	//todo: admin 제한
	@PostMapping("/re-collect")
	public ApiResponse<String> recollectPosts() {
		postRepository.deleteAllInBatch();
		postRepository.flush();
		postCollectorScheduler.collectPosts();
		return ApiResponse.success();
	}
}
