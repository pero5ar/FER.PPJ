package lab2.transform;

import java.util.ArrayList;

/**
 * Created by CHOPPER on 11/18/2016.
 */
public class EpsilonNKA {

    private String pocetnoStanje;
    private ArrayList<String> skupStanja;
    private ArrayList<Prijelaz>  prijelazi;

    public EpsilonNKA(){}
    public EpsilonNKA(String pocetnoStanje, ArrayList<String> skupStanja, ArrayList<Prijelaz> prijelazi) {
        this.pocetnoStanje = pocetnoStanje;
        this.skupStanja = skupStanja;
        this.prijelazi = prijelazi;
    }

    public String getPocetnoStanje() {
        return pocetnoStanje;
    }

    public void setPocetnoStanje(String pocetnoStanje) {
        this.pocetnoStanje = pocetnoStanje;
    }

    public ArrayList<String> getSkupStanja() {
        return skupStanja;
    }

    public void setSkupStanja(ArrayList<String> skupStanja) {
        this.skupStanja = skupStanja;
    }

    public ArrayList<Prijelaz> getPrijelazi() {
        return prijelazi;
    }

    public void setPrijelazi(ArrayList<Prijelaz> prijelazi) {
        this.prijelazi = prijelazi;
    }
}
