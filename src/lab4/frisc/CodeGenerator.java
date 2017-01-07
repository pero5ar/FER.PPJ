package lab4.frisc;

/**
 * Created by pero5ar on 7.1.2017..
 */
public class CodeGenerator {

    private static CodeGenerator instance = null;

    private RegisterManager registerManager;
    private SubroutineManager subroutineManager;
    private VariableManager variableManager;

    private CodeGenerator() {
        registerManager = new RegisterManager();
        subroutineManager = new SubroutineManager();
        variableManager = new VariableManager();
    }

    public static CodeGenerator getInstance() {
        if (instance == null) instance = new CodeGenerator();
        return instance;
    }
}
