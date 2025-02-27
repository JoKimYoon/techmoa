package team.jokimyoon.techmoa.domain.post.model.vo;

import java.util.Locale;

import lombok.Getter;
import team.jokimyoon.techmoa.domain.collector.model.vo.CollectorName;
import team.jokimyoon.techmoa.global.exception.BusinessException;

@Getter
public enum Company {
	KURLY(CollectorName.KURLY),
	LINE(CollectorName.LINE),
	TOSS(CollectorName.TOSS);

	private final String collectorName;

	public static Company of(String name) {
		return switch (name.toUpperCase(Locale.ROOT)) {
			case "KURLY" -> KURLY;
			case "LINE" -> LINE;
			case "TOSS" -> TOSS;
			default -> throw new BusinessException("Invalid company name: " + name);
		};
	}

	Company(String collectorName) {
		this.collectorName = collectorName;
	}
}
