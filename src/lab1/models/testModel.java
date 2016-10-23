package lab1.models;

import java.util.Set;

/**
 * Created by pero5ar on 23.10.2016..
 */
public class testModel {

    private static String line = "(e|(E|$)a)|(+|-)|Q|R|S|T";

    public static void main(String args[]) {
        RegEx regEx = new RegEx(line);
        Set<String> set = regEx.getOptions();
        for (String s : set) {
            System.out.println(s);
        }
    }
}
