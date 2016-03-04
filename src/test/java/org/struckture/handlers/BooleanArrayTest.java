package org.struckture.handlers;

import org.struckture.base.Struck;
import org.struckture.base.Strucktor;
import org.struckture.base.StrucktureTest;
import org.struckture.base.annotations.StruckField;
import org.struckture.base.annotations.Struckture;
import org.junit.Test;

import java.io.InputStream;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.junit.MatcherAssert.assertThat;

/**
 * Test.
 */
public class BooleanArrayTest extends StrucktureTest {

    @Struckture(length = 0x10)
    private static class BooleanArrayTestStructure {
        @StruckField(offset = 0, size = 2)
        private boolean[] array;
    }

    @Test
    public void shouldReadByteArray() {
        //given
        InputStream stream = stream(
                o(0x1, 0xff, 0, 0x10, 0, 0, 0, 0), o(0x7, 0, 0, 0x5, 0, 0, 0, 0),
                o(0x20, 0, 0, 0x10, 0, 0, 0, 0), o(0x7, 0, 0, 0x5, 0, 0, 0, 0));
        Struck<BooleanArrayTestStructure> struck = Strucktor.forClass(BooleanArrayTestStructure.class);

        //when
        BooleanArrayTestStructure structure = struck.read(stream);

        //then
        assertThat(structure.array, is(new boolean[] {false, false, false, false, false, false, false, true,
                true, true, true, true, true, true, true, true}));
    }
}
