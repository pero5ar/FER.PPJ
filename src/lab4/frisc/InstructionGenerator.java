package lab4.frisc;

import lab4.lab3modified.models.Scope;
import lab4.frisc.models.Line;
import lab4.frisc.models.Variable;

import java.util.ArrayList;

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
        String instrukcija ="MOVE "+value+", "+label;
        CodeGenerator.getInstance().getLines().add(new Line(instrukcija));

    }

    //int f(int x, int y,...){
    public static void definicijaIntFunkcije(String cName, ArrayList<String> args, Scope scope){

        int i = args.size();
        String functionLabel;
        if(cName.equals("main")){
            functionLabel = "F_MAIN";
        }
        else {
            functionLabel = CodeGenerator.getInstance().getSubroutineManager().createSubrutine(cName);
        }
        if(i==0){
             String instruction = "ADD R0, 0, R0";
             CodeGenerator.getInstance().getLines().add(new Line(functionLabel, instruction));
        }
        else{
            for(int n=i; n>0;n--){
                if(n==i){
                    //prva inst5rukcija, ukljucit labelu
                    //i-n+1
                    Variable var = new Variable(scope, args.get(i-n));
                    String label = CodeGenerator.getInstance().getVariableManager().getVariableLabel(var);
                    int broj=4*n;
                    String instuction = "LOAD R0, (R7 + "+Integer.toHexString(broj)+")";
                    CodeGenerator.getInstance().getLines().add(new Line(functionLabel, instuction));
                    instuction = "MOVE R0, (" +label+")";
                    CodeGenerator.getInstance().getLines().add(new Line( instuction));
                }
                else{
                    Variable var = new Variable(scope, args.get(i-n));
                    String label = CodeGenerator.getInstance().getVariableManager().getVariableLabel(var);
                    int broj=4*n;
                    String instuction = "LOAD R0, (R7 + "+Integer.toHexString(broj)+")";
                    CodeGenerator.getInstance().getLines().add(new Line( instuction));
                    instuction = "MOVE R0, (" +label+")";
                    CodeGenerator.getInstance().getLines().add(new Line( instuction));
                    //ostale instrukcije
                }
            }
        }
    }

    //return 42;
    public static void returnConst(Scope scope, int c){

        String instuction = "MOVE %D "+Integer.valueOf(c)+", R6";
        CodeGenerator.getInstance().getLines().add(new Line( instuction));
        instuction="RET";
        CodeGenerator.getInstance().getLines().add(new Line( instuction));
    }

    //return x;
    public static void returnVar(Scope scope, String cName){

        Variable var = new Variable(scope, cName);
        String label = CodeGenerator.getInstance().getVariableManager().getVariableLabel(var);
        String instuction = "LOAD R6, ("+label+")";
        CodeGenerator.getInstance().getLines().add(new Line( instuction));
        instuction="RET";
        CodeGenerator.getInstance().getLines().add(new Line( instuction));
    }
}
