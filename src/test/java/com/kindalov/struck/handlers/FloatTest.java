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
public class FloatTest extends StruckTest {

    @Structure(len = 0x10)
    public static class FloatTestStructure {
        @StruckField(offset = 0)
        private Float wrapper;
        @StruckField(offset = 0x8)
        private float primitive;
        @StruckField(offset = 0xc) @Reverse
        private float reverse;
    }

    @Test
    public void shouldReadFloatWrapper() {
        //given
        InputStream stream = stream(
                x(0x3f, 0xd5, 0x55, 0x55, 0x55, 0x55, 0x55, 0x55), x(0, 0, 0, 0, 0, 0, 0, 0),
                x(0, 0, 0, 0, 0, 0, 0, 0), x(0x3f, 0xd5, 0x55, 0x55, 0x55, 0x55, 0x55, 0x55));
        Struck<FloatTestStructure> struck = Struck.forClass(FloatTestStructure.class);

        //when
        FloatTestStructure structure = struck.read(stream);

        //then
        assertThat(structure.wrapper, is(5/3f));
     }

    @Test
    public void shouldReadFloatPrimitive() {
        //given
        InputStream stream = stream(
                x(0, 0, 0, 0, 0, 0, 0, 0), x(0x3f, 0xd5, 0x55, 0x55, 0x55, 0x55, 0x55, 0x55),
                x(0x3f, 0xd5, 0x55, 0x55, 0x55, 0x55, 0x55, 0x55), x(0, 0, 0, 0, 0, 0, 0, 0));
        Struck<FloatTestStructure> struck = Struck.forClass(FloatTestStructure.class);

        //when
        FloatTestStructure structure = struck.read(stream);

        //then
        assertThat(structure.primitive, is(5/3f));
    }

    @Test
    public void shouldReadFloatReverse() {
        //given
        InputStream stream = stream(
                x(0, 0, 0, 0, 0, 0, 0, 0), x(0x55, 0x55, 0x55, 0x55, 0x55, 0x55, 0xd5, 0x3f),
                x(0x3f, 0xd5, 0x55, 0x55, 0x55, 0x55, 0x55, 0x55), x(0, 0, 0, 0, 0, 0, 0, 0));
        Struck<FloatTestStructure> struck = Struck.forClass(FloatTestStructure.class);

        //when
        FloatTestStructure structure = struck.read(stream);

        //then
        assertThat(structure.reverse, is(5/3f));
    }
}
