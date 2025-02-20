package team.jokimyoon.techmoa.domain.collector;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import team.jokimyoon.techmoa.domain.collector.model.CollectorName;

@Component(CollectorName.TOSS)
@RequiredArgsConstructor
public class TossPostCollector implements PostCollector {

	@Async
	@Override
	public void collectPosts(String targetUrl) {

	}
}
