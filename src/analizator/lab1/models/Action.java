package lab1.models;

import lab1.lexer.Lexer;

import java.io.Serializable;

public class Action implements Serializable {

	private static final long serialVersionUID = -122711346043843025L;

	private ActionSpecialArgument specialArgument = null;
	private String line;

	private Action(String line) {
		this.line = line;
	}

	public static Action forLine(String line) {
		String[] lineSplit = line.split(" ");

		Action action = new Action(line);
		ActionSpecialArgument specialArgument = ActionSpecialArgument.getByIdentifier(lineSplit[0]);
		if (specialArgument != null) {
			action.specialArgument = specialArgument;
		}

		return action;
	}

	public String getLine() {
		return line;
	}

	public void doAction(Lexer lexer) {
		//System.out.println(specialArgument);
		if (specialArgument != null) {
			int indexOfArgument = line.indexOf(' ');
			String argument = null;
			if (indexOfArgument > -1) {
				argument = line.substring(indexOfArgument+1);
			}
			specialArgument.doAction(lexer, argument);
		} else {
			lexer.saveLine(line);
		}
	}
}
