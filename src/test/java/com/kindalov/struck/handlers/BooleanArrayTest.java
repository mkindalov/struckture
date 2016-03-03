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
public class BooleanArrayTest extends StruckTest {

    @Structure(len = 0x10)
    public static class BooleanArrayTestStructure {
        @StruckField(offset = 0, size = 2)
        private boolean[] array;
    }

    @Test
    public void shouldReadByteArray() {
        //given
        InputStream stream = stream(
                x(0x1, 0xff, 0, 0x10, 0, 0, 0, 0), x(0x7, 0, 0, 0x5, 0, 0, 0, 0),
                x(0x20, 0, 0, 0x10, 0, 0, 0, 0), x(0x7, 0, 0, 0x5, 0, 0, 0, 0));
        Struck<BooleanArrayTestStructure> struck = Struck.forClass(BooleanArrayTestStructure.class);

        //when
        BooleanArrayTestStructure structure = struck.read(stream);

        //then
        assertThat(structure.array, is(new boolean[] {false, false, false, false, false, false, false, true,
                true, true, true, true, true, true, true, true}));
    }
}
