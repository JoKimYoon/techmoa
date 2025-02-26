package team.jokimyoon.techmoa.global.util;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RestClientUtil {

	private final RestClient restClient;

	public <T> T get(String uri, Class<T> clazz) {
		return restClient.get()
			.uri(uri)
			.retrieve()
			.body(clazz);
	}
}
