package lab1;


import lab1.storage.RegexStorage;
import lab1.storage.RulesStorage;
import lab1.storage.StateStorage;
import lab1.storage.TokenStorage;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class LA {

	private static final String IN_PATH = "storage.bin";

	/**
	 * Program main entry point.
	 *
	 * @param args Command line arguments
	 */
	public static void main(String args[]) {
		//reading from storage bin
		RulesStorage rulesStorage;
		TokenStorage tokenStorage;
		StateStorage stateStorage;
		RegexStorage regexStorage;
		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(IN_PATH))) {
			rulesStorage = (RulesStorage) in.readObject();
			tokenStorage = (TokenStorage) in.readObject();
			stateStorage = (StateStorage) in.readObject();
			regexStorage = (RegexStorage) in.readObject();
		}catch(IOException e) {
			e.printStackTrace();
			return;
		}catch(ClassNotFoundException e) {
			System.out.println("Storage class not found.");
			e.printStackTrace();
			return;
		}

		//TODO file (source) read form stdin
		String sourceCode = "...";

		Lexer lexer = new Lexer(stateStorage, rulesStorage, tokenStorage, regexStorage, sourceCode);
	}

}
