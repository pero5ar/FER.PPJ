package lab1;

import lab1.input.LangRuleReader;
import lab1.storage.RegexStorage;
import lab1.storage.RulesStorage;
import lab1.storage.StateStorage;
import lab1.storage.TokenStorage;

public class GLA {

	/**
	 * Program main entry point.
	 * @param args Command line arguments
	 */
	public static void main(String args[]){
		RegexStorage regexStorage = new RegexStorage();
		StateStorage stateStorage = new StateStorage();
		TokenStorage tokenStorage = new TokenStorage();
		RulesStorage rulesStorage = new RulesStorage();

		LangRuleReader.read(regexStorage, stateStorage, tokenStorage, rulesStorage);

		System.out.println("gotov");
		System.out.println("gotov");

	}

}
