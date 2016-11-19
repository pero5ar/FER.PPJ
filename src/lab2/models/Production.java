package lab2.models;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * Created by pero5ar on 10.11.2016..
 */
public class Production implements Serializable {

    private static final long serialVersionUID = -7753802406325313450L;

    private String domain;
    private String[] codomain;
    private List<String> codomainAsList;

    public Production(String domain, String[] codomain) {
        this.domain = domain;
        this.codomain = codomain;
        codomainAsList = Arrays.asList(codomain);
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

    public  String transformToString(){
        return this.domain + "->" +codomain;

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
