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
public class DoubleTest extends StrucktureTest {

    @Struckture(length = 0x10, allowOverlapping = true)
    private static class DoubleTestStructure {
        @StruckField(offset = 0)
        private Double wrapper;
        @StruckField(offset = 0x8)
        private double primitive;
        @StruckField(offset = 0x8) @Reverse
        private double reverse;
    }

    @Test
    public void shouldReadDoubleWrapper() {
        //given
        InputStream stream = stream(
                o(0x3f, 0xd5, 0x55, 0x55, 0x55, 0x55, 0x55, 0x55), o(0, 0, 0, 0, 0, 0, 0, 0),
                o(0, 0, 0, 0, 0, 0, 0, 0), o(0x3f, 0xd5, 0x55, 0x55, 0x55, 0x55, 0x55, 0x55));
        Struck<DoubleTestStructure> struck = Strucktor.forClass(DoubleTestStructure.class);

        //when
        DoubleTestStructure structure = struck.read(stream);

        //then
        assertThat(structure.wrapper, is(1/3d));
     }

    @Test
    public void shouldReadDoublePrimitive() {
        //given
        InputStream stream = stream(
                o(0, 0, 0, 0, 0, 0, 0, 0), o(0x3f, 0xd5, 0x55, 0x55, 0x55, 0x55, 0x55, 0x55),
                o(0x3f, 0xd5, 0x55, 0x55, 0x55, 0x55, 0x55, 0x55), o(0, 0, 0, 0, 0, 0, 0, 0));
        Struck<DoubleTestStructure> struck = Strucktor.forClass(DoubleTestStructure.class);

        //when
        DoubleTestStructure structure = struck.read(stream);

        //then
        assertThat(structure.primitive, is(1/3d));
    }

    @Test
    public void shouldReadDoubleReverse() {
        //given
        InputStream stream = stream(
                o(0, 0, 0, 0, 0, 0, 0, 0), o(0x55, 0x55, 0x55, 0x55, 0x55, 0x55, 0xd5, 0x3f),
                o(0x3f, 0xd5, 0x55, 0x55, 0x55, 0x55, 0x55, 0x55), o(0, 0, 0, 0, 0, 0, 0, 0));
        Struck<DoubleTestStructure> struck = Strucktor.forClass(DoubleTestStructure.class);

        //when
        DoubleTestStructure structure = struck.read(stream);

        //then
        assertThat(structure.reverse, is(1/3d));
    }
}
