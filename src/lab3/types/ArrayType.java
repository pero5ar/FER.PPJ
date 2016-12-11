package lab3.types;

/**
 * @author JJ
 */
public class ArrayType extends Type {
    private final PrimitiveType elementType;

    public ArrayType(PrimitiveType type) {
        this.elementType = type;
    }

    public PrimitiveType getElementType() {
        return elementType;
    }

    @Override
    public boolean canImplicitCast(Type target) {
        // vidi 4.3.1 (str. 41) - implicitne promjene tipa
        if (this.equals(target)) {
            return true;
        }

        if (!(target instanceof ArrayType)) {
            return false;
        }

        ArrayType targetArray = (ArrayType) target;
        if (!(targetArray.elementType instanceof ConstType)) {
            return false;
        }

        // voodoo magic by experiment
        ConstType targetConst = (ConstType) targetArray.elementType;
        if (this.elementType instanceof ConstType) {
            return this.elementType.canImplicitCast(targetConst.getType());
        } else {
            return this.elementType.canImplicitCast(targetArray.elementType);
        }
    }

    @Override
    public boolean equals(Type o) {
        if (!(o instanceof ArrayType)) {
            return false;
        }

        ArrayType oArray = (ArrayType) o;
        return elementType.equals(oArray.elementType);
    }

    public static boolean validString(String value) {
        value = value.substring(1, value.length()-1);

        boolean escaping = false;
        for(char c : value.toCharArray()) {
            if (escaping) {
                if (!CharType.ALLOWED_ESCAPES.contains(c)) {
                    return false;
                }
                escaping = false;
            }

            if (c == '\\') {
                escaping = true;
            }
        }

        return !escaping;
    }
}
