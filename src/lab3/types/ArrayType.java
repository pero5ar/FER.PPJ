package lab3.types;

/**
 * @author JJ
 */
public class ArrayType extends Type {
    private static final long serialVersionUID = 4003871070080564848L;

    public final PrimitiveType elementType;

    public ArrayType(PrimitiveType type) {
        this.elementType = type;
    }

    @Override
    public boolean equals(Type o) {
        if (!(o instanceof ArrayType)) {
            return false;
        }

        ArrayType otherArray = (ArrayType) o;
        return this.elementType.equals(otherArray.elementType);
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

        if (this.elementType instanceof ConstType) {
            ConstType targetConst = (ConstType) targetArray.elementType;
            return this.elementType.canImplicitCast(targetConst.wrappedType);
        } else {
            return this.elementType.canImplicitCast(targetArray.elementType);
        }
    }

    public static boolean validString(String value) {
        if (value.length() < 2 || value.charAt(0) != '"' || !value.endsWith("\"")) {
            return false;
        }
        value = value.substring(1, value.length()-1);

        boolean escaping = false;
        for(char c : value.toCharArray()) {
            if (escaping) {
                if (!CharType.ALLOWED_ESCAPES.contains(c)) {
                    return false;
                }
                escaping = false;
                continue;
            }

            if (c == '\\') {
                escaping = true;
                continue;
            }
            if (c == '"') {
                return false;
            }
        }

        return !escaping;
    }

    public static int calcStringLength(String value) {
        value = value.substring(1, value.length()-1);

        int length = 0;
        boolean escaping = false;
        for(char c : value.toCharArray()) {
            if (escaping) {
                escaping = false;
                continue;
            }

            escaping = (c == '\\');
            length++;
        }

        return length;
    }
}
