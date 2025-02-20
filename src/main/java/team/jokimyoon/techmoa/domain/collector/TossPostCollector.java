package team.jokimyoon.techmoa.domain.collector;

import org.springframework.stereotype.Component;

import team.jokimyoon.techmoa.domain.post.model.CollectorName;

@Component(CollectorName.TOSS)
public class TossPostCollector implements PostCollector {

	@Override
	public void collectPosts(String targetUrl) {

	}
}
