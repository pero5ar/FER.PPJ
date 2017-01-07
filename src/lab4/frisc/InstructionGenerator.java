package lab4.frisc;

import lab3.models.Scope;
import lab4.frisc.models.Variable;

/**
 * Created by Nicky on 7.1.2017..
 */
public class InstructionGenerator {

    //int x;
    public static void deklaracijaaVarijable( String cName, Scope scope){

        Variable var = new Variable(scope, cName);
        String instrukcija="DW %D 0";
        CodeGenerator.getInstance().getVariableManager().createVariable(var, instrukcija);
    }

    //int x = 3 ;
    public static void deklaracijaIDefinicijaVarijable( String cName, Scope scope, String value){

        Variable var = new Variable(scope, cName);
        String insturkcija="DW %D "+value;
        CodeGenerator.getInstance().getVariableManager().createVariable(var, insturkcija);
    }

    //x = 3;
    public static void definicijaVarijable(String cName, String value, Scope scope){

        Variable var = new Variable(scope, cName);
        String label = CodeGenerator.getInstance().getVariableManager().getVariableLabel(var);



    }



}
