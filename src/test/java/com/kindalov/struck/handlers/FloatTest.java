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
public class FloatTest extends StruckTest {

    @Structure(len = 0x10)
    public static class FloatTestStructure {
        @Field(offset = 0)
        private Float wrapper;
        @Field(offset = 0x8)
        private float primitive;
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
        assertThat(structure.wrapper, is(1/3));
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
        assertThat(structure.primitive, is(1/3));
    }
}
