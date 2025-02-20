package team.jokimyoon.techmoa.domain.collector;

import org.springframework.stereotype.Component;

import team.jokimyoon.techmoa.domain.post.model.CollectorName;

@Component(CollectorName.KCURLY)
public class KcurlyPostCollector implements PostCollector {

	@Override
	public void collectPosts(String targetUrl) {

	}
}
