package lab3.types;

import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertEquals;

/**
 * Created by jj on 14.12.16..
 */
public class FunctionTypeTest {
    @Test
    public void equalsTest() {
        FunctionType f1 = new FunctionType(
                IntType.INSTANCE,
                Collections.singletonList(
                        new ArrayType(
                                CharType.INSTANCE
                        )
                )
        );
        FunctionType f2 = new FunctionType(
                IntType.INSTANCE,
                Collections.singletonList(
                        new ArrayType(
                                CharType.INSTANCE
                        )
                )
        );

        assertEquals(f1, f2);
    }
}