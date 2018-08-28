package pt.hospetall.web.error.exceptions;

public class InvalidPeriodException extends RuntimeException{
	public InvalidPeriodException() {
		super();
	}

	public InvalidPeriodException(String message) {
		super(message);
	}

	public InvalidPeriodException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidPeriodException(Throwable cause) {
		super(cause);
	}

	protected InvalidPeriodException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
