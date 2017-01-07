package lab4.frisc;

import lab3.models.Scope;
import lab4.frisc.models.Variable;

/**
 * Created by Nicky on 7.1.2017..
 */
public class InstructionGenerator {

    //int x;
    public static void deklaracijaaVarijable(String type, String cName, Scope scope){

        Variable var = new Variable(scope, cName, type, "0");
        String insturkcija="DW %D 0";
        CodeGenerator.getInstance().getVariableManager().createVariable(var, insturkcija);
    }

    //int x = 3 ;
    public static void deklaracijaIDefinicijaVarijable(String type, String cName, Scope scope, String value){

        Variable var = new Variable(scope, cName, type, value);
        String insturkcija="DW %D "+value;
        CodeGenerator.getInstance().getVariableManager().createVariable(var, insturkcija);
    }

    //x = 3;
    public static void definicijaVarijable(String cName, String value, Scope scope){

        //gettat varijablu
        //promjenit variajblu
        //spremi promjene


    }



}
