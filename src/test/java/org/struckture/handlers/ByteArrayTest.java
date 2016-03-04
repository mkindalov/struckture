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
public class ByteArrayTest extends StrucktureTest {

    @Struckture(length = 0x20)
    private static class ByteArrayTestStructure {
        @StruckField(offset = 0x10, size = 5)
        private byte[] array;

        @StruckField(offset = 0x18, size = 4) @Reverse
        private byte[] reversed;
    }

    @Test
    public void shouldReadByteArray() {
        //given
        InputStream stream = stream(
                o(0x7, 0, 0, 0x10, 0, 0, 0, 0), o(0x7, 0, 0, 0x5, 0, 0, 0, 0),
                o(0x20, 0, 0, 0x10, 0, 0, 0, 0), o(0x7, 0, 0, 0x5, 0, 0, 0, 0));
        Struck<ByteArrayTestStructure> struck = Strucktor.forClass(ByteArrayTestStructure.class);

        //when
        ByteArrayTestStructure structure = struck.read(stream);

        //then
        assertThat(structure.array, is(new byte[]{0x20, 0, 0, 0x10, 0}));
    }

    @Test
    public void shouldReadByteArrayReversed() {
        //given
        InputStream stream = stream(
                o(0x7, 0, 0, 0x10, 0, 0, 0, 0), o(0x7, 0, 0, 0x5, 0, 0, 0, 0),
                o(0x20, 0, 0, 0x10, 0, 0, 0, 0), o(0x7, 0, 0, 0x5, 0, 0, 0, 0));
        Struck<ByteArrayTestStructure> struck = Strucktor.forClass(ByteArrayTestStructure.class);

        //when
        ByteArrayTestStructure structure = struck.read(stream);

        //then
        assertThat(structure.reversed, is(new byte[] {0x5, 0, 0, 0x7}));
    }
}
