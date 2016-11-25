package lab2.transform;

import lab2.automaton.EpsilonNKA;
import lab2.models.DoubleMap;
import lab2.models.Production;
import lab2.models.StateSet;

import java.util.*;

/**
 * Created by pero5ar on 25.11.2016..
 */
public class AutomataGenerator {

    private static final String OZNAKA_TOCKE = Production.OZNAKA_TOCKE;

    public static ArrayList<Production> generirajStavke(List<Production> productions){

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
                newCodomain.add(OZNAKA_TOCKE);
                list.add(new Production(domain, newCodomain.toArray(new String[0])));
            }
            //ako nije epsilon produkcija
            else{
                codomain.addAll(production.getCodomainAsList());
                //dodaj tocku na svako moguce mjesto
                for(i=0;i<=codomain.size();i++){
                    newCodomain.clear();
                    newCodomain.addAll(codomain);
                    newCodomain.add(i, OZNAKA_TOCKE);
                    list.add(new Production(domain, newCodomain.toArray(new String[0])));
                }
            }
        }
        return list;
    }

    public static EpsilonNKA generirajENKA (ArrayList<Production> produkcije, Set<String> terminalSymbols, Set<String> emptyNonterminalSymbols, Map<String, Set<String>> startsWithTerminalSymbols, String m_pocetnoStanje){
        //enka
        StateSet enkaPocetnoStanje = new StateSet();
        StateSet enkaSkupStanja = new StateSet();
        DoubleMap<StateSet, String, StateSet> enkaPrijelazi = new DoubleMap<>();

        String novoPocetnoStanje = "<NovoPocetnoStanje>";
        String pocetnoStanje=m_pocetnoStanje;
        boolean imaJos = true;

        String[] stavka1 = new String[2];
        String[] stavka2 = new String[2];

        stavka1[0]=OZNAKA_TOCKE;
        stavka1[1]=pocetnoStanje;
        stavka2[1]=OZNAKA_TOCKE;
        stavka2[0]=pocetnoStanje;

        Production pocetnaProdukcija =new Production(novoPocetnoStanje, stavka1);
        Production tempProdukcija = new Production();
        Set<Production> stareProdukcije = new HashSet<>();
        Set<Production> noveProdukcije = new HashSet<>();
        Set<Production> dodaneProdukcije= new HashSet<>();
        List<Production> tempProdukcije = new ArrayList<>();
        DoubleMap<StateSet, String, StateSet> prijelazi;
        produkcije.add(pocetnaProdukcija);
        produkcije.add(new Production(novoPocetnoStanje, stavka2));
        Set<String> skupZavrsnih = new HashSet<>();
        skupZavrsnih.add("<OznakaKrajaNiza>");
        pocetnaProdukcija.setSkupZavrsnih(skupZavrsnih);
        //dodja poc produkciju kao pco stanje enka;
        enkaPocetnoStanje.add(pocetnaProdukcija.transformToString());
        skupZavrsnih.clear();
        stareProdukcije.add(pocetnaProdukcija);
        dodaneProdukcije.add(pocetnaProdukcija);

        while(imaJos){
            imaJos=false;
            for(Production produkcija : stareProdukcije){
                //  dodaj produkciju u skup stanja enka
                enkaSkupStanja.add((produkcija.transformToString()));
                if(!produkcija.getCodomainAsList().get(produkcija.getCodomainAsList().size()-1).equals(OZNAKA_TOCKE)){ //tocka nije na kraju
                    tempProdukcija= Production.nadjiSljedecu(produkcija);
                    // set skupzavrnsih nove pr na skupZavrsnih stare pr
                    tempProdukcija.setSkupZavrsnih(produkcija.getSkupZavrsnih());
                    String prijazniZnak = Production.getPrijlazniZnak(produkcija);
                    // dodaj obicni prijelaz
                    //dodajprijelaz(prod, prijazni, tempProdukcija);
                    StateSet key1 = new StateSet();
                    StateSet value = new StateSet();
                    key1.add(produkcija.transformToString());
                    value.add(tempProdukcija.transformToString());
                    enkaPrijelazi.put(key1,prijazniZnak,value);
                    key1.clear();
                    value.clear();
                    //dodaj novu produkciju u set;
                    noveProdukcije.add(tempProdukcija);
                    //prijazniZnak=null;
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
                    tempProdukcije=Production.getNoveProdukcije(produkcije, produkcija);
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
                        key1.add(produkcija.transformToString());
                        value.add(tempProdukcija.transformToString());
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
        Set<StateSet> enkaSkupStanjaSet = new HashSet<>();
        enkaSkupStanja.forEach(s -> {
            StateSet tempSet = new StateSet();
            tempSet.add(s);
            enkaSkupStanjaSet.add(tempSet);
        });

        return new EpsilonNKA(enkaPocetnoStanje,enkaSkupStanjaSet,enkaPrijelazi);
    }
}
