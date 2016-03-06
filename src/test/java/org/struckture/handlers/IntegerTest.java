package org.struckture.handlers;

import org.struckture.base.Struck;
import org.struckture.base.Strucktor;
import org.struckture.base.StrucktureTest;
import org.struckture.base.annotations.Reverse;
import org.struckture.base.annotations.StruckField;
import org.struckture.base.annotations.Struckture;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.junit.MatcherAssert.assertThat;

/**
 * Test.
 */
public class IntegerTest extends StrucktureTest {

    @Struckture(length = 0x10, allowOverlapping = true)
    private static class IntegerTestStructure {
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
                o(0, 0, 0, 0x10, 0, 0, 0, 0), o(0x7, 0, 0, 0x5, 0, 0, 0, 0),
                o(0x20, 0, 0, 0x10, 0, 0, 0, 0), o(0x7, 0, 0, 0x5, 0, 0, 0, 0));

        Struck<IntegerTestStructure> struck = Strucktor.forClass(IntegerTestStructure.class);

        //when
        IntegerTestStructure structure = struck.read(stream);

       //then
        assertThat(structure.wrapper, is(0x10));
    }

    @Test
    public void shouldReadIntegerPrimitive() {
        //given
        InputStream stream = stream(
                o(0, 0, 0, 0x10, 0, 0, 0, 0), o(0x1, 0, 0, 0x5, 0, 0, 0, 0),
                o(0x20, 0, 0, 0x10, 0, 0, 0, 0), o(0x1, 0, 0, 0x5, 0, 0, 0, 0));
        Struck<IntegerTestStructure> struck = Strucktor.forClass(IntegerTestStructure.class);

        //when
        IntegerTestStructure structure = struck.read(stream);

        //then
        assertThat(structure.primitive, is(0x1000005));
    }

    @Test
    public void shouldReadIntegerReverse() {
        //given
        InputStream stream = stream(
                o(0, 0, 0, 0x10, 0, 0, 0, 0), o(0x1, 0, 0, 0x5, 0, 0, 0, 0),
                o(0x20, 0, 0, 0x10, 0, 0, 0, 0), o(0x1, 0, 0, 0x5, 0, 0, 0, 0));
        Struck<IntegerTestStructure> struck = Strucktor.forClass(IntegerTestStructure.class);

        //when
        IntegerTestStructure structure = struck.read(stream);

        //then
        assertThat(structure.reverse, is(0x5000001));
    }
}
