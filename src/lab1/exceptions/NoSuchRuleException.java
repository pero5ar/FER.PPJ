package lab1.exceptions;

public class NoSuchRuleException extends LexerException {
	public NoSuchRuleException(){
		super();
	}

	public NoSuchRuleException(String message){
		super(message);
	}
}
