package lab3.types;

public class IntType extends NumberType {
    public static final IntType INSTANCE = new IntType();

    private IntType() {
        super(-2147483648, 2147483647);
    }

    @Override
    public boolean equals(Type o) {
        return o == this;
    }
}
