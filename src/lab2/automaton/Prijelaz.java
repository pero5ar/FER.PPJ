package lab2.automaton;

/**
 * Created by CHOPPER on 11/18/2016.
 */
public class Prijelaz {
    private String trenutnoStanje;
    private String prijelazniZnak;
    private String sljedeceStanje;

    public Prijelaz(){}

    public Prijelaz(String nezavrsniZnak, String prijelazniZnak, String skupZnakova) {
        this.trenutnoStanje = nezavrsniZnak;
        this.prijelazniZnak = prijelazniZnak;
        this.sljedeceStanje = skupZnakova;
    }

    public String getTrenutnoStanje() {
        return trenutnoStanje;
    }

    public void setTrenutnoStanje(String nezavrsniZnak) {
        this.trenutnoStanje = nezavrsniZnak;
    }

    public String getPrijelazniZnak() {
        return prijelazniZnak;
    }

    public void setPrijelazniZnak(String prijelazniZnak) {
        this.prijelazniZnak = prijelazniZnak;
    }

    public String getSljedeceStanje() {
        return sljedeceStanje;
    }

    public void setSljedeceStanje(String skupZnakova) {
        this.sljedeceStanje = skupZnakova;
    }
}
