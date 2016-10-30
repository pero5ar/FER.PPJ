package lab1.exceptions;

public class NoSuchRuleException extends LexerException {
	private static final long serialVersionUID = -9171156456857248779L;

	public NoSuchRuleException() {
		super();
	}

	public NoSuchRuleException(String message) {
		super(message);
	}
}
