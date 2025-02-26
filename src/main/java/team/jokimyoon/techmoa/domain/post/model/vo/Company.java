package team.jokimyoon.techmoa.domain.post.model.vo;

import lombok.Getter;
import team.jokimyoon.techmoa.domain.collector.model.CollectorName;

@Getter
public enum Company {
	KURLY(CollectorName.KURLY),
	LINE(CollectorName.LINE),
	TOSS(CollectorName.TOSS);

	private final String collectorName;

	Company(String collectorName) {
		this.collectorName = collectorName;
	}
}
