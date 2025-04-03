package team.jokimyoon.techmoa.domain.webhook.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import team.jokimyoon.techmoa.domain.webhook.service.WebHookScheduler;
import team.jokimyoon.techmoa.global.model.ApiResponse;

@RequestMapping("/api/v1/webhook")
@RestController
@RequiredArgsConstructor
public class WebhookController {

	private final WebHookScheduler webHookScheduler;

	@GetMapping("/re-send")
	public ApiResponse<String> sendTest() {
		webHookScheduler.sendNewPosts();
		return ApiResponse.success();
	}
}
