package lab3.types;

public class VoidType extends Type {
    public static final VoidType INSTANCE = new VoidType();

    private VoidType() {}

    @Override
    public boolean equals(Type o) {
        return o == this;
    }
}
