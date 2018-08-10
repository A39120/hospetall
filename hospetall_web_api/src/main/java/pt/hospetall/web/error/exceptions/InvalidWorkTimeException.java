package pt.hospetall.web.error.exceptions;

public class InvalidWorkTimeException extends RuntimeException{
	public InvalidWorkTimeException() {
		super();
	}

	public InvalidWorkTimeException(String message) {
		super(message);
	}

	public InvalidWorkTimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidWorkTimeException(Throwable cause) {
		super(cause);
	}

	protected InvalidWorkTimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
