package lab4.lab3modified.semantic;

public class SemanticException extends RuntimeException {
    private static final long serialVersionUID = 1866747635363200029L;

    public final String output;

    public SemanticException(String output){
        this(output, "");
    }

    public SemanticException(String output, String message){
        super(message);
        this.output = output;
    }
}
