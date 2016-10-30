package lab1.models;

import lab1.lexer.Lexer;

import java.util.function.BiConsumer;

public enum ActionSpecialArgument {
	MINUS("-", (l, a) -> {
		//TODO ignore
	}),
	NOVI_REDAK("NOVI_REDAK", (l, a) -> l.increaseLineNumber()),
	UDJI_U_STANJE("UDJI_U_STANJE", Lexer::setState),
	VRATI_SE("VRATI_SE", Lexer::vratiSe);

	private String identifier;
	private BiConsumer<Lexer, String> action;

	ActionSpecialArgument(String identifier, BiConsumer<Lexer, String> action) {
		this.identifier = identifier;
		this.action = action;
	}

	public static ActionSpecialArgument getByIdentifier(String identifier) {
		for (ActionSpecialArgument arg : ActionSpecialArgument.values()) {
			if (arg.identifier.equals(identifier)) {
				return arg;
			}
		}
		return null;
	}

	public void doAction(Lexer lexer, String argument) {
		action.accept(lexer, argument);
	}
}
