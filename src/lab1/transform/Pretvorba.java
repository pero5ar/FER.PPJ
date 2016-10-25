package lab1.transform;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by CHOPPER on 10/25/2016.
 */
public class Pretvorba {


    private static int NovoStanje(Automat automat) {
        automat.brStanja = automat.brStanja + 1;
        return automat.brStanja - 1;
    }

    private static boolean JeOperator(char[] izraz, int i) {
        int br = 0;
        while (i - 1 >= 0 && izraz[i - 1] == '\\') { // ovo je jedan \, kao u Cu
            br = br + 1;
            i = i - 1;
        }
        return br % 2 == 0;
    }

    public static List<Integer> Pretvori(char[] izraz, Automat automat) {
        List<String> izbori = new ArrayList<String>();
        int brZagrada = 0;
        String spoji = "";
        boolean baremJedan = false;
        int l=0;
        for (int i = 0; i < izraz.length; i++) {
            if (izraz[i] == '(' && JeOperator(izraz, i)) {
                brZagrada = brZagrada + 1;
            } else if (izraz[i] == ')' && JeOperator(izraz, i)) {
                brZagrada = brZagrada - 1;
            } else if (brZagrada == 0 && izraz[i] == '|' && JeOperator(izraz, i)) {
                baremJedan=true;
                for(;l<i;l++){
                    spoji += izraz[l];
                }
                l++;
                izbori.add(spoji);
                spoji="";
            }
        }
        if(baremJedan){
            for(;l<izraz.length;l++){
                spoji += izraz[l];
            }
            izbori.add(spoji);
        }

        int lijevoStanje = NovoStanje(automat);
        int desnoStanje = NovoStanje(automat);
        if (baremJedan) {//ako je pronađen barem jedan operator izbora
            for (int j = 0; j < izbori.size(); j++) {
                List<Integer> privremeno = Pretvori(izbori.get(j).toCharArray(), automat);
                DodajEpsilonPrijelaz(automat, lijevoStanje, privremeno.get(0));
                DodajEpsilonPrijelaz(automat, privremeno.get(1), desnoStanje);
            }
        } else {
            boolean prefiksirano = false;
            Character prijelazniZnak;
            int zadnjeStanje = lijevoStanje;
            for (int k = 0; k < izraz.length; k++) {
                int a, b;
                if (prefiksirano) {
                    // slučaj 1
                    prefiksirano = false;

                    if (izraz[k] == 't') {
                        prijelazniZnak = '\t';
                    } else if (izraz[k] == 'n') {
                        prijelazniZnak = '\n';
                    } else if (izraz[k] == '_') {
                        prijelazniZnak = ' ';
                    } else {
                        prijelazniZnak = izraz[k];
                    }

                    a = NovoStanje(automat);
                    b = NovoStanje(automat);
                    DodajPrijelaz(automat, a, b, prijelazniZnak);
                } else {
                    // slučaj 2
                    if (izraz[k] == '\\') {
                        prefiksirano = true;
                        continue; // continue u Cu
                    }
                    if (izraz[k] != '(') {
                        // slučaj 2a
                        a = NovoStanje(automat);
                        b = NovoStanje(automat);
                        if (izraz[k] == '$') {
                            DodajEpsilonPrijelaz(automat, a, b);
                        } else {
                            DodajPrijelaz(automat, a, b, izraz[k]);
                        }
                    } else {
                        // slučaj 2b

                        int g; //= *pronađi odgovarajuću zatvorenu zagradu*
                        int brZagradaDva=1;
                        int n;
                        for (n = k+1; n < izraz.length; n++) {
                            if (izraz[n] == '(' && JeOperator(izraz, n)) {
                                brZagradaDva = brZagradaDva + 1;
                            } else if (izraz[n] == ')' && JeOperator(izraz, n)) {
                                brZagradaDva = brZagradaDva - 1;
                                if (brZagradaDva == 0) {
                                    break;
                                }
                            }
                        }
                        String temp="";
                        for(int m=k+1;m<n;m++){
                            temp += izraz[m];
                        }
                        List<Integer> privremeno = Pretvori(temp.toCharArray(), automat);
                        a = privremeno.get(0);
                        b = privremeno.get(1);
                        k = n;
                    }
                }
                // provjera ponavljanja
                if (k + 1 < izraz.length && izraz[k + 1] == '*') {
                    int x = a;
                    int y = b;
                    a = NovoStanje(automat);
                    b = NovoStanje(automat);
                    DodajEpsilonPrijelaz(automat, a, x);
                    DodajEpsilonPrijelaz(automat, y, b);
                    DodajEpsilonPrijelaz(automat, a, b);
                    DodajEpsilonPrijelaz(automat, y, x);
                    k = k + 1;
                }

                // povezivanje s ostatkom automata
                DodajEpsilonPrijelaz(automat, zadnjeStanje, a);
                zadnjeStanje = b;
            }
            DodajEpsilonPrijelaz(automat, zadnjeStanje, desnoStanje);
        }
        List<Integer> parStanja = new ArrayList<Integer>();
        parStanja.add(lijevoStanje);
        parStanja.add(desnoStanje);
        return parStanja;
    }

    private static void DodajEpsilonPrijelaz(Automat automat, int a, int b) {
        Prijelaz prijelaz = new Prijelaz(a,'$');
        automat.DodajPrijelaz(prijelaz, b);
    }

    private static void DodajPrijelaz(Automat automat, int a, int b, Character izraz) {
        Prijelaz prijelaz = new Prijelaz(a,izraz);
        automat.DodajPrijelaz(prijelaz, b);
    }
}