package lab2.transform;

import lab2.automaton.EpsilonNKA;
import lab2.models.DoubleMap;
import lab2.models.Production;
import lab2.models.StateSet;
import lab2.storage.ProductionRulesStorage;

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
        for(Production production : productions){
            domain=production.getDomain();
            //ako je epsilon produkcija
            if(production.getCodomainAsList().contains(ProductionRulesStorage.EPSILON)){
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
            codomain.clear();
        }
        return list;
    }

    public static EpsilonNKA generirajENKA (ArrayList<Production> produkcije, Set<String> terminalSymbols, Set<String> emptyNonterminalSymbols, Map<String, Set<String>> startsWithTerminalSymbols, String m_pocetnoStanje){
        //enka

        StateSet enkaPocetnoStanje = new StateSet();
        StateSet enkaSkupStanja = new StateSet();
        DoubleMap<StateSet, String, Set<StateSet>> enkaPrijelazi = new DoubleMap<>();

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
        //skupZavrsnih.clear();
        stareProdukcije.add(new Production(pocetnaProdukcija));
        dodaneProdukcije.add(new Production(pocetnaProdukcija));
        int br=0;
        int br2=0;

        while(imaJos){
            imaJos=false;
            for(Production produkcija : stareProdukcije){
                //  dodaj produkciju u skup stanja enka
                enkaSkupStanja.add((produkcija.transformToString()));
                if(!produkcija.getCodomainAsList().get(produkcija.getCodomainAsList().size()-1).equals(OZNAKA_TOCKE)){ //tocka nije na kraju
                    tempProdukcija= new Production(Production.nadjiSljedecu(produkcija));
                    // set skupzavrnsih nove pr na skupZavrsnih stare pr
                    tempProdukcija.setSkupZavrsnih(produkcija.getSkupZavrsnih());
                    String prijelazniZnak = Production.getPrijlazniZnak(produkcija);
                    // dodaj obicni prijelaz
                    //dodajprijelaz(prod, prijazni, tempProdukcija);
                    StateSet key1 = new StateSet();
                    StateSet value = new StateSet();
                    key1.add(produkcija.transformToString());
                    value.add(tempProdukcija.transformToString());
                    if(enkaPrijelazi.jeliVecPostoji(key1, prijelazniZnak)){
                        Set<StateSet> tempSet = enkaPrijelazi.get(key1, prijelazniZnak);
                        tempSet.add(value);
                    }
                    else{
                        Set<StateSet> tempSet = new HashSet<>();
                        tempSet.add(value);
                        enkaPrijelazi.put(key1,prijelazniZnak,tempSet);
                    }


                    key1 = new StateSet();
                    value = new StateSet();
                    //dodaj novu produkciju u set;
                    //System.out.println(tempProdukcija.transformToString());
                    noveProdukcije.add(new Production(tempProdukcija));
                    imaJos=true;
                    //epislon prijelaz {}

                    Set<String> noviSkup = new HashSet<>();

                    if(Production.pripremiProdukciju(produkcija).getCodomainAsList().size()!=0) {
                        tempProdukcija = new Production(Production.pripremiProdukciju(produkcija));
                        noviSkup = ProductionStarts.generateProductionStartSymbolsMap(tempProdukcija,terminalSymbols,emptyNonterminalSymbols, startsWithTerminalSymbols );

                    }
                    if(noviSkup.size()==0){
                        noviSkup.addAll(produkcija.getSkupZavrsnih());
                    }
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
                    br+=tempProdukcije.size();

                    for(Production pro : tempProdukcije){

                        boolean jelVecPostoji=false;
                        for(Production pro2 : dodaneProdukcije){
                            if(pro.equals(pro2)){
                                jelVecPostoji=true;
                                tempProdukcija=new Production(pro2);

                            }
                        }
                        if(!jelVecPostoji){
                            tempProdukcija=new Production(pro);
                            //System.out.println(noviSkup);
                            tempProdukcija.setSkupZavrsnih(noviSkup);
                            noveProdukcije.add(new Production(tempProdukcija));
                        }
                        //dodaj eps prijelaz
                        key1.add(produkcija.transformToString());
                        value.add(tempProdukcija.transformToString());
                        if(enkaPrijelazi.jeliVecPostoji(key1, prijelazniZnak)){
                            Set<StateSet> tempSet = enkaPrijelazi.get(key1, prijelazniZnak);
                            tempSet.add(value);
                        }
                        else{
                            Set<StateSet> tempSet = new HashSet<>();
                            tempSet.add(value);
                            enkaPrijelazi.put(key1,prijelazniZnak,tempSet);
                        }
                        key1 = new StateSet();
                        value = new StateSet();

                    }

                }
                else{
                    continue;
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
        //System.out.println(enkaPocetnoStanje);
        //enkaSkupStanja.forEach(e -> System.out.println(e + " , "));


        return new EpsilonNKA(enkaPocetnoStanje,enkaSkupStanjaSet,enkaPrijelazi);
    }
}

