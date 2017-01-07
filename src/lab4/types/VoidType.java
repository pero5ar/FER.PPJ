package lab4.types;

public class VoidType extends Type {
    public static final VoidType INSTANCE = new VoidType();

    private VoidType() {}

    @Override
    public boolean canImplicitCast(Type target) {
        return this.equals(target);
    }

    @Override
    public boolean equals(Type o) {
        return o instanceof VoidType;
    }
}
