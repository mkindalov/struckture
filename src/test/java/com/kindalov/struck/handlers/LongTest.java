package com.kindalov.struck.handlers;

import com.kindalov.struck.Struck;
import com.kindalov.struck.StruckTest;
import com.kindalov.struck.annotations.Field;
import com.kindalov.struck.annotations.Structure;
import org.junit.Test;

import java.io.InputStream;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.junit.MatcherAssert.assertThat;

/**
 * TODO comment.
 */
public class LongTest extends StruckTest {

    @Structure(len = 0x20)
    public static class LongTestStructure {
        @Field(offset = 0x0)
        private Long wrapper;
        @Field(offset = 0x14)
        private long primitive;
    }

    @Test
    public void shouldReadLongWrapper() {
        //given
        InputStream stream = stream(
                x(0, 0, 0, 0x10, 0, 0, 0, 0), x(0x7, 0, 0, 0x5, 0, 0, 0, 0),
                x(0x20, 0, 0, 0x10, 0, 0, 0, 0), x(0x7, 0, 0, 0x5, 0, 0, 0, 0));
        Struck<LongTestStructure> struck = Struck.forClass(LongTestStructure.class);

        //when
        LongTestStructure structure = struck.read(stream);

        //then
        assertThat(structure.wrapper, is(0x1000000000l));
    }

    @Test
    public void shouldReadLongPrimitive() {
        //given
        InputStream stream = stream(
                x(0, 0, 0, 0x10, 0, 0, 0, 0), x(0, 0, 0, 0x5, 0, 0, 0, 0),
                x(0x20, 0, 0, 0x10, 0, 0, 0, 0x03), x(0x1, 0, 0, 0x5, 0, 0, 0, 0));
        Struck<LongTestStructure> struck = Struck.forClass(LongTestStructure.class);

        //when
        LongTestStructure s = struck.read(stream);

        //then
        assertThat(s.primitive, is(0x0301000005l));
    }
}
