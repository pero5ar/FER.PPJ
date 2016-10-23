package lab1;

import lab1.exceptions.NoSuchRuleException;
import lab1.models.Action;
import lab1.models.RegEx;
import lab1.storage.RulesStorage;
import lab1.storage.StateStorage;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by juraj on 23.10.2016..
 */
public class Lexer {
	private StateStorage states;
	private RulesStorage rules;
	private String sourceCode;

	private String state;
	private int lineNumber = 1;

	// TODO maybe better mapping?
	// Still not sure how output should work...
	/**
	 * Uniformni znakovi
	 */
	private List<String> uniformChars;
	/**
	 * Izvorni tekstovi
	 */
	private List<String> sourceText;
	/**
	 * Brojevi linija
	 */
	private List<Integer> lineNumbers;

	private StringBuilder currentWord;

	private Lexer(StateStorage states, RulesStorage rules, String sourceCode){
		this.states = states;
		this.rules = rules;
		this.sourceCode = sourceCode;
		this.uniformChars = new ArrayList<>();
		this.sourceText = new ArrayList<>();
	}

	/**
	 * Method that lexes the given source code.
	 * Lexing is done character-by-character.
	 * @throws NoSuchRuleException
	 */
	private void lex() throws NoSuchRuleException {
		char[] charArr = sourceCode.toCharArray();

		currentWord = new StringBuilder();
		String state = states.getStorage().get(0);

		outerloop:
		for(char c : charArr){
			currentWord.append(c);

			List<Action> possibleRule = null;
			LinkedHashMap<RegEx, List<Action>> stateRules = rules.getRulesForState(state);

			for(Map.Entry<RegEx, List<Action>> rule : stateRules.entrySet()){
				if (rule.getKey().startsWith(currentWord.toString())){
					if (possibleRule != null){
						continue outerloop;
					}
					possibleRule = rule.getValue();
				}
			}

			if (possibleRule == null){
				throw new NoSuchRuleException();
			}

			possibleRule.forEach(action -> action.doAction(this));
		}
	}

	public void saveLine(String line) {
		//a single entry is stored in all lists at the same index...
		//when reading, iterate through all the lists at the same time
		uniformChars.add(line);
		sourceText.add(currentWord.toString());
		lineNumbers.add(lineNumber);
	}

	public void increaseLineNumber() {
		lineNumber++;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void vratiSe(String a) {
		//TODO
	}

	public void printOutput(){
		//TODO how should output be printed? which format?

		int size = uniformChars.size();
		for(int i = 0; i < size; i++){
			System.out.print(uniformChars.get(i));
			System.out.print(" ");
			System.out.print(sourceText.get(i));
			System.out.print(" ");
			System.out.println(lineNumbers.get(i));
			// ... you get the idea
		}
	}
}
