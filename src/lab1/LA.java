package lab1;


import lab1.storage.RegexStorage;
import lab1.storage.RulesStorage;
import lab1.storage.StateStorage;
import lab1.storage.TokenStorage;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static jdk.nashorn.internal.runtime.regexp.joni.encoding.CharacterType.ASCII;

public class LA {

    private static final String IN_PATH = "C:\\Users\\CHOPPER\\Desktop\\PPJ\\storage.bin";

    /**
     * Program main entry point.
     *
     * @param args Command line arguments
     */
    public static void main(String args[])throws IOException {
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
        } catch (IOException e) {
            e.printStackTrace();
            return;
        } catch (ClassNotFoundException e) {
            System.out.println("Storage class not found.");
            e.printStackTrace();
            return;
        }
        String sourceCode = null;
        String lines;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            while((lines = reader.readLine())!=null ) {
               sourceCode += lines;
                sourceCode+= '\n';
            }
        }

        //TODO file (source) read form stdin
        //byte[] encoded = Files.readAllBytes(Paths.get("C:\\Users\\CHOPPER\\Desktop\\PPJ\\integration\\simplePpjLang.in"));

        //String sourceCode = new String(encoded, Charset.defaultCharset());
        Lexer lexer = new Lexer(stateStorage, rulesStorage, tokenStorage, regexStorage, sourceCode);
        lexer.lex();
    }

}
