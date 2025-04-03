package team.jokimyoon.techmoa.domain.webhook.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import team.jokimyoon.techmoa.global.util.RestClientUtil;

@Service
@RequiredArgsConstructor
public class DiscordWebhookSender implements WebhookSender {

	private final RestClientUtil restClientUtil;

	@Async
	@Override
	public void sendMessage(String url, Object message) {
		restClientUtil.post(url, message, String.class);
	}
}
