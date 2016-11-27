package lab2.models;

import java.io.Serializable;
import java.util.*;

/**
 * Created by pero5ar on 10.11.2016..
 */
public class Production implements Serializable {

    private static final long serialVersionUID = -7753802406325313450L;

    public static final String OZNAKA_TOCKE = "<OznakaTocke>";

    private String domain;
    private String[] codomain;
    private ArrayList<String> codomainAsList;
    private Set<String> skupZavrsnih;

    /*@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Production that = (Production) o;

        if (!getDomain().equals(that.getDomain())) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(getCodomain(), that.getCodomain());

    }

    @Override
    public int hashCode() {
        int result = getDomain().hashCode();
        result = 31 * result + Arrays.hashCode(getCodomain());
        return result;
    }*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Production that = (Production) o;

        if (getDomain() != null ? !getDomain().equals(that.getDomain()) : that.getDomain() != null) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(getCodomain(), that.getCodomain())) return false;
        return getSkupZavrsnih() != null ? getSkupZavrsnih().equals(that.getSkupZavrsnih()) : that.getSkupZavrsnih() == null;

    }

    @Override
    public int hashCode() {
        int result = getDomain() != null ? getDomain().hashCode() : 0;
        result = 31 * result + Arrays.hashCode(getCodomain());
        result = 31 * result + (getSkupZavrsnih() != null ? getSkupZavrsnih().hashCode() : 0);
        return result;
    }

    public Production(){
        codomainAsList = new ArrayList<>();
    }
    public Production(String domain, String[] codomain) {
        this.domain = domain;
        this.codomain = codomain;
        this.codomainAsList = new ArrayList<>(Arrays.asList(codomain));
        this.skupZavrsnih=new HashSet<>();
    }
    public Production(Production produkcija){
        this.domain=produkcija.getDomain();
        this.codomain=produkcija.getCodomain();
        this.codomainAsList = new ArrayList<>(Arrays.asList(codomain));
        this.skupZavrsnih=produkcija.getSkupZavrsnih();
    }

    public String getDomain() {
        return domain;
    }

    public String[] getCodomain() {
        return codomain;
    }

    public ArrayList<String> getCodomainAsList() {
        return codomainAsList;
    }

    public boolean inCodomain(String symobol) {
        return codomainAsList.contains(symobol);
    }

    public Set<String> getSkupZavrsnih() {
        return skupZavrsnih;
    }

    public void setSkupZavrsnih(Set<String> skupZavrsnih) {
        this.skupZavrsnih = skupZavrsnih;
    }

    public static Production nadjiSljedecu (Production produkcija){
        ArrayList<String> tempList = new ArrayList<>(produkcija.getCodomainAsList());
        int n=0;
        for(String s : tempList){
            if(s.equals(OZNAKA_TOCKE)){
                n = produkcija.getCodomainAsList().indexOf(s);
            }
        }
        tempList.remove(OZNAKA_TOCKE);
        tempList.add(n+1, OZNAKA_TOCKE);
        return new Production(produkcija.domain, tempList.toArray(new String[0]));
    }
    public static String getPrijlazniZnak (Production produkcija){
        ArrayList<String> tempList = new ArrayList<>(produkcija.getCodomainAsList());
        int n=0;
        for(String s : tempList){
            if(s.equals(OZNAKA_TOCKE)){
                n = produkcija.getCodomainAsList().indexOf(s);
            }
        }
        return tempList.get(n+1);
    }
    public static Production pripremiProdukciju (Production produkcija){
        ArrayList<String> tempList = new ArrayList<>(produkcija.getCodomainAsList());
        int n=0;
        for(String s : tempList){
            if(s.equals(OZNAKA_TOCKE)){
                n = produkcija.getCodomainAsList().indexOf(s);
            }
        }
        if(n==produkcija.getCodomainAsList().size()-2){
            return new Production();
        }
        tempList = new ArrayList<>(tempList.subList(n+2, tempList.size()));
        return new Production(produkcija.domain, tempList.toArray(new String[0]));
    }
    public static List<Production> getNoveProdukcije (ArrayList<Production> listaSvih, Production produkcija){
        ArrayList<Production> list = new ArrayList<>();
        ArrayList<String> tempList = new ArrayList<>(produkcija.getCodomainAsList());
        int n=0;
        for(String s : tempList){
            if(s.equals(OZNAKA_TOCKE)){
                n = produkcija.getCodomainAsList().indexOf(s);
            }
        }
        String domena = tempList.get(n+1);
        if(!domena.startsWith("<")){
            return  list;
        }
        else{
            for(Production pro : listaSvih){
                if(pro.getDomain().equals(domena) && pro.getCodomainAsList().get(0).equals(OZNAKA_TOCKE)){
                    list.add(new Production(pro));
                }
            }
        }

        return list;


    }

    public  String transformToString(){
        String codomainString = "";
        for(String cod: codomainAsList){
            codomainString = codomainString + cod + " ";
        }
        if (skupZavrsnih != null && !skupZavrsnih.isEmpty()) {
            codomainString += "{";
            codomainString += skupZavrsnih.toString();
            codomainString += "}";
        }
        return this.domain + "->" +codomainString;

    }

    public ArrayList<String> transformFromString(String prod){
        String temp = prod.split("->")[1];
        String[] prodTemp = temp.split(" ");
        //String[] codomainArray = new String[]{};
        ArrayList<String> codomainArray = new ArrayList<>();
        for(int i=0;i<prodTemp.length;i++){
            codomainArray.add(String.valueOf(prodTemp[i]));
        }
        return codomainArray;


    }
}
