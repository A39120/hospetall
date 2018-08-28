package pt.hospetall.web.error.exceptions;

public class ScheduleConflictException extends RuntimeException{
	public ScheduleConflictException() {
		super();
	}

	public ScheduleConflictException(String message) {
		super(message);
	}

	public ScheduleConflictException(String message, Throwable cause) {
		super(message, cause);
	}

	public ScheduleConflictException(Throwable cause) {
		super(cause);
	}

	protected ScheduleConflictException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
