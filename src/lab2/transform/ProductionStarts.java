package lab2.transform;

import lab2.automaton.EpsilonNKA;
import lab2.automaton.Prijelaz;
import lab2.models.Production;
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

    private static EpsilonNKA enka = new EpsilonNKA();

    public ProductionStarts(ProductionRulesStorage productionRulesStorage, TerminalSymbolsStorage terminalSymbolsStorage) {
        productionRules = productionRulesStorage.getStorage();
        modeledProductions = productionRulesStorage.getModeledStorage();
        terminalSymbols = terminalSymbolsStorage.getTypedStorage();

        emptyNonterminalSymbols = generateEmptyNonterminalSymbolsSet(productionRules);
        startsDirectlyWithSymbols = generateStartsDirectlyWithSymbolsMap(productionRules, emptyNonterminalSymbols);
        startsWithSymbols = generateStartsWithSymbolsMap(startsDirectlyWithSymbols);
        startsWithTerminalSymbols = generateStartsWithTerminalSymbolsMap(terminalSymbols, startsWithSymbols);

        productionStartSymbols = generateProductionStartSymbolsMap(modeledProductions, terminalSymbols, emptyNonterminalSymbols, startsWithTerminalSymbols);
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

    private static Map<Production, Set<String>> generateProductionStartSymbolsMap(List<Production> productions, Set<String> terminalSymbols,Set<String> emptyNonterminalSymbols, Map<String, Set<String>> startsWithTerminalSymbols) {
        Map<Production, Set<String>> productionStartSymbols = new HashMap<>();
        for (Production production : productions) {
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
            productionStartSymbols.put(production, startSymbols);
        }
        return productionStartSymbols;
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
    private static EpsilonNKA generirajENKA (ArrayList<Production> stavke, Set<String> terminalSymbols,Set<String> emptyNonterminalSymbols, Map<String, Set<String>> startsWithTerminalSymbols){
        Set<Prijelaz> prijelazi;
        String novoPocetnoStanje = "<NovoPocetnoStanje>";
        String pocetnoStanje=null;
        //String pocetnoStanje=  nonterminal.getFirst() //kad jure napravi ubaciti pravilan poziv za ovo;

        String[] stavka1 = new String[2];
        String[] stavka2 = new String[2];

        stavka1[0]="<OznakaTocke>";
        stavka1[1]=pocetnoStanje;
        stavka2[1]="<OznakaTocke>";
        stavka2[0]=pocetnoStanje;

        Production pocetnaProdukcija =new Production(novoPocetnoStanje, stavka1);

        stavke.add(pocetnaProdukcija);
        stavke.add(new Production(novoPocetnoStanje, stavka2));

        //Set<Production> trenutni = null;
        LinkedHashMap<Production, ArrayList<String>> trenutni = new LinkedHashMap<>();
        ArrayList<String> zagrade = new ArrayList<>();
        zagrade.add("<prazno>");
        trenutni.put(pocetnaProdukcija, zagrade);
        boolean gotov= false;
        while(!gotov) {
            for(Production prod2: trenutni.keySet()) {
                for (Production prod : stavke) {
                    List<String> desnaStrana = pocetnaProdukcija.getCodomainAsList();
                    String znakPoslijeTocke = null;
                    for (String temp : desnaStrana) {
                        if (temp.equals("<OznakaTocke>")) {
                            znakPoslijeTocke = desnaStrana.get(desnaStrana.indexOf(temp));
                        }
                    }
                    if (prod.getDomain().equals(znakPoslijeTocke)) {
                        if(trenutni.get(prod2).get(0).equals("<OznakaTocke>"))
                            trenutni.put(prod,zagrade);
                    }
                }
            }
        }








    }

    //ovdje idu Nickyjeve 2 metode


//    private static ArrayList<Prijelaz> nkaToDka(ArrayList<Prijelaz> nkaPrijelazi){
//        LinkedHashMap<String, ArrayList<String>> znakStanje = new LinkedHashMap<>();
//        LinkedHashMap<String, LinkedHashMap<String, String>> prijelazi = new LinkedHashMap<>();
//        LinkedHashMap<String, LinkedHashMap<String, String>> dka = new LinkedHashMap<>();
//        String trenutniZnak;
//        boolean promijeniZnak;
//        for(String stanje: enka.getSkupStanja()){
//            promijeniZnak=true;
//            while(promijeniZnak) {
//                promijeniZnak=false;
//                for (Prijelaz prijelaz : nkaPrijelazi) {
//                    if (prijelaz.getTrenutnoStanje().equals(stanje)) {
//                        znakStanje.put(prijelaz.getPrijelazniZnak(), prijelaz.getSljedeceStanje());
//                    }
//                }
//            }
//            prijelazi.put(stanje, znakStanje);
//            znakStanje.clear();
//        }
//
//
//    }

}
