package lab1;

import lab1.input.LangRuleReader;
import lab1.storage.RegexStorage;
import lab1.storage.RulesStorage;
import lab1.storage.StateStorage;
import lab1.storage.TokenStorage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class GLA {

	private static final String OUT_PATH = "C:\\Users\\CHOPPER\\Desktop\\PPJ\\storage.bin";

	/**
	 * Program main entry point.
	 *
	 * @param args Command line arguments
	 */
	public static void main(String args[]) {
		RegexStorage regexStorage = new RegexStorage();
		StateStorage stateStorage = new StateStorage();
		TokenStorage tokenStorage = new TokenStorage();
		RulesStorage rulesStorage = new RulesStorage();
		LangRuleReader.read(regexStorage, stateStorage, tokenStorage, rulesStorage);
		//save rules to OUT_PATH
		//TODO provjeriti u pdf-u gdje spremiti fajl!
		try {
			File file = new File(OUT_PATH);
			file.createNewFile();
			FileOutputStream fileOut = new FileOutputStream(file);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(rulesStorage);
			out.writeObject(tokenStorage);
			out.writeObject(stateStorage);
			out.writeObject(regexStorage);
			out.close();
			fileOut.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

}
