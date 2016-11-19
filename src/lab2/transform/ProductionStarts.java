package lab2.transform;

import lab2.automaton.DKA;
import lab2.automaton.EpsilonNKA;
import lab2.automaton.Prijelaz;
import lab2.automaton.State;
import lab2.models.DoubleMap;
import lab2.models.Production;
import lab2.storage.NonterminalSymbolsStorage;
import lab2.storage.ProductionRulesStorage;
import lab2.storage.TerminalSymbolsStorage;

import java.io.Serializable;
import java.util.*;

/**
 * Created by pero5ar on 10.11.2016..
 */
public class ProductionStarts implements Serializable{

    private static final long serialVersionUID = 8028312764334164336L;

    private static final String EPSILON = ProductionRulesStorage.EPSILON;

    private Map<String, List<String[]>> productionRules;
    private List<Production> modeledProductions;
    private Set<String> terminalSymbols;

    private Set<String> emptyNonterminalSymbols;
    private Map<String, Set<String>> startsDirectlyWithSymbols;
    private Map<String, Set<String>> startsWithSymbols;
    private Map<String, Set<String>> startsWithTerminalSymbols;

    private Map<Production, Set<String>> productionStartSymbols;
    private String pocetnoStanje;

//    private static EpsilonNKA enka = new EpsilonNKA();

    public ProductionStarts(ProductionRulesStorage productionRulesStorage, TerminalSymbolsStorage terminalSymbolsStorage, NonterminalSymbolsStorage nonterminalSymbolsStorage) {
        productionRules = productionRulesStorage.getStorage();
        modeledProductions = productionRulesStorage.getModeledStorage();
        terminalSymbols = terminalSymbolsStorage.getTypedStorage();

        emptyNonterminalSymbols = generateEmptyNonterminalSymbolsSet(productionRules);
        startsDirectlyWithSymbols = generateStartsDirectlyWithSymbolsMap(productionRules, emptyNonterminalSymbols);
        startsWithSymbols = generateStartsWithSymbolsMap(startsDirectlyWithSymbols);
        startsWithTerminalSymbols = generateStartsWithTerminalSymbolsMap(terminalSymbols, startsWithSymbols);
        //productionStartSymbols = generateProductionStartSymbolsMap(modeledProductions, terminalSymbols, emptyNonterminalSymbols, startsWithTerminalSymbols);
        this.pocetnoStanje=nonterminalSymbolsStorage.getFirst();
    }

    private static Set<String> generateEmptyNonterminalSymbolsSet(Map<String, List<String[]>> productionRules) {
        Set<String> emptyNonterminalSymbols = new HashSet<>();
        emptyNonterminalSymbols.add(EPSILON);
        Set<String> emptyNonterminalSymbols_copy;
        do {
            emptyNonterminalSymbols_copy = new HashSet<>(emptyNonterminalSymbols);
            emptyNonterminalSymbols.addAll(newEmptySymbols(productionRules, emptyNonterminalSymbols));
        } while (emptyNonterminalSymbols.size() != emptyNonterminalSymbols_copy.size());
        return emptyNonterminalSymbols;
    }

    private static Set<String> newEmptySymbols(Map<String, List<String[]>> productionRules, Set<String> emptyNonterminalSymbols) {
        Set<String> newSymbols = new HashSet<>();
        for (Map.Entry<String, List<String[]>> entry : productionRules.entrySet()) {
            if (emptyNonterminalSymbols.contains(entry.getKey())) {
                continue;
            }
            for (String[] production : entry.getValue()) {
                if (emptyNonterminalSymbols.contains(production[0])) {
                    newSymbols.add(entry.getKey());
                    break;
                }
            }
        }
        return newSymbols;
    }

    private static Map<String, Set<String>> generateStartsDirectlyWithSymbolsMap(Map<String, List<String[]>> productionRules, Set<String> emptyNonterminalSymbols) {
        Map<String, Set<String>> startsDirectlyWithSymbols = new HashMap<>();
        for (Map.Entry<String, List<String[]>> entry : productionRules.entrySet()) {
            Set<String> keyStartsDirectyWith = new HashSet<>();
            entry.getValue().forEach( p -> keyStartsDirectyWith.addAll(productionStartsDirectlyWithSymbols(emptyNonterminalSymbols, p) ));
            startsDirectlyWithSymbols.put(entry.getKey(), keyStartsDirectyWith);
        }
        return startsDirectlyWithSymbols;
    }

    private static Set<String> productionStartsDirectlyWithSymbols(Set<String> emptyNonterminalSymbols, String[] production) {
        Set<String> directSymbols = new HashSet<>();
        for (String symbol : production) {
            if (symbol.equals(EPSILON)) {
                continue;
            }
            directSymbols.add(symbol);
            if (!emptyNonterminalSymbols.contains(symbol)) {
                break;
            }
        }
        return directSymbols;
    }


    /**
     *
     * @param startsDirectlyWithSymbols
     * @return
     * @author JJ
     */
    private static Map<String, Set<String>> generateStartsWithSymbolsMap(Map<String, Set<String>> startsDirectlyWithSymbols) {
        Map<String, Set<String>> startsWithSymbols  = new HashMap<>(startsDirectlyWithSymbols);

        while (true){
            boolean changed = false;

            Map<String, Set<String>> startsWithSymbols_copy = new HashMap<>();
            startsWithSymbols.entrySet().forEach(
                    e -> startsWithSymbols_copy.put(
                            e.getKey(),
                            new HashSet<>(e.getValue())
                    )
            );

            for(Map.Entry<String, Set<String>> entry : startsWithSymbols.entrySet()){
                for(String symbol : entry.getValue()) {
                    Set<String> referenced = startsWithSymbols.get(symbol);
                    if (referenced != null){
                        changed = changed || startsWithSymbols_copy.get(entry.getKey()).addAll(referenced);
                    }
                }
            }

            if (!changed){
                return startsWithSymbols;
            }
            startsWithSymbols = startsWithSymbols_copy;
        }
    }

    private static Map<String, Set<String>> generateStartsWithTerminalSymbolsMap(Set<String> terminalSymbols, Map<String, Set<String>> startsWithSymbols) {
        Map<String, Set<String>> startsWithTerminalSymbols = new HashMap<>(startsWithSymbols);

        startsWithTerminalSymbols.values().forEach(set -> set.removeIf(string -> !terminalSymbols.contains(string)));

        return startsWithTerminalSymbols;
    }

    private static Set<String> generateProductionStartSymbolsMap(Production production, Set<String> terminalSymbols,Set<String> emptyNonterminalSymbols, Map<String, Set<String>> startsWithTerminalSymbols) {

            Set<String> startSymbols = new HashSet<>();
            for (String symbol : production.getCodomainAsList()) {
                if (terminalSymbols.contains(symbol)) {
                    startSymbols.add(symbol);
                    break;
                }
                startSymbols.addAll(startsWithTerminalSymbols.get(symbol));
                if (!emptyNonterminalSymbols.contains(symbol)) {
                    break;
                }
            }
            return startSymbols;
     }

    private static ArrayList<Production>generirajStavke (ArrayList<Production> productions){

        ArrayList<Production> list = new ArrayList<>();
        ArrayList<String> codomain = new ArrayList<>();
        ArrayList<String> newCodomain = new ArrayList<>();
        String domain = null;
        int i;
        for(Production production: productions){
            domain=production.getDomain();
            //ako je epsilon produkcija
            if(production.getCodomainAsList().size()==1 &&production.getCodomainAsList().get(0).equals("&")){
                newCodomain.clear();
                newCodomain.add("<OznakaTocke>");
                list.add(new Production(domain, newCodomain.toArray(new String[0])));
            }
            //ako nije epsilon produkcija
            else{
                codomain.addAll(production.getCodomainAsList());
                //dodaj tocku na svako moguce mjesto
                for(i=0;i<=codomain.size();i++){
                    newCodomain.clear();
                    newCodomain.addAll(codomain);
                    newCodomain.add(i, "<OznakaTocke>");
                    list.add(new Production(domain, newCodomain.toArray(new String[0])));
                }
            }
        }
        return list;
    }
    private static EpsilonNKA generirajENKA (ArrayList<Production> stavke, Set<String> terminalSymbols,Set<String> emptyNonterminalSymbols, Map<String, Set<String>> startsWithTerminalSymbols, String m_pocetnoStanje){
        //enka
        Set<State> enkaPocetnoStanje = null;
        Set<State> enkaSkupStanja = null;
        DoubleMap<Set<State>, String, Set<State>> enkaPrijelazi = new DoubleMap<>();

        String novoPocetnoStanje = "<NovoPocetnoStanje>";
        String pocetnoStanje=m_pocetnoStanje;
        boolean imaJos = true;

        String[] stavka1 = new String[2];
        String[] stavka2 = new String[2];

        stavka1[0]="<OznakaTocke>";
        stavka1[1]=pocetnoStanje;
        stavka2[1]="<OznakaTocke>";
        stavka2[0]=pocetnoStanje;

        Production pocetnaProdukcija =new Production(novoPocetnoStanje, stavka1);
        Production tempProdukcija = null;
        Set<Production> stareProdukcije = null ;
        Set<Production> noveProdukcije = null;
        Set<Production> dodaneProdukcije=null;
        List<Production> tempProdukcije = new ArrayList<>();
        DoubleMap<Set<State>, String, Set<State>> prijelazi;
        stavke.add(pocetnaProdukcija);
        //dodja poc produkciju kao pco stanje enka;
        enkaPocetnoStanje.add(new State(pocetnaProdukcija.transformToString()));
        stavke.add(new Production(novoPocetnoStanje, stavka2));
        Set<String> skupZavrsnih =null;
        skupZavrsnih.add("<OznakaKrajaNiza>");
        pocetnaProdukcija.setSkupZavrsnih(skupZavrsnih);
        skupZavrsnih.clear();
        stareProdukcije.add(pocetnaProdukcija);
        dodaneProdukcije.add(pocetnaProdukcija);

        while(imaJos){
            imaJos=false;
            for(Production produkcija : stareProdukcije){
                //  dodaj produkciju u skup stanja enka
                enkaSkupStanja.add(new State(produkcija.transformToString()));
                if(!produkcija.getCodomainAsList().get(produkcija.getCodomainAsList().size()-1).equals("<OznakaTocke>")){ //tocka nije na kraju
                    tempProdukcija= Production.nadjiSljedecu(produkcija);
                    // set skupzavrnsih nove pr na skupZavrsnih stare pr
                    tempProdukcija.setSkupZavrsnih(produkcija.getSkupZavrsnih());
                    String prijazniZnak = Production.getPrijlazniZnak(produkcija);
                    // dodaj obicni prijelaz
                    //dodajprijelaz(prod, prijazni, tempProdukcija);
                    Set<State> key1 = null;
                    Set<State> value = null;
                    key1.add(new State(produkcija.transformToString()));
                    value.add(new State(tempProdukcija.transformToString()));
                    enkaPrijelazi.put(key1,prijazniZnak,value);
                    key1.clear();
                    value.clear();
                    //dodaj novu produkciju u set;
                    noveProdukcije.add(tempProdukcija);
                    prijazniZnak=null;
                    imaJos=true;
                    //epislon prijelaz {}
                    tempProdukcija = new Production(Production.pripremiProdukciju(produkcija));
                    Set<String> noviSkup = ProductionStarts.generateProductionStartSymbolsMap(tempProdukcija,terminalSymbols,emptyNonterminalSymbols, startsWithTerminalSymbols );
                    boolean jeliProdukcijaPrazna=true;
                    for(String s : tempProdukcija.getCodomainAsList()){
                        if(!emptyNonterminalSymbols.contains(s)){
                            jeliProdukcijaPrazna=false;
                            break;
                        }
                    }
                    if(jeliProdukcijaPrazna){
                        noviSkup.addAll(produkcija.getSkupZavrsnih());
                    }
                    tempProdukcije=Production.getNoveProdukcije(stavke, produkcija);
                    for(Production pro : tempProdukcije){
                        boolean jelVecPostoji=false;
                        for(Production pro2 : dodaneProdukcije){
                            if(pro.equals(pro2)){
                                jelVecPostoji=true;
                                tempProdukcija=new Production(pro2);
                                break;
                            }
                        }
                        if(!jelVecPostoji){
                            tempProdukcija=new Production(pro);
                            tempProdukcija.setSkupZavrsnih(noviSkup);
                            noveProdukcije.add(tempProdukcija);
                        }
                        //dodaj eps prijelaz
                        key1.add(new State(produkcija.transformToString()));
                        value.add(new State(tempProdukcija.transformToString()));
                        enkaPrijelazi.put(key1,"&",value);
                        key1.clear();
                        value.clear();
                    }
                }
            }
            stareProdukcije.clear();
            stareProdukcije.addAll(noveProdukcije);
            dodaneProdukcije.addAll(noveProdukcije);
            noveProdukcije.clear();
        }
        return new EpsilonNKA(enkaPocetnoStanje,enkaSkupStanja,enkaPrijelazi);
    }

    /*private static DKA generirajBrojeve (DKA dka){
        for(State s : dka.getSkupStanja()){

        }


    }*/
}
