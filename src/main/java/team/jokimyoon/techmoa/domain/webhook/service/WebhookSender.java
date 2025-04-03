package team.jokimyoon.techmoa.domain.webhook.service;

import org.springframework.stereotype.Service;

@Service
public interface WebhookSender {

	void sendMessage(String url, Object message);
}
