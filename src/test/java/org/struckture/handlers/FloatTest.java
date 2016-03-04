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
public class FloatTest extends StrucktureTest {

    @Struckture(length = 0x10)
    private static class FloatTestStructure {
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
                o(0x3f, 0xd5, 0x55, 0x55, 0x55, 0x55, 0x55, 0x55), o(0, 0, 0, 0, 0, 0, 0, 0),
                o(0, 0, 0, 0, 0, 0, 0, 0), o(0x3f, 0xd5, 0x55, 0x55, 0x55, 0x55, 0x55, 0x55));
        Struck<FloatTestStructure> struck = Strucktor.forClass(FloatTestStructure.class);

        //when
        FloatTestStructure structure = struck.read(stream);

        //then
        assertThat(structure.wrapper, is(5/3f));
     }

    @Test
    public void shouldReadFloatPrimitive() {
        //given
        InputStream stream = stream(
                o(0, 0, 0, 0, 0, 0, 0, 0), o(0x3f, 0xd5, 0x55, 0x55, 0x55, 0x55, 0x55, 0x55),
                o(0x3f, 0xd5, 0x55, 0x55, 0x55, 0x55, 0x55, 0x55), o(0, 0, 0, 0, 0, 0, 0, 0));
        Struck<FloatTestStructure> struck = Strucktor.forClass(FloatTestStructure.class);

        //when
        FloatTestStructure structure = struck.read(stream);

        //then
        assertThat(structure.primitive, is(5/3f));
    }

    @Test
    public void shouldReadFloatReverse() {
        //given
        InputStream stream = stream(
                o(0, 0, 0, 0, 0, 0, 0, 0), o(0x55, 0x55, 0x55, 0x55, 0x55, 0x55, 0xd5, 0x3f),
                o(0x3f, 0xd5, 0x55, 0x55, 0x55, 0x55, 0x55, 0x55), o(0, 0, 0, 0, 0, 0, 0, 0));
        Struck<FloatTestStructure> struck = Strucktor.forClass(FloatTestStructure.class);

        //when
        FloatTestStructure structure = struck.read(stream);

        //then
        assertThat(structure.reverse, is(5/3f));
    }
}
