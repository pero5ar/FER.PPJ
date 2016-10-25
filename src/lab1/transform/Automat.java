package lab1.transform;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/**
 * Created by CHOPPER on 10/25/2016.
 */
public class Automat {

    public int pocetnoStanje;
    public int prihvatljivoStanje;
    public HashMap<Prijelaz, Integer> mapaPrijelaza;
    public int brStanja;


    public Automat() {

    }

    public void DodajPrijelaz(Prijelaz prijelaz, int zavrsnoStanje){
        mapaPrijelaza.put(prijelaz, zavrsnoStanje);
    }

    public boolean Execute(char[] input){
        return true;
    }
}