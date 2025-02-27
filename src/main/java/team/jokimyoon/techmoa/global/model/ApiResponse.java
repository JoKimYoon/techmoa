package team.jokimyoon.techmoa.global.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApiResponse<T> {

	private int statusCode;
	private T data;
	private String message;

	private static <T> ApiResponse<T> create(final int statusCode, final String message) {
		return create(null, statusCode, message);
	}

	private static <T> ApiResponse<T> create(final T data, final int statusCode, final String message) {
		return ApiResponse.<T>builder()
			.data(data)
			.statusCode(statusCode)
			.message(message)
			.build();
	}

	public static <T> ApiResponse<T> success(final T data) {
		return ApiResponse.create(data, 200, null);
	}

	public static <T> ApiResponse<T> success() {
		return ApiResponse.create(null, 200, null);
	}

	public static <T> ApiResponse<T> error(final int statusCode, final String message) {
		return create(null, statusCode, message);
	}

	public static <T> ApiResponse<T> error(final T data, final int statusCode, final String message) {
		return create(data, statusCode, message);
	}

	public static <T> ApiResponse<T> error(final int statusCode, Throwable e) {
		return ApiResponse.create(null, statusCode, e.getMessage());
	}

	public static <T> ApiResponse<T> error(Throwable e) {
		return ApiResponse.create(null, 500, e.getMessage());
	}
}
