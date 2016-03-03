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
public class DoubleTest extends StruckTest {

    @Structure(len = 0x10)
    public static class DoubleTestStructure {
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
                x(0x3f, 0xd5, 0x55, 0x55, 0x55, 0x55, 0x55, 0x55), x(0, 0, 0, 0, 0, 0, 0, 0),
                x(0, 0, 0, 0, 0, 0, 0, 0), x(0x3f, 0xd5, 0x55, 0x55, 0x55, 0x55, 0x55, 0x55));
        Struck<DoubleTestStructure> struck = Struck.forClass(DoubleTestStructure.class);

        //when
        DoubleTestStructure structure = struck.read(stream);

        //then
        assertThat(structure.wrapper, is(1/3d));
     }

    @Test
    public void shouldReadDoublePrimitive() {
        //given
        InputStream stream = stream(
                x(0, 0, 0, 0, 0, 0, 0, 0), x(0x3f, 0xd5, 0x55, 0x55, 0x55, 0x55, 0x55, 0x55),
                x(0x3f, 0xd5, 0x55, 0x55, 0x55, 0x55, 0x55, 0x55), x(0, 0, 0, 0, 0, 0, 0, 0));
        Struck<DoubleTestStructure> struck = Struck.forClass(DoubleTestStructure.class);

        //when
        DoubleTestStructure structure = struck.read(stream);

        //then
        assertThat(structure.primitive, is(1/3d));
    }

    @Test
    public void shouldReadDoubleReverse() {
        //given
        InputStream stream = stream(
                x(0, 0, 0, 0, 0, 0, 0, 0), x(0x55, 0x55, 0x55, 0x55, 0x55, 0x55, 0xd5, 0x3f),
                x(0x3f, 0xd5, 0x55, 0x55, 0x55, 0x55, 0x55, 0x55), x(0, 0, 0, 0, 0, 0, 0, 0));
        Struck<DoubleTestStructure> struck = Struck.forClass(DoubleTestStructure.class);

        //when
        DoubleTestStructure structure = struck.read(stream);

        //then
        assertThat(structure.reverse, is(1/3d));
    }
}
