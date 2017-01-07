package lab4.rules;

import lab4.rules.deklaracijedefinicije.*;
import lab4.rules.izrazi.*;
import lab4.rules.naredbe.*;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * @author JJ
 */
public enum Rules {
    // 4.4.4 izrazi
    PRIMARNI_IZRAZ(new PrimarniIzraz()),
    POSTFIKS_IZRAZ(new PostfiksIzraz()),
    LISTA_ARGUMENATA(new ListaArgumenata()),
    UNARNI_IZRAZ(new UnarniIzraz()),
    UNARNI_OPERATOR(new UnarniOperator()),
    CAST_IZRAZ(new CastIzraz()),
    IME_TIPA(new ImeTipa()),
    SPECIFIKATOR_TIPA(new SpecifikatorTipa()),
    MULTIPLIKATIVNI_IZRAZ(new MultiplikativniIzraz()),
    ADITIVNI_IZRAZ(new AditivniIzraz()),
    ODNOSNI_IZRAZ(new OdnosniIzraz()),
    JEDNAKOSNI_IZRAZ(new JednakosniIzraz()),
    BIN_I_IZRAZ(new BinIIzraz()),
    BIN_XILI_IZRAZ(new BinXiliIzraz()),
    BIN_ILI_IZRAZ(new BinIliIzraz()),
    LOG_I_IZRAZ(new LogIIzraz()),
    LOG_ILI_IZRAZ(new LogIliIzraz()),
    IZRAZ_PRIDRUZIVANJA(new IzrazPridruzivanja()),
    IZRAZ(new Izraz()),

    // 4.4.5 naredbena struktura programa
    SLOZENA_NAREDBA(new SlozenaNaredba()),
    LISTA_NAREDBI(new ListaNaredbi()),
    NAREDBA(new Naredba()),
//    IZRAZ_NAREDBA(new IzrazNaredba()),
//    NAREDBA_GRANANJA(new NaredbaGrananja()),
//    NAREDBA_PETLJE(new NaredbaPetlje()),
    NAREDBA_SKOKA(new NaredbaSkoka()),
    PRIJEVODNA_JEDINICA(new PrijevodnaJedinica()),
    VANJSKA_DEKLARACIJA(new VanjskaDeklaracija()),

    // 4.4.6 deklaracije i definicije
    DEFINICIJA_FUNKCIJA(new DefinicijaFunkcije()),
    LISTA_PARAMETARA(new ListaParametara()),
    DEKLARACIJA_PARAMETRA(new DeklaracijaParametra()),
    LISTA_DEKLARACIJA(new ListaDeklaracija()),
    DEKLARACIJA(new Deklaracija()),
    LISTA_INIT_DEKLARATORA(new ListaInitDeklaratora()),
    INIT_DEKLARATOR(new InitDeklarator()),
    IZRAVNI_DEKLARATOR(new IzravniDeklarator()),
    INICIJALIZATOR(new Inicijalizator()),
    LISTA_IZRAZA_PRIDRUZIVANJA(new ListaIzrazaPridruzivanja()),
    ;

    private Rule rule;
    public final String symbol;

    Rules(Rule rule) {
        this.rule = rule;
        this.symbol = rule.symbol;

        if (RulesMap.map.containsKey(symbol)) {
            throw new RuntimeException("Double symbol : " + symbol + " => " + rule.getClass());
        }
        RulesMap.map.put(symbol, this);
    }

    public Rule getRule() {
        return rule;
    }

    public static Rule getRule(String symbol) {
        if (!RulesMap.map.containsKey(symbol)) {
            throw new NoSuchElementException("No rule for " + symbol);
        }

        return RulesMap.map.get(symbol).rule;
    }

    private static final class RulesMap {
        private static final Map<String, Rules> map = new HashMap<>();
    }
}
