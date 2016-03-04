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
public class LongTest extends StrucktureTest {

    @Struckture(length = 0x20, allowOverlapping = true)
    private static class LongTestStructure {
        @StruckField(offset = 0x0)
        private Long wrapper;
        @StruckField(offset = 0x14)
        private long primitive;
        @StruckField(offset = 0x18) @Reverse
        private long reverse;
    }

    @Test
    public void shouldReadLongWrapper() {
        //given
        InputStream stream = stream(
                o(0, 0, 0, 0x10, 0, 0, 0, 0), o(0x7, 0, 0, 0x5, 0, 0, 0, 0),
                o(0x20, 0, 0, 0x10, 0, 0, 0, 0), o(0x7, 0, 0, 0x5, 0, 0, 0, 0));
        Struck<LongTestStructure> struck = Strucktor.forClass(LongTestStructure.class);

        //when
        LongTestStructure structure = struck.read(stream);

        //then
        assertThat(structure.wrapper, is(0x1000000000l));
    }

    @Test
    public void shouldReadLongPrimitive() {
        //given
        InputStream stream = stream(
                o(0, 0, 0, 0x10, 0, 0, 0, 0), o(0, 0, 0, 0x5, 0, 0, 0, 0),
                o(0x20, 0, 0, 0x10, 0, 0, 0, 0x03), o(0x1, 0, 0, 0x5, 0, 0, 0, 0));
        Struck<LongTestStructure> struck = Strucktor.forClass(LongTestStructure.class);

        //when
        LongTestStructure s = struck.read(stream);

        //then
        assertThat(s.primitive, is(0x0301000005l));
    }

    @Test
    public void shouldReadLongReverse() {
        //given
        InputStream stream = stream(
                o(0, 0, 0, 0x10, 0, 0, 0, 0), o(0, 0, 0, 0x5, 0, 0, 0, 0),
                o(0x20, 0, 0, 0x10, 0, 0, 0, 0x03), o(0x1, 0, 0, 0x5, 0, 0, 0, 0));
        Struck<LongTestStructure> struck = Strucktor.forClass(LongTestStructure.class);

        //when
        LongTestStructure s = struck.read(stream);

        //then
        assertThat(s.reverse, is(0x5000001l));
    }
}
