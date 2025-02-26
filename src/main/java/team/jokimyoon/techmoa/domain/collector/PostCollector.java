package team.jokimyoon.techmoa.domain.collector;

import org.springframework.stereotype.Component;

@Component
public interface PostCollector {

	void collectPosts(String postCompanyUrl, Long postCompanyId);
}
