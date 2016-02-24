package com.kindalov.struck.handlers;

import com.kindalov.struck.Struck;
import com.kindalov.struck.StruckTest;
import com.kindalov.struck.annotations.Field;
import com.kindalov.struck.annotations.Structure;
import org.hamcrest.collection.IsArrayContaining;
import org.junit.Test;

import java.io.InputStream;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsArrayContaining.hasItemInArray;
import static org.hamcrest.junit.MatcherAssert.assertThat;

/**
 * TODO comment.
 */
public class ByteArrayTest extends StruckTest {

    @Structure(len = 0x20)
    public static class ByteArrayTestStructure {
        @Field(offset = 0x10, size = 5)
        private byte[] array;
    }

    @Test
    public void shouldReadByteArray() {
        //given
        InputStream stream = stream(
                x(0x7, 0, 0, 0x10, 0, 0, 0, 0), x(0x7, 0, 0, 0x5, 0, 0, 0, 0),
                x(0x20, 0, 0, 0x10, 0, 0, 0, 0), x(0x7, 0, 0, 0x5, 0, 0, 0, 0));
        Struck<ByteArrayTestStructure> struck = Struck.forClass(ByteArrayTestStructure.class);

        //when
        ByteArrayTestStructure structure = struck.read(stream);

        //then
        assertThat(structure.array, is(new byte[] {0x20, 0, 0, 0x10, 0}));
    }
}
