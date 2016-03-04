package org.struckture.handlers;

import org.struckture.base.Struck;
import org.struckture.base.Strucktor;
import org.struckture.base.StrucktureTest;
import org.struckture.base.annotations.Reverse;
import org.struckture.base.annotations.StruckField;
import org.struckture.base.annotations.Struckture;
import org.junit.Test;

import java.io.InputStream;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.junit.MatcherAssert.assertThat;

/**
 * Test.
 */
public class ShortTest extends StrucktureTest {

    @Struckture(length = 0x20)
    private static class ShortTestStructure {
        @StruckField(offset = 0x3)
        private Short wrapper;
        @StruckField(offset = 0x10)
        private short primitive;
        @StruckField(offset = 0x18) @Reverse
        private short reverse;
    }

    @Test
    public void shouldReadShortWrapper() {
        //given
        InputStream stream = stream(
                o(0x7, 0, 0, 0x10, 0, 0, 0, 0), o(0x7, 0, 0, 0x5, 0, 0, 0, 0),
                o(0x20, 0, 0, 0x10, 0, 0, 0, 0), o(0x7, 0, 0, 0x5, 0, 0, 0, 0));
        Struck<ShortTestStructure> struck = Strucktor.forClass(ShortTestStructure.class);

        //when
        ShortTestStructure structure = struck.read(stream);

        //then
        assertThat(structure.wrapper, is((short)0x1000));
    }

    @Test
    public void shouldReadShortPrimitive() {
        //given
        InputStream stream = stream(
                o(0, 0, 0, 0x10, 0, 0, 0, 0), o(0, 0, 0, 0x5, 0, 0, 0, 0),
                o(0x20, 0, 0, 0x10, 0, 0, 0, 0x03), o(0x1, 0, 0, 0x5, 0, 0, 0, 0));
        Struck<ShortTestStructure> struck = Strucktor.forClass(ShortTestStructure.class);

        //when
        ShortTestStructure s = struck.read(stream);

        //then
        assertThat(s.primitive, is((short) 0x2000));
    }

    @Test
    public void shouldReadShortReverse() {
        //given
        InputStream stream = stream(
                o(0, 0, 0, 0x10, 0, 0, 0, 0), o(0, 0, 0, 0x5, 0, 0, 0, 0),
                o(0x20, 0, 0, 0x10, 0, 0, 0, 0x03), o(0x1, 0, 0, 0x5, 0, 0, 0, 0));
        Struck<ShortTestStructure> struck = Strucktor.forClass(ShortTestStructure.class);

        //when
        ShortTestStructure s = struck.read(stream);

        //then
        assertThat(s.reverse, is((short) 0x1));
    }
}
