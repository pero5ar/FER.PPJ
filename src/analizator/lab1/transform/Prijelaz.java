package lab1.transform;

import java.io.Serializable;

/**
 * Created by CHOPPER on 10/25/2016.
 */
public class Prijelaz implements Serializable{

    private static final long serialVersionUID = 3043420294362912402L;

    public int pocetnoStanje;
    public char znakPrijelaza;

    public Prijelaz(int pocetnoStanje, char znakPrijelaza){
        this.pocetnoStanje = pocetnoStanje;
        this.znakPrijelaza = znakPrijelaza;
    }

}
