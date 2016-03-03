package com.kindalov.struck.handlers;

import com.kindalov.struck.Struck;
import com.kindalov.struck.StruckTest;
import com.kindalov.struck.annotations.Reverse;
import com.kindalov.struck.annotations.StruckField;
import com.kindalov.struck.annotations.Structure;
import org.junit.Test;

import java.io.InputStream;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.junit.MatcherAssert.assertThat;

/**
 * TODO comment.
 */
public class IntegerTest extends StruckTest {

    @Structure(len = 0x10)
    public static class IntegerTestStructure {
        @StruckField(offset = 0x0)
        private Integer wrapper;
        @StruckField(offset = 0x8)
        private int primitive;
        @StruckField(offset = 0x8) @Reverse
        private int reverse;
    }

    @Test
    public void shouldReadIntegerWrapper() {
        //given
        InputStream stream = stream(
                x(0, 0, 0, 0x10, 0, 0, 0, 0), x(0x7, 0, 0, 0x5, 0, 0, 0, 0),
                x(0x20, 0, 0, 0x10, 0, 0, 0, 0), x(0x7, 0, 0, 0x5, 0, 0, 0, 0));

        Struck<IntegerTestStructure> struck = Struck.forClass(IntegerTestStructure.class);

        //when
        IntegerTestStructure structure = struck.read(stream);

       //then
        assertThat(structure.wrapper, is(0x10));
    }

    @Test
    public void shouldReadIntegerPrimitive() {
        //given
        InputStream stream = stream(
                x(0, 0, 0, 0x10, 0, 0, 0, 0), x(0x1, 0, 0, 0x5, 0, 0, 0, 0),
                x(0x20, 0, 0, 0x10, 0, 0, 0, 0), x(0x1, 0, 0, 0x5, 0, 0, 0, 0));
        Struck<IntegerTestStructure> struck = Struck.forClass(IntegerTestStructure.class);

        //when
        IntegerTestStructure s = struck.read(stream);

        //then
        assertThat(s.primitive, is(0x1000005));
    }

    @Test
    public void shouldReadIntegerReverse() {
        //given
        InputStream stream = stream(
                x(0, 0, 0, 0x10, 0, 0, 0, 0), x(0x1, 0, 0, 0x5, 0, 0, 0, 0),
                x(0x20, 0, 0, 0x10, 0, 0, 0, 0), x(0x1, 0, 0, 0x5, 0, 0, 0, 0));
        Struck<IntegerTestStructure> struck = Struck.forClass(IntegerTestStructure.class);

        //when
        IntegerTestStructure s = struck.read(stream);

        //then
        assertThat(s.reverse, is(0x5000001));
    }
}
