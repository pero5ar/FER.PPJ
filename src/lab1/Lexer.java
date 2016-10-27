package lab1;

import lab1.exceptions.NoSuchRuleException;
import lab1.models.Action;
import lab1.storage.RegexStorage;
import lab1.storage.RulesStorage;
import lab1.storage.StateStorage;
import lab1.storage.TokenStorage;
import lab1.transform.Automat;
import lab1.transform.Prijelaz;

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
    private TokenStorage tokens;
    private RegexStorage regexes;
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


    public Lexer(StateStorage states, RulesStorage rules, TokenStorage tokens, RegexStorage regexes, String sourceCode) {
        this.states = states;
        this.rules = rules;
        this.tokens = tokens;
        this.regexes = regexes;
        this.sourceCode = sourceCode;

        this.uniformChars = new ArrayList<>();
        this.sourceText = new ArrayList<>();
    }

    /**
     * Method that lexes the given source code.
     * Lexing is done character-by-character.
     *
     * @throws NoSuchRuleException
     */
    public void lex() {

        char[] charArr = sourceCode.toCharArray();

        currentWord = new StringBuilder();
        String state = states.getStorage().get(0);
        Automat prviAutomat = new Automat("");


        LinkedHashMap<Automat, List<Action>> stateRules = rules.getRulesForState(state);
        LinkedHashMap<Automat, List<Action>> tempStateRules = new LinkedHashMap<>();

        boolean prvi = true;
        boolean jesiDosaDoKraja = false;
        boolean jesiDosaSkrozDoKraja = false;
        boolean jesiNasaoIsta = false;

        int i = 0;
        currentWord.append(charArr[i]);
        while (!jesiDosaSkrozDoKraja || !jesiNasaoIsta) {
            while (!jesiDosaDoKraja && !jesiDosaSkrozDoKraja) {
                prvi = true;
                for (Automat automat : stateRules.keySet()) {
                    if (automat.Execute(currentWord.toString().toCharArray())) {
                        jesiNasaoIsta=true;
                        tempStateRules.put(automat, stateRules.get(automat));
                        if (prvi) {
                            //System.out.println(automat.pocetnoStanje);

                            prviAutomat = automat;
                            prvi = false;
                        }
                    }
                }
                System.out.println(currentWord);
                if(!jesiNasaoIsta){
                    i++;
                    currentWord.append(charArr[i]);
                    continue;

                }

                if (tempStateRules.size() == 0) {
                    jesiDosaDoKraja = true;
                    i--;
                } else {
                    //izbaci npotrebne automate
                    stateRules.clear();
                    stateRules.putAll(tempStateRules);
                    tempStateRules.clear();
                }
                i++;
                if (i != charArr.length) {
                    currentWord.append(charArr[i]);
                } else {
                    jesiDosaSkrozDoKraja = true;
                }

            }
            List<Action> actionList = stateRules.get(prviAutomat);

            for(Prijelaz mapa: prviAutomat.mapaPrijelaza.keySet()){
                System.out.println(mapa.pocetnoStanje+" ---*"+ mapa.znakPrijelaza+"*--- "+ prviAutomat.mapaPrijelaza.get(mapa));

            }
            actionList.forEach(action -> action.doAction(this));
            System.out.println("B");
            printOutput();

        }
    }

    public void saveLine(String line) {
        //a single entry is stored in all lists at the same index...
        //when reading, iterate through all the lists at the same time
        System.out.println(line);

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

    public void printOutput() {
        //TODO how should output be printed? which format?

        int size = uniformChars.size();
        for (int i = 0; i < size; i++) {
            System.out.print(uniformChars.get(i));
            System.out.print(" ");
            System.out.print(sourceText.get(i));
            System.out.print(" ");
            System.out.println(lineNumbers.get(i));
            // ... you get the idea
        }
    }
}
