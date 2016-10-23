package lab1.exceptions;

/**
 * Exception thrown by Lexer in case of a lexic error.
 */
public abstract class LexerException extends Exception {
	public LexerException() {
		super();
	}

	public LexerException(String message) {
		super(message);
	}
}
