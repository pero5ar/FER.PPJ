package lab1.models;

import lab1.LA;
import lab1.Lexer;

public class Action {
	private ActionSpecialArgument specialArgument = null;
	private String line;

	public static Action forLine(String line){
		String[] lineSplit = line.split(" ");

		Action action = new Action(line);
		ActionSpecialArgument specialArgument = ActionSpecialArgument.getByIdentifier(lineSplit[0]);
		if (specialArgument != null){
			action.specialArgument = specialArgument;
		}

		return action;
	}

	private Action(String line){
		this.line = line;
	}

	public String getLine(){
		return line;
	}

	public void doAction(Lexer lexer){
		if (specialArgument != null){
			int indexOfArgument = line.indexOf(' ');
			String argument = null;
			if (indexOfArgument > -1) {
				argument = line.substring(indexOfArgument);
			}
			specialArgument.doAction(lexer, argument);
		}else{
			lexer.saveLine(line);
		}
	}
}
