package lab3.types;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CharType extends NumberType {
    private static final long serialVersionUID = -8862265770296971547L;

    public static final CharType INSTANCE = new CharType();

    static final Set<Character> ALLOWED_ESCAPES = new HashSet<>(Arrays.asList(
            new Character[] {'0', '\'', 'n', 't', '"', '\\'}
    ));

    private CharType() {
        super(0, 255);
    }

    @Override
    public boolean validRange(String value) {
        // prema 4.3.2
        value = value.substring(1, value.length()-1); // iskljucimo navodnike

        if (value.length() == 1) {
            return value.charAt(0) != '\\';
        }

        if (value.length() == 2) {
            if (value.charAt(0) != '\\') {
                return false;
            }
            if (ALLOWED_ESCAPES.contains(value.charAt(1))) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean canImplicitCast(Type target) {
        // vidi 4.3.1 (str. 41)
        return this.equals(target) || target instanceof IntType;
    }

    @Override
    public boolean equals(Type o) {
        return o instanceof CharType;
    }
}