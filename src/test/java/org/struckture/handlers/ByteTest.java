package org.struckture.handlers;

import org.struckture.base.Struck;
import org.struckture.base.Strucktor;
import org.struckture.base.StrucktureTest;
import org.struckture.base.annotations.StruckField;
import org.struckture.base.annotations.Struckture;
import org.junit.Test;

import java.io.InputStream;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.junit.MatcherAssert.assertThat;

/**
 * Test.
 */
public class ByteTest extends StrucktureTest {

    @Struckture(length = 0x20)
    private static class ByteTestStructure {
        @StruckField(offset = 0x3)
        private Byte wrapper;
        @StruckField(offset = 0x10)
        private byte primitive;
    }

    @Test
    public void shouldReadByteWrapper() {
        //given
        InputStream stream = stream(
                o(0x7, 0, 0, 0x10, 0, 0, 0, 0), o(0x7, 0, 0, 0x5, 0, 0, 0, 0),
                o(0x20, 0, 0, 0x10, 0, 0, 0, 0), o(0x7, 0, 0, 0x5, 0, 0, 0, 0));
        Struck<ByteTestStructure> struck = Strucktor.forClass(ByteTestStructure.class);

        //when
        ByteTestStructure structure = struck.read(stream);

        //then
        assertThat(structure.wrapper, is((byte)0x10));
    }

    @Test
    public void shouldReadBytePrimitive() {
        //given
        InputStream stream = stream(
                o(0, 0, 0, 0x10, 0, 0, 0, 0), o(0, 0, 0, 0x5, 0, 0, 0, 0),
                o(0x20, 0, 0, 0x10, 0, 0, 0, 0x03), o(0x1, 0, 0, 0x5, 0, 0, 0, 0));
        Struck<ByteTestStructure> struck = Strucktor.forClass(ByteTestStructure.class);

        //when
        ByteTestStructure s = struck.read(stream);

        //then
        assertThat(s.primitive, is((byte)0x20));
    }
}
