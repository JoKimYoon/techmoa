package team.jokimyoon.techmoa.global.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
	private final String message;
	private final HttpStatus httpStatus;

	public BusinessException() {
		super(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
		this.message = HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();
		this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
	}

	public BusinessException(String message) {
		super(message);
		this.message = message;
		this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
	}

	public BusinessException(HttpStatus httpStatus) {
		super(httpStatus.getReasonPhrase());
		this.message = httpStatus.getReasonPhrase();
		this.httpStatus = httpStatus;
	}

	public BusinessException(Throwable cause) {
		super(cause);
		this.message = HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();
		this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
	}

	public BusinessException(String message, HttpStatus httpStatus) {
		super(message);
		this.message = message;
		this.httpStatus = httpStatus;
	}

	public BusinessException(String message, Throwable cause) {
		super(message, cause);
		this.message = message;
		this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
	}

	public BusinessException(HttpStatus httpStatus, Throwable cause) {
		super(httpStatus.getReasonPhrase(), cause);
		this.message = httpStatus.getReasonPhrase();
		this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
	}

	public BusinessException(String message, HttpStatus httpStatus, Throwable cause) {
		super(message, cause);
		this.message = message;
		this.httpStatus = httpStatus;
	}
}