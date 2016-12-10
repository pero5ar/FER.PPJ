import common.SerializableHelper;
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

	private static final String OUT_PATH = "analizator/storage.bin";

	/**
	 * Program main entry point.
	 *
	 * @param args Command line arguments
	 */
	public static void main(String args[]) {
		String outPath = OUT_PATH;
		if (args.length > 0){
			outPath = args[0];
		}

		RegexStorage regexStorage = new RegexStorage();
		StateStorage stateStorage = new StateStorage();
		TokenStorage tokenStorage = new TokenStorage();
		RulesStorage rulesStorage = new RulesStorage();
		LangRuleReader.read(regexStorage, stateStorage, tokenStorage, rulesStorage);
		//save rules to OUT_PATH
		//TODO provjeriti u pdf-u gdje spremiti fajl!
		SerializableHelper.createOutput(outPath, regexStorage, stateStorage, tokenStorage, rulesStorage);
	}

}
