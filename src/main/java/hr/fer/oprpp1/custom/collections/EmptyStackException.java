package hr.fer.oprpp1.custom.collections;

/**
 * Exception when stack is empty and method pop is called
 * @author filip fabris
 * @version 1.0.0
 */
public class EmptyStackException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	/**
	 * EmptyStackException constructor with message parameter
	 * @param message
	 */
	public EmptyStackException(String message) {
		super( message );
	}

	/**
	 * default EmptyStackException constructor
	 */
	public EmptyStackException() {
		super();
	}
}
