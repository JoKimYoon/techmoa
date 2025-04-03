package team.jokimyoon.techmoa.global.model;

import java.time.DayOfWeek;

import lombok.Getter;

public enum KoreanDay {
	MONDAY("월"),
	TUESDAY("화"),
	WEDNESDAY("수"),
	THURSDAY("목"),
	FRIDAY("금"),
	SATURDAY("토"),
	SUNDAY("일");

	@Getter
	final String koreanDay;

	public static KoreanDay valueOf(DayOfWeek day) {
		return switch (day) {
			case MONDAY -> KoreanDay.MONDAY;
			case TUESDAY -> KoreanDay.TUESDAY;
			case WEDNESDAY -> KoreanDay.WEDNESDAY;
			case THURSDAY -> KoreanDay.THURSDAY;
			case FRIDAY -> KoreanDay.FRIDAY;
			case SATURDAY -> KoreanDay.SATURDAY;
			case SUNDAY -> KoreanDay.SUNDAY;
		};
	}

	KoreanDay(String koreanDay) {
		this.koreanDay = koreanDay;
	}
}
