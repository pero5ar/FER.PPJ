package lab1.transform;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by CHOPPER on 10/25/2016.
 */
public class Automat implements Serializable {

    private static final long serialVersionUID = 4727193730743552189L;

    public int pocetnoStanje=0;
    public int prihvatljivoStanje=1;
    public HashMap<Prijelaz, Integer> mapaPrijelaza = new HashMap<>();
    public int brStanja;
    //public int najduza;

    /*public Automat() {
    }*/

    public Automat(char[] rule) {


        Pretvorba.Pretvori(rule, this);
    }

    /*public Automat(Automat automat) {
        this.pocetnoStanje = automat.pocetnoStanje;
        this.prihvatljivoStanje = automat.prihvatljivoStanje;
        this.mapaPrijelaza = automat.mapaPrijelaza;
        this.brStanja = automat.brStanja;
    }*/

    public void DodajPrijelazAutomatu(Prijelaz prijelaz, Integer zavrsnoStanje) {
        mapaPrijelaza.put(prijelaz, zavrsnoStanje);
    }

    /**
     * TODO
     *
     * @param input
     * @return
     */
    public boolean Execute(char[] input) {

        List<Integer> epsilon = new ArrayList<>();
        List<Integer> tempEpsilon = new ArrayList<>();
        epsilon.add(pocetnoStanje);
        /*boolean prosiri = true;
        while (prosiri) {
            prosiri = false;
            for (Prijelaz prijelaz : mapaPrijelaza.keySet()) {
                if (prijelaz.pocetnoStanje == this.pocetnoStanje && prijelaz.znakPrijelaza == '$' && !epsilon.contains(mapaPrijelaza.get(prijelaz))) {
                    epsilon.add(mapaPrijelaza.get(prijelaz));
                    prosiri = true;
                }
            }
        }*/
        prosiri(epsilon);
        tempEpsilon.addAll(epsilon);
        epsilon.clear();

        for (int j = 0; j < input.length; j++) {
            tempEpsilon.addAll(epsilon);
            epsilon.clear();
            for (int i = 0; i < tempEpsilon.size(); i++) {

                for (Prijelaz prijelaz : mapaPrijelaza.keySet()) {
                    if (prijelaz.pocetnoStanje == tempEpsilon.get(i) && prijelaz.znakPrijelaza == input[j] && !epsilon.contains(mapaPrijelaza.get(prijelaz))) {
                        epsilon.add(mapaPrijelaza.get(prijelaz));
                    }
                }
            }
            prosiri(epsilon);
            tempEpsilon.clear();
            /* while (prosiri) {
                for (Integer stanje : epsilon) {

                    tempEpsilon.clear();
                    tempEpsilon.addAll(epsilon);
                    prosiri = false;
                    for (Prijelaz prijelaz : mapaPrijelaza.keySet()) {
                        if (prijelaz.pocetnoStanje == stanje && prijelaz.znakPrijelaza == '$' && !tempEpsilon.contains(mapaPrijelaza.get(prijelaz))) {
                            tempEpsilon.add(mapaPrijelaza.get(prijelaz));
                            prosiri = true;
                        }
                    }

                }
                epsilon.clear();
                epsilon.addAll(tempEpsilon);
            }*/


        }

        if (epsilon.contains(prihvatljivoStanje)) {
            return true;
        } else {
            return false;
        }

    }
    public List<Integer> prosiri(List<Integer> list){
        List<Integer> tempList=new ArrayList<Integer>();
        boolean prosiri = true;
        while(prosiri){
            tempList.clear();
            tempList.addAll(list);
            prosiri=false;
            for(Integer stanje: list){

                for (Prijelaz prijelaz : mapaPrijelaza.keySet()) {
                    if (prijelaz.pocetnoStanje == stanje && prijelaz.znakPrijelaza == '$' && !tempList.contains(mapaPrijelaza.get(prijelaz))) {
                        tempList.add(mapaPrijelaza.get(prijelaz));
                        prosiri = true;
                    }
                }

            }
            list.clear();
            list.addAll(tempList);
        }
        return list;



    }
}