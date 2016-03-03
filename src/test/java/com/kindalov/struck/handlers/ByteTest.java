package com.kindalov.struck.handlers;

import com.kindalov.struck.Struck;
import com.kindalov.struck.StruckTest;
import com.kindalov.struck.annotations.StruckField;
import com.kindalov.struck.annotations.Structure;
import org.junit.Test;

import java.io.InputStream;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.junit.MatcherAssert.assertThat;

/**
 * TODO comment.
 */
public class ByteTest extends StruckTest {

    @Structure(len = 0x20)
    public static class ByteTestStructure {
        @StruckField(offset = 0x3)
        private Byte wrapper;
        @StruckField(offset = 0x10)
        private byte primitive;
    }

    @Test
    public void shouldReadByteWrapper() {
        //given
        InputStream stream = stream(
                x(0x7, 0, 0, 0x10, 0, 0, 0, 0), x(0x7, 0, 0, 0x5, 0, 0, 0, 0),
                x(0x20, 0, 0, 0x10, 0, 0, 0, 0), x(0x7, 0, 0, 0x5, 0, 0, 0, 0));
        Struck<ByteTestStructure> struck = Struck.forClass(ByteTestStructure.class);

        //when
        ByteTestStructure structure = struck.read(stream);

        //then
        assertThat(structure.wrapper, is((byte)0x10));
    }

    @Test
    public void shouldReadBytePrimitive() {
        //given
        InputStream stream = stream(
                x(0, 0, 0, 0x10, 0, 0, 0, 0), x(0, 0, 0, 0x5, 0, 0, 0, 0),
                x(0x20, 0, 0, 0x10, 0, 0, 0, 0x03), x(0x1, 0, 0, 0x5, 0, 0, 0, 0));
        Struck<ByteTestStructure> struck = Struck.forClass(ByteTestStructure.class);

        //when
        ByteTestStructure s = struck.read(stream);

        //then
        assertThat(s.primitive, is((byte)0x20));
    }
}
