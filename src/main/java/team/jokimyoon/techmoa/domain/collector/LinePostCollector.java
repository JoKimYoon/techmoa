package team.jokimyoon.techmoa.domain.collector;

import org.springframework.stereotype.Component;

import team.jokimyoon.techmoa.domain.post.model.CollectorName;

@Component(CollectorName.LINE)
public class LinePostCollector implements PostCollector {

	@Override
	public void collectPosts(String targetUrl) {

	}
}
