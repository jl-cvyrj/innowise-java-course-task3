package by.paulouskaya.task3.exception;

public class RiverFerryException extends Exception {
	public RiverFerryException() {
	}
	
	public RiverFerryException(String message) {
		super(message);
	}
	
	public RiverFerryException(Throwable cause) {
		super(cause);
	}
	
	public RiverFerryException(String message, Throwable cause) {
		super(message, cause);
	}
}
