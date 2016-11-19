package lab2.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Created by pero5ar on 10.11.2016..
 */
public class Production implements Serializable {

    private static final long serialVersionUID = -7753802406325313450L;

    private String domain;
    private String[] codomain;
    private List<String> codomainAsList;
    private Set<String> skupZavrsnih;

    @Override
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
    }

    public Production(String domain, String[] codomain) {
        this.domain = domain;
        this.codomain = codomain;
        codomainAsList = Arrays.asList(codomain);
        skupZavrsnih=null;
    }
    public Production(Production produkcija){
        this.domain=produkcija.getDomain();
        this.codomain=produkcija.getCodomain();
        codomainAsList = Arrays.asList(codomain);
        skupZavrsnih=null;
    }

    public String getDomain() {
        return domain;
    }

    public String[] getCodomain() {
        return codomain;
    }

    public List<String> getCodomainAsList() {
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
        List<String> tempList = produkcija.getCodomainAsList();
        int n=0;
        for(String s : tempList){
            if(s.equals("<OznakaTocke>")){
                n = produkcija.getCodomainAsList().indexOf(s);
            }
        }
        tempList.remove(n);
        tempList.add(n+1, "<OznakaTocke>");
        return new Production(produkcija.domain, tempList.toArray(new String[0]));
    }
    public static String getPrijlazniZnak (Production produkcija){
        List<String> tempList = produkcija.getCodomainAsList();
        int n=0;
        for(String s : tempList){
            if(s.equals("<OznakaTocke>")){
                n = produkcija.getCodomainAsList().indexOf(s);
            }
        }
        return tempList.get(n+1);
    }
    public static Production pripremiProdukciju (Production produkcija){
        List<String> tempList = produkcija.getCodomainAsList();
        int n=0;
        for(String s : tempList){
            if(s.equals("<OznakaTocke>")){
                n = produkcija.getCodomainAsList().indexOf(s);
            }
        }
        tempList = tempList.subList(n+1, tempList.size());
        return new Production(produkcija.domain, tempList.toArray(new String[0]));
    }
    public static List<Production> getNoveProdukcije (ArrayList<Production> listaSvih, Production produkcija){
        ArrayList<Production> list = new ArrayList<>();
        List<String> tempList = produkcija.getCodomainAsList();
        int n=0;
        for(String s : tempList){
            if(s.equals("<OznakaTocke>")){
                n = produkcija.getCodomainAsList().indexOf(s);
            }
        }
        String domena = tempList.get(n+1);
        if(!domena.startsWith("<")){
            return  list;
        }
        else{
            for(Production pro : listaSvih){
                if(pro.getDomain().equals(domena) && pro.getCodomainAsList().get(0).equals("<OznakaTocke>")){
                    list.add(pro);
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
        codomainString += "{";
        codomainString += skupZavrsnih.toString();
        codomainString += "}";
        return this.domain + "->" +codomainString;

    }

    public Production transformFromString(String prod){
        String temp = prod.split("->")[1];
        String[] codomainArray = new String[]{};
        for(int i=0;i<temp.length();i++){
            codomainArray[i]= String.valueOf(temp.charAt(i));
        }
        return new Production(prod.split("->")[0], codomainArray);

    }
}
