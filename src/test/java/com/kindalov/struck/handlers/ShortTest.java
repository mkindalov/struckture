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
public class ShortTest extends StruckTest {

    @Structure(len = 0x20)
    public static class ShortTestStructure {
        @Field(offset = 0x3)
        private Short wrapper;
        @Field(offset = 0x10)
        private short primitive;
    }

    @Test
    public void shouldReadShortWrapper() {
        //given
        InputStream stream = stream(
                x(0x7, 0, 0, 0x10, 0, 0, 0, 0), x(0x7, 0, 0, 0x5, 0, 0, 0, 0),
                x(0x20, 0, 0, 0x10, 0, 0, 0, 0), x(0x7, 0, 0, 0x5, 0, 0, 0, 0));
        Struck<ShortTestStructure> struck = Struck.forClass(ShortTestStructure.class);

        //when
        ShortTestStructure structure = struck.read(stream);

        //then
        assertThat(structure.wrapper, is((short)0x1000));
    }

    @Test
    public void shouldReadShortPrimitive() {
        //given
        InputStream stream = stream(
                x(0, 0, 0, 0x10, 0, 0, 0, 0), x(0, 0, 0, 0x5, 0, 0, 0, 0),
                x(0x20, 0, 0, 0x10, 0, 0, 0, 0x03), x(0x1, 0, 0, 0x5, 0, 0, 0, 0));
        Struck<ShortTestStructure> struck = Struck.forClass(ShortTestStructure.class);

        //when
        ShortTestStructure s = struck.read(stream);

        //then
        assertThat(s.primitive, is((short)0x2000));
    }
}
