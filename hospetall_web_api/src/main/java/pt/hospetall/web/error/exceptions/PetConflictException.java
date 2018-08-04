package pt.hospetall.web.error.exceptions;

public class PetConflictException extends RuntimeException{

	public PetConflictException() {
		super();
	}

	public PetConflictException(String message) {
		super(message);
	}

	public PetConflictException(String message, Throwable cause) {
		super(message, cause);
	}

	public PetConflictException(Throwable cause) {
		super(cause);
	}

	protected PetConflictException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
