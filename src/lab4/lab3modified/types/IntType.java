package lab4.lab3modified.types;

public class IntType extends NumberType {
    private static final long serialVersionUID = -6272626724896289583L;

    public static final IntType INSTANCE = new IntType();

    private IntType() {
        super(-2147483648, 2147483647);
    }

    @Override
    public boolean equals(Type o) {
        return o instanceof IntType;
    }
}
