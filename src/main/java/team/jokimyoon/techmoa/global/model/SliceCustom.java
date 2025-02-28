package team.jokimyoon.techmoa.global.model;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SliceCustom<T> {
	public static final int DEFAULT_PAGE_SIZE = 20;
	@Builder.Default
	private final int pageSize = 20;
	private final boolean hasNext;
	private final List<T> data;
	private final Object lastIndex;
}
