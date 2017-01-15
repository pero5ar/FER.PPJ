package lab4.frisc;

import lab4.frisc.models.Label;
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
        String label=null;
        if(CodeGenerator.getInstance().getVariableManager().getVariableLabel(var)!=null) {
            label = CodeGenerator.getInstance().getVariableManager().getVariableLabel(var);
        }
        else{
            boolean t =true;
            while (t){
                t=false;
                scope=scope.getParent();
                var=new Variable(scope, cName);
                if(CodeGenerator.getInstance().getVariableManager().getVariableLabel(var)!=null) {
                    label = CodeGenerator.getInstance().getVariableManager().getVariableLabel(var);
                }
                else{
                    t=true;
                }
            }
        }
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
        Label label = new Label();
        label.setValue(String.valueOf(c));
        CodeGenerator.getInstance().addLabel(label);

        String instuction = "LOAD R6, (" + label.getLabel() + ")";
        CodeGenerator.getInstance().getLines().add(new Line( instuction));
        instuction="RET";
        CodeGenerator.getInstance().getLines().add(new Line( instuction));
    }

    //return x;
    public static void returnVar(Scope scope, String cName){

        Variable var = new Variable(scope, cName);
        String label=null;
        if(CodeGenerator.getInstance().getVariableManager().getVariableLabel(var)!=null) {
             label = CodeGenerator.getInstance().getVariableManager().getVariableLabel(var);
        }
        else{
            boolean t =true;
            while (t){
                t=false;
                scope=scope.getParent();
                var=new Variable(scope, cName);
                if(CodeGenerator.getInstance().getVariableManager().getVariableLabel(var)!=null) {
                    label = CodeGenerator.getInstance().getVariableManager().getVariableLabel(var);
                }
                else{
                    t=true;
                }
            }
        }
        String instuction = "LOAD R6, ("+label+")";
        CodeGenerator.getInstance().getLines().add(new Line( instuction));
        instuction="RET";
        CodeGenerator.getInstance().getLines().add(new Line( instuction));

    }

    // x+y;
    public static void addition(Scope scope, String cNameFirst, String cNameSecond){

        Variable varFirst = new Variable(scope, cNameFirst);
        String labelFirst=null;
        if(CodeGenerator.getInstance().getVariableManager().getVariableLabel(varFirst)!=null) {
            labelFirst = CodeGenerator.getInstance().getVariableManager().getVariableLabel(varFirst);
        }
        else{
            boolean t =true;
            while (t){
                t=false;
                scope=scope.getParent();
                varFirst=new Variable(scope, cNameFirst);
                if(CodeGenerator.getInstance().getVariableManager().getVariableLabel(varFirst)!=null) {
                    labelFirst = CodeGenerator.getInstance().getVariableManager().getVariableLabel(varFirst);
                }
                else{
                    t=true;
                }
            }
        }
        Variable varSecond = new Variable(scope, cNameSecond);
        String labelSecond=null;
        if(CodeGenerator.getInstance().getVariableManager().getVariableLabel(varSecond)!=null) {
            labelSecond = CodeGenerator.getInstance().getVariableManager().getVariableLabel(varSecond);
        }
        else{
            boolean t =true;
            while (t){
                t=false;
                scope=scope.getParent();
                varSecond=new Variable(scope, cNameSecond);
                if(CodeGenerator.getInstance().getVariableManager().getVariableLabel(varSecond)!=null) {
                    labelSecond = CodeGenerator.getInstance().getVariableManager().getVariableLabel(varSecond);
                }
                else{
                    t=true;
                }
            }
        }

        String instuction = "LOAD R0, ("+labelFirst+")";
        CodeGenerator.getInstance().getLines().add(new Line( instuction));
        instuction = "LOAD R1, ("+labelSecond+")";
        CodeGenerator.getInstance().getLines().add(new Line( instuction));
        instuction="ADD R0, R1, R0";
        CodeGenerator.getInstance().getLines().add(new Line( instuction));
        instuction="MOVE R0, R6";
        CodeGenerator.getInstance().getLines().add(new Line( instuction));


    }
    public static void returnVoid(){
        String instruciton = "RET";
        CodeGenerator.getInstance().getLines().add(new Line( instruciton));
    }

    // x+y;
    public static void subtraction(Scope scope, String cNameFirst, String cNameSecond){

        Variable varFirst = new Variable(scope, cNameFirst);
        String labelFirst=null;
        if(CodeGenerator.getInstance().getVariableManager().getVariableLabel(varFirst)!=null) {
            labelFirst = CodeGenerator.getInstance().getVariableManager().getVariableLabel(varFirst);
        }
        else{
            boolean t =true;
            while (t){
                t=false;
                scope=scope.getParent();
                varFirst=new Variable(scope, cNameFirst);
                if(CodeGenerator.getInstance().getVariableManager().getVariableLabel(varFirst)!=null) {
                    labelFirst = CodeGenerator.getInstance().getVariableManager().getVariableLabel(varFirst);
                }
                else{
                    t=true;
                }
            }
        }
        Variable varSecond = new Variable(scope, cNameSecond);
        String labelSecond=null;
        if(CodeGenerator.getInstance().getVariableManager().getVariableLabel(varSecond)!=null) {
            labelSecond = CodeGenerator.getInstance().getVariableManager().getVariableLabel(varSecond);
        }
        else{
            boolean t =true;
            while (t){
                t=false;
                scope=scope.getParent();
                varSecond=new Variable(scope, cNameSecond);
                if(CodeGenerator.getInstance().getVariableManager().getVariableLabel(varSecond)!=null) {
                    labelSecond = CodeGenerator.getInstance().getVariableManager().getVariableLabel(varSecond);
                }
                else{
                    t=true;
                }
            }
        }

        String instuction = "LOAD R0, ("+labelFirst+")";
        CodeGenerator.getInstance().getLines().add(new Line( instuction));
        instuction = "LOAD R1, ("+labelSecond+")";
        CodeGenerator.getInstance().getLines().add(new Line( instuction));
        instuction="SUB R0, R1, R0";
        CodeGenerator.getInstance().getLines().add(new Line( instuction));
        instuction="MOVE R0, R6";
        CodeGenerator.getInstance().getLines().add(new Line( instuction));


    }

    // x|y;
    public static void bitor(Scope scope, String cNameFirst, String cNameSecond){

        Variable varFirst = new Variable(scope, cNameFirst);
        String labelFirst=null;
        if(CodeGenerator.getInstance().getVariableManager().getVariableLabel(varFirst)!=null) {
            labelFirst = CodeGenerator.getInstance().getVariableManager().getVariableLabel(varFirst);
        }
        else{
            boolean t =true;
            while (t){
                t=false;
                scope=scope.getParent();
                varFirst=new Variable(scope, cNameFirst);
                if(CodeGenerator.getInstance().getVariableManager().getVariableLabel(varFirst)!=null) {
                    labelFirst = CodeGenerator.getInstance().getVariableManager().getVariableLabel(varFirst);
                }
                else{
                    t=true;
                }
            }
        }
        Variable varSecond = new Variable(scope, cNameSecond);
        String labelSecond=null;
        if(CodeGenerator.getInstance().getVariableManager().getVariableLabel(varSecond)!=null) {
            labelSecond = CodeGenerator.getInstance().getVariableManager().getVariableLabel(varSecond);
        }
        else{
            boolean t =true;
            while (t){
                t=false;
                scope=scope.getParent();
                varSecond=new Variable(scope, cNameSecond);
                if(CodeGenerator.getInstance().getVariableManager().getVariableLabel(varSecond)!=null) {
                    labelSecond = CodeGenerator.getInstance().getVariableManager().getVariableLabel(varSecond);
                }
                else{
                    t=true;
                }
            }
        }

        String instuction = "LOAD R0, ("+labelFirst+")";
        CodeGenerator.getInstance().getLines().add(new Line( instuction));
        instuction = "LOAD R1, ("+labelSecond+")";
        CodeGenerator.getInstance().getLines().add(new Line( instuction));
        instuction="OR R0, R1, R0";
        CodeGenerator.getInstance().getLines().add(new Line( instuction));
        instuction="MOVE R0, R6";
        CodeGenerator.getInstance().getLines().add(new Line( instuction));


    }

    public static void bitand(Scope scope, String cNameFirst, String cNameSecond){

        Variable varFirst = new Variable(scope, cNameFirst);
        String labelFirst=null;
        if(CodeGenerator.getInstance().getVariableManager().getVariableLabel(varFirst)!=null) {
            labelFirst = CodeGenerator.getInstance().getVariableManager().getVariableLabel(varFirst);
        }
        else{
            boolean t =true;
            while (t){
                t=false;
                scope=scope.getParent();
                varFirst=new Variable(scope, cNameFirst);
                if(CodeGenerator.getInstance().getVariableManager().getVariableLabel(varFirst)!=null) {
                    labelFirst = CodeGenerator.getInstance().getVariableManager().getVariableLabel(varFirst);
                }
                else{
                    t=true;
                }
            }
        }
        Variable varSecond = new Variable(scope, cNameSecond);
        String labelSecond=null;
        if(CodeGenerator.getInstance().getVariableManager().getVariableLabel(varSecond)!=null) {
            labelSecond = CodeGenerator.getInstance().getVariableManager().getVariableLabel(varSecond);
        }
        else{
            boolean t =true;
            while (t){
                t=false;
                scope=scope.getParent();
                varSecond=new Variable(scope, cNameSecond);
                if(CodeGenerator.getInstance().getVariableManager().getVariableLabel(varSecond)!=null) {
                    labelSecond = CodeGenerator.getInstance().getVariableManager().getVariableLabel(varSecond);
                }
                else{
                    t=true;
                }
            }
        }

        String instuction = "LOAD R0, ("+labelFirst+")";
        CodeGenerator.getInstance().getLines().add(new Line( instuction));
        instuction = "LOAD R1, ("+labelSecond+")";
        CodeGenerator.getInstance().getLines().add(new Line( instuction));
        instuction="AND R0, R1, R0";
        CodeGenerator.getInstance().getLines().add(new Line( instuction));
        instuction="MOVE R0, R6";
        CodeGenerator.getInstance().getLines().add(new Line( instuction));


    }

    public static void bitxor(Scope scope, String cNameFirst, String cNameSecond){

        Variable varFirst = new Variable(scope, cNameFirst);
        String labelFirst=null;
        if(CodeGenerator.getInstance().getVariableManager().getVariableLabel(varFirst)!=null) {
            labelFirst = CodeGenerator.getInstance().getVariableManager().getVariableLabel(varFirst);
        }
        else{
            boolean t =true;
            while (t){
                t=false;
                scope=scope.getParent();
                varFirst=new Variable(scope, cNameFirst);
                if(CodeGenerator.getInstance().getVariableManager().getVariableLabel(varFirst)!=null) {
                    labelFirst = CodeGenerator.getInstance().getVariableManager().getVariableLabel(varFirst);
                }
                else{
                    t=true;
                }
            }
        }
        Variable varSecond = new Variable(scope, cNameSecond);
        String labelSecond=null;
        if(CodeGenerator.getInstance().getVariableManager().getVariableLabel(varSecond)!=null) {
            labelSecond = CodeGenerator.getInstance().getVariableManager().getVariableLabel(varSecond);
        }
        else{
            boolean t =true;
            while (t){
                t=false;
                scope=scope.getParent();
                varSecond=new Variable(scope, cNameSecond);
                if(CodeGenerator.getInstance().getVariableManager().getVariableLabel(varSecond)!=null) {
                    labelSecond = CodeGenerator.getInstance().getVariableManager().getVariableLabel(varSecond);
                }
                else{
                    t=true;
                }
            }
        }

        String instuction = "LOAD R0, ("+labelFirst+")";
        CodeGenerator.getInstance().getLines().add(new Line( instuction));
        instuction = "LOAD R1, ("+labelSecond+")";
        CodeGenerator.getInstance().getLines().add(new Line( instuction));
        instuction="XOR R0, R1, R0";
        CodeGenerator.getInstance().getLines().add(new Line( instuction));
        instuction="MOVE R0, R6";
        CodeGenerator.getInstance().getLines().add(new Line( instuction));


    }
}
