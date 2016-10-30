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
    private int i = 0;
    int k = 0;
    int brojac = 0;
    private boolean jeliUniZnak = false;

    private String state;
    private int lineNumber = 1;
    private LinkedHashMap<Automat, List<Action>> stateRules = new LinkedHashMap<Automat, List<Action>>();
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
    private List<Integer> lineNumbers = new ArrayList<Integer>();

    private StringBuilder currentWord;
    private  char[] znakovi = new char[1000];


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
        state = states.getStorage().get(0);
        char[] init = new char[0];
        Automat prviAutomat = new Automat(init);


        stateRules = rules.getRulesForState(state);
        LinkedHashMap<Automat, List<Action>> tempStateRules = new LinkedHashMap<>();

        boolean prvi = true;
        boolean jesiDosaDoKraja = false;
        boolean jesiDosaSkrozDoKraja = false;
        boolean jesiNasaoIsta = false;
        boolean provjerio = false;


        currentWord.append(charArr[i]);


        while (!jesiDosaSkrozDoKraja) {

            //System.out.println(i);
            prvi = true;
            if (i == charArr.length - 1) {
                provjerio = true;
            }
            if (currentWord.length() == 15) {
                provjerio = true;
            }
            if (brojac > 30 || i==charArr.length-1 && !jesiNasaoIsta) {
                k++;
                i = k;
                currentWord.delete(0, currentWord.length());
                if(!(i==charArr.length)) {
                    currentWord.append(charArr[i]);
                } else{
                    jesiDosaSkrozDoKraja = true;
                    printOutput();
                    continue;
                }
                brojac = 0;
                jesiNasaoIsta = false;
                provjerio = false;


            }
            for (Automat automat : stateRules.keySet()) {
                if (automat.Execute(currentWord.toString().toCharArray())) {

                    jesiNasaoIsta = true;
                    brojac = 0;

                    //jesiDosaDoKraja = true;
                    tempStateRules.put(automat, stateRules.get(automat));
                    if (prvi) {
                        //System.out.println(automat.pocetnoStanje);
                        prviAutomat = automat;
                        prvi = false;

                        if((i==charArr.length-1)) {
                            jesiDosaDoKraja=true;
                            provjerio=true;
                        }
                    }
                }
            }
            //System.out.println(currentWord);
            if (!jesiNasaoIsta && currentWord.length()!=0) {
                i++;
                currentWord.append(charArr[i]);
                brojac++;
                continue;
            }
            if (prvi && provjerio && currentWord.length()!=0) {
                jesiDosaDoKraja = true;
                i--;
                //System.out.println(state );
                //System.out.println(currentWord);
                currentWord.delete(currentWord.length() - 1, currentWord.length());
                //System.out.println(currentWord);

                continue;
            }
            if (jesiDosaDoKraja && currentWord.length()!=0) {
                //izbaci npotrebne automate
                //stateRules.clear();
                //stateRules.putAll(tempStateRules);
                //tempStateRules.clear();
                // System.out.println(state );
                // System.out.println(currentWord);
                List<Action> actionList = stateRules.get(prviAutomat);
                actionList.forEach(action -> action.doAction(this));
                if (jeliUniZnak) {
                    sourceText.add(currentWord.toString());
                    lineNumbers.add(lineNumber);
                }
                jeliUniZnak = false;
                //System.out.println(state );
                /*for (Prijelaz mapa : prviAutomat.mapaPrijelaza.keySet()) {
                    System.out.println(mapa.pocetnoStanje + " ---*" + mapa.znakPrijelaza + "*--- " + prviAutomat.mapaPrijelaza.get(mapa));

                }*/
                if (this.lineNumber==12 && i==199) {
                    i=i;
                }
                if (!(i == charArr.length - 1)) {
                    currentWord.delete(0, currentWord.length());
                    i++;
                    k = i;
                    currentWord.append(charArr[i]);

                    jesiNasaoIsta = false;
                    jesiDosaDoKraja = false;
                    provjerio = false;
                }
            } else if(currentWord.length()!=0){
                i++;
                currentWord.append(charArr[i]);
                continue;
            }
            if (i == charArr.length - 1 && provjerio) {
                jesiDosaSkrozDoKraja = true;
                printOutput();

            }
            //
            // printOutput();
        }

    }

    public void saveLine(String line) {
        //a single entry is stored in all lists at the same index...
        //when reading, iterate through all the lists at the same time
        //System.out.println(line);

        uniformChars.add(line);
        jeliUniZnak = true;

    }

    public void increaseLineNumber() {
        this.lineNumber++;
    }

    public void setState(String state) {
        //System.out.println("+++"+this.state);
        this.state = state;
        stateRules = rules.getRulesForState(state);

        //System.out.println("+++"+this.state);
    }

    public void vratiSe(String a) {
        int brojac = Integer.parseInt(a);
        int t = currentWord.length() - brojac;
        for (; t > 0; t--) {
            i--;
            currentWord.delete(currentWord.length() - 1, currentWord.length());

        }
    }

    public void printOutput() {
        //TODO how should output be printed? which format?

        int size = uniformChars.size();
        for (int i = 0; i < size; i++) {
            System.out.print(uniformChars.get(i));
            System.out.print(" ");
            System.out.print(lineNumbers.get(i));
            System.out.print(" ");
            System.out.println(sourceText.get(i));

            // ... you get the idea
        }
    }
}
