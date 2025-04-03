package team.jokimyoon.techmoa.domain.webhook.service;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SlackWebhookSender implements WebhookSender {

	@Override
	public void sendMessage(String url, Object message) {

	}
}
