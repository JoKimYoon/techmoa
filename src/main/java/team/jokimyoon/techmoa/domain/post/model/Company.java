package team.jokimyoon.techmoa.domain.post.model;

import lombok.Getter;

@Getter
public enum Company {
	KURLY(CollectorName.KCURLY),
	LINE(CollectorName.LINE),
	TOSS(CollectorName.TOSS);

	private final String collectorName;

	Company(String collectorName) {
		this.collectorName = collectorName;
	}
}
