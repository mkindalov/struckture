package com.kindalov.struck.handlers;

import com.kindalov.struck.Struck;
import com.kindalov.struck.StruckTest;
import com.kindalov.struck.annotations.BitPosition;
import com.kindalov.struck.annotations.StruckField;
import com.kindalov.struck.annotations.Structure;
import org.junit.Test;

import java.io.InputStream;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.junit.MatcherAssert.assertThat;

/**
 * TODO comment.
 */
public class BooleanTest extends StruckTest {

    @Structure(len = 0x10)
    public static class BooleanTestStructure {
        @StruckField(offset = 0x3)
        private Boolean wrapper;
        @StruckField(offset = 0xa)
        private boolean primitive;
        @StruckField(offset = 0xf) @BitPosition(3)
        private boolean bit3;
        @StruckField(offset = 0xf) @BitPosition(4)
        private boolean bit4;
    }

    @Test
    public void shouldReadBooleanWrapper() {
        //given
        InputStream stream = stream(
                x(0x7, 0, 0, 0x10, 0, 0, 0, 0), x(0x7, 0, 0, 0x5, 0, 0, 0, 0),
                x(0x20, 0, 0, 0, 0, 0, 0, 0), x(0x7, 0, 0x1, 0x5, 0, 0, 0, 0));
        Struck<BooleanTestStructure> struck = Struck.forClass(BooleanTestStructure.class);

        //when
        BooleanTestStructure structure1 = struck.read(stream);
        BooleanTestStructure structure2 = struck.read(stream);

        //then
        assertThat(structure1.wrapper, is(true));
        assertThat(structure2.wrapper, is(false));
    }

    @Test
    public void shouldReadBooleanPrimitive() {
        //given
        InputStream stream = stream(
                x(0, 0, 0, 0x10, 0, 0, 0, 0), x(0, 0, 0, 0x5, 0, 0, 0, 0),
                x(0x20, 0, 0, 0x10, 0, 0, 0, 0x03), x(0, 0, 0x1, 0x5, 0, 0, 0, 0));
        Struck<BooleanTestStructure> struck = Struck.forClass(BooleanTestStructure.class);

        //when
        BooleanTestStructure structure1 = struck.read(stream);
        BooleanTestStructure structure2 = struck.read(stream);

        //then
        //then
        assertThat(structure1.primitive, is(false));
        assertThat(structure2.primitive, is(true));
    }

    @Test
    public void shouldReadBits() {
        //given
        InputStream stream = stream(
                x(0, 0, 0, 0x10, 0, 0, 0, 0), x(0, 0, 0, 0x5, 0, 0, 0, 0xf),
                x(0x20, 0, 0, 0x10, 0, 0, 0, 0x03), x(0, 0, 0x1, 0x5, 0, 0, 0, 0));
        Struck<BooleanTestStructure> struck = Struck.forClass(BooleanTestStructure.class);

        //when
        BooleanTestStructure structure = struck.read(stream);

        //then
        //then
        assertThat(structure.bit3, is(true));
        assertThat(structure.bit4, is(false));
    }
}
