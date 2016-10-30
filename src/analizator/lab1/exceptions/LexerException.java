package lab1.exceptions;

/**
 * Exception thrown by lab1.lexer.Lexer in case of a lexic error.
 */
public abstract class LexerException extends Exception {
	private static final long serialVersionUID = -7524368926789671007L;

	public LexerException() {
		super();
	}

	public LexerException(String message) {
		super(message);
	}
}
