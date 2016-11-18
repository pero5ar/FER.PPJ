package lab2.transform;

import java.util.ArrayList;

/**
 * Created by CHOPPER on 11/18/2016.
 */
public class Prijelaz {

    private String nezavrsniZnak;
    private String prijelazniZnak;
    private ArrayList<String>  skupZnakova;


    public Prijelaz(){}
    public Prijelaz(String nezavrsniZnak, String prijelazniZnak, ArrayList<String> skupZnakova) {
        this.nezavrsniZnak = nezavrsniZnak;
        this.prijelazniZnak = prijelazniZnak;
        this.skupZnakova = skupZnakova;
    }

    public String getNezavrsniZnak() {
        return nezavrsniZnak;
    }

    public void setNezavrsniZnak(String nezavrsniZnak) {
        this.nezavrsniZnak = nezavrsniZnak;
    }

    public String getPrijelazniZnak() {
        return prijelazniZnak;
    }

    public void setPrijelazniZnak(String prijelazniZnak) {
        this.prijelazniZnak = prijelazniZnak;
    }

    public ArrayList<String> getSkupZnakova() {
        return skupZnakova;
    }

    public void setSkupZnakova(ArrayList<String> skupZnakova) {
        this.skupZnakova = skupZnakova;
    }
}
